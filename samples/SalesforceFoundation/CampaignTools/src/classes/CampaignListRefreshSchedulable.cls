/*
    Copyright (c) 2016 Salesforce.org
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Salesforce.com Foundation nor the names of
      its contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
    "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
    LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
    FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
    COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
    BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
    CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
    LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
    ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
    POSSIBILITY OF SUCH DAMAGE.
*/
/**
 * @author Salesforce.org
 * @group CampaignTools
 * @description A Schedulable for refreshing marked Campaigns from Campaign Lists
 */
public with sharing class CampaignListRefreshSchedulable implements Schedulable {
    /**
     * @description Implementation of Schedulable.execute() method.  This will
     * begin the process of refreshing Campaigns that have been marked for
     * auto-refresh.  It will query for all Campaigns to be refreshed,
     * determine any dependencies between Campaigns, and remove any cyclic
     * dependencies that may exist.  Then, it delegates to the Worker class to
     * begin the actual scheduling of Campaign refreshes.
     *
     * @param sc
     */
    public void execute(SchedulableContext sc) {
        // Check if user has all required permissions for batch processing
        CampaignTools_UTIL.checkCampaignBatchPermission();

        Map<Id, Campaign> campaigns = getCampaignsForAutoRefresh();

        lockAndMarkCampaignsAsQueued(campaigns);

        Set<Id> campaignListIds = getRootSegmentIdsFromCampaigns(campaigns.values());

        List<Segment__c> segments = getSegmentsByRootIds(campaignListIds);

        CampaignGraph cg = new CampaignGraph(campaigns, segments);

        cg.removeCycles();

        Worker w = new Worker(cg);

        CampaignList.getService().enqueueJob(w);
    }

    /**
     * @description Query for all Campaigns that have been marked for
     * auto-refresh and that have a Campaign List assigned.
     *
     * @return Map<Id, Campaign>
     */
    @TestVisible
    private static Map<Id, Campaign> getCampaignsForAutoRefresh() {
        return new Map<Id, Campaign>([
            SELECT
                Campaign_List__c,
                Campaign_List_Update_Status__c,
                Campaign_List_Mutex__c
            FROM Campaign
            WHERE Campaign_List_Refresh_Automatically__c = true
            AND Campaign_List__c != null
            FOR UPDATE
        ]);
    }

    /**
     * @description Query for Campaigns given a set of Campaign ids.
     *
     * @param campaignIds
     * @return List<Campaign>
     */
    @TestVisible
    private static List<Campaign> getCampaignsByIds(Set<Id> campaignIds) {
        return [
            SELECT
                Campaign_List__c,
                Campaign_List_Update_Status__c
            FROM Campaign
            WHERE Id IN :campaignIds
        ];
    }

    /**
     * @description Given a list of Campaigns, pluck the associated Campaign
     * List ids from those Campaigns and return this set of ids.
     *
     * @param campaigns
     * @return Set<Id>
     */
    @TestVisible
    private static Set<Id> getRootSegmentIdsFromCampaigns(List<Campaign> campaigns) {
        Set<Id> campaignListIds = new Set<Id>();

        for (Campaign c : campaigns) {
            campaignListIds.add(c.Campaign_List__c);
        }

        return campaignListIds;
    }

    /**
     * @description Given a set of Segment__c ids, query for all Segment__c
     * objects that are associated with those ids.
     *
     * @param segmentIds The ids of the root segments for which all associated segments should be queried.
     * @return List<Segment__c>
     */
    @TestVisible
    private static List<Segment__c> getSegmentsByRootIds(Set<Id> segmentIds) {
        return [
            SELECT
                Root_Segment__c,
                Source_ID__c,
                (
                    SELECT Id
                    FROM Campaigns__r
                )
            FROM Segment__c
            WHERE Id IN :segmentIds
            OR Root_Segment__c IN :segmentIds
        ];
    }

    /**
     * @description Given some Campaigns, mark those Campaigns as being locked
     * (by updating their mutex field) and update their status to 'Queued'.
     *
     * @param campaigns
     */
    @TestVisible
    private static void lockAndMarkCampaignsAsQueued(Map<Id, Campaign> campaigns) {
        for (Campaign c : campaigns.values()) {
            if (null != c.Campaign_List_Mutex__c) {
                throw new CampaignAlreadyLockedException();
            }
            c.Campaign_List_Mutex__c = DateTime.now();
            c.Campaign_List_Update_Status__c = 'Queued';
        }
        update campaigns.values();
    }

    /**
     * @author Salesforce.org
     * @description An exception that is thrown if a Campaign is unable to be
     * locked because it is already locked.
     */
    public class CampaignAlreadyLockedException extends Exception {}

    /**
     * @author Salesforce.org
     * @description This class represents a graph of dependencies between
     * Campaigns to be updated.  It allows for updating the status of those
     * Campaigns and then querying for Campaigns that have all of their
     * dependencies resolved and are ready for scheduling.
     */
    @TestVisible
    private with sharing class CampaignGraph {
        /**
         * @description The CampaignNodes that make up this graph.
         */
        @TestVisible
        private Map<Id, CampaignNode> nodes = new Map<Id, CampaignNode>();

        /**
         * @description A set for keeping track of which nodes have been
         * visited when performing a depth first search.
         */
        private Set<CampaignNode> visitedNodes = new Set<CampaignNode>();

        /**
         * @description A set for keeping track of which nodes have been
         * assigned to a strongly connected component when identifying SSCs.
         */
        private Set<CampaignNode> assignedNodes = new Set<CampaignNode>();

        /**
         * @description A collection of strongly connected components, where
         * the key represents the 'root' of the component, and the value is the
         * set of nodes within that SSC.
         */
        private Map<CampaignNode, Set<CampaignNode>> stronglyConnectedComponents = new Map<CampaignNode, Set<CampaignNode>>();

        /**
         * @description A list of nodes that is intended to be used as a stack
         * for identifying potential leading nodes when searching for strongly
         * connected components.
         */
        private List<CampaignNode> leadingNodesStack = new List<CampaignNode>();

        /**
         * @description A private empty constructor for bypassing the main
         * constructor.
         */
        @TestVisible
        private CampaignGraph() {}

        /**
         * @description Construct a CampaignGraph given a map of Campaigns and
         * a list of all of the Segment__c objects associated with those
         * Campaigns.  This will determine the dependencies between the
         * Campaigns, and store that dependency graph.
         *
         * @param campaigns
         * @param segments
         */
        public CampaignGraph(Map<Id, Campaign> campaigns, List<Segment__c> segments) {
            // start by adding each given campaign as a node in the campaign
            // graph.
            for (Id campaignId : campaigns.keySet()) {
                Campaign c = campaigns.get(campaignId);
                nodes.put(
                    campaignId,
                    new CampaignNode(
                        campaignId,
                        c.Campaign_List__c
                    )
                );
            }

            Map<Id, Segment__c> segmentsMap = new Map<Id, Segment__c>(segments);

            // next we determine both in and out edges for the campaign nodes
            // by examining the Segment__c objects associated with the given
            // campaigns for which Segment__c objects point to which Campaigns
            // as sources.
            for (Segment__c s : segments) {
                if (campaigns.containsKey(s.Source_ID__c)) {
                    Campaign sourceCampaign = campaigns.get(s.Source_ID__c);
                    List<Campaign> targetCampaigns;
                    if (null != s.Root_Segment__c) {
                        if (!segmentsMap.containsKey(s.Root_Segment__c))  {
                            continue;
                        }
                        targetCampaigns = segmentsMap.get(s.Root_Segment__c).Campaigns__r;
                    } else {
                        targetCampaigns = s.Campaigns__r;
                    }
                    for (Campaign targetCampaign : targetCampaigns) {
                        if (nodes.containsKey(targetCampaign.Id)) {
                            CampaignNode sourceCampaignNode = nodes.get(sourceCampaign.Id);
                            CampaignNode targetCampaignNode = nodes.get(targetCampaign.Id);
                            sourceCampaignNode.dependants.add(targetCampaignNode);
                            targetCampaignNode.dependencies.add(sourceCampaignNode);
                        }
                    }
                }
            }
        }

        /**
         * @description Get a set of Campaign ids for the Campaigns stored in
         * this graph.
         *
         * @return Set<Id>
         */
        public Set<Id> getCampaignIds() {
            return nodes.keySet();
        }

        /**
         * @description Update the status of the Campaigns stored in this
         * Campaign graph given a list of Campaigns that include the update
         * status value.
         *
         * @param campaigns
         * @return Integer The number of Campaigns in 'Queued' status
         */
        public Integer setCampaignStatus(List<Campaign> campaigns) {
            Integer queuedCount = 0;
            for (Campaign c : campaigns) {
                if (nodes.containsKey(c.Id)) {
                    nodes.get(c.Id).updateStatus = c.Campaign_List_Update_Status__c;
                    if ('Queued' == c.Campaign_List_Update_Status__c) {
                        queuedCount++;
                    }
                }
            }
            return queuedCount;
        }

        /**
         * @description Determine which Campaigns stored in this graph are
         * ready for scheduling.  That is, find the Campaigns that have not yet
         * been scheduled for update and that have all of their dependencies
         * resolved.
         *
         * @return List<Campaign>
         */
        public List<Campaign> getReadyCampaigns() {
            List<Campaign> readyCampaigns = new List<Campaign>();

            for (CampaignNode cn : nodes.values()) {
                if (cn.isReady()) {
                    readyCampaigns.add(new Campaign(
                        Id = cn.campaignId,
                        Campaign_List__c = cn.campaignListId
                    ));
                }
            }

            return readyCampaigns;
        }

        /**
         * @description Locate and break any cycles that may exist in this
         * graph.
         */
        @TestVisible
        private void removeCycles() {
            /*
             * If there is a cyclic dependency between Campaigns then we want
             * to break that cycle, but still attempt to update Campaigns with
             * minimal disruption to their dependency order.
             *
             * This method tries preserve dependencies that are not involved in
             * a cycle by first finding all strongly connected components (SSC)
             * using Kosaraju's algorithm, and then breaking all cycles between
             * the nodes in each SSC.
             *
             * A strongly connected component is a graph where every node is
             * reachable from every other node in the graph.  The nodes in a
             * graph can be partitioned into subgraphs where each subgraph is a
             * SSC (a graph with a single node is considered a strongly
             * connected component, regardless of whether it has a loop or not).
             *
             * Kosaraju's algorithm works by performing two depth first
             * searches of the graph, first traversing edges in one direction,
             * and the second DFS by traversing edges in the other direction.
             * The first DFS tracks the order of the nodes as they are first
             * discovered and adds them to a stack (here called
             * leadingNodesStack).  The second pass through the graph performs
             * a DFS on the nodes in the order they appear on the stack.  In
             * the second pass, if a node pulled from the stack is not yet
             * visited, then it is considered a 'leading node' and all
             * unvisited nodes reachable by performing a DFS in reverse
             * direction from that node are added to a SSC identified by the
             * leading node (stronglyConnectedComponents map)
             *
             * Once the strongly connected components are identified, we
             * identify all edges between nodes in that SSC (omitting edges
             * to/from nodes outside the SSC).  Then we identify a set of edges
             * that we can remove to break all cycles within the SSC (see
             * getEdgesToRemove() for details), and finally we remove those
             * identified edges from the graph.
             */
            visitedNodes = new Set<CampaignNode>();
            assignedNodes = new Set<CampaignNode>();
            stronglyConnectedComponents = new Map<CampaignNode, Set<CampaignNode>>();
            leadingNodesStack = new List<CampaignNode>();

            for (CampaignNode cn : nodes.values()) {
                depthFirstVisit(cn);
            }

            for (Integer i = leadingNodesStack.size() - 1; i >= 0; i--) {
                CampaignNode cn = leadingNodesStack.get(i);
                assignToStronglyConnectedComponent(cn, cn);
            }

            for (Set<CampaignNode> ssc : stronglyConnectedComponents.values()) {
                List<Edge> edgesInComponent = getEdgesBetweenNodes(ssc);
                List<Edge> edgesToRemove = getEdgesToRemove(edgesInComponent);
                removeEdges(edgesToRemove);
            }
        }

        /**
         * @description Perform a depth first search on the given CampaignNode
         * by recursively visiting each *out* edge of the node.  After all
         * nodes are visited, add this node to the leading nodes stack as a
         * potential leading node (see removeCycles())
         *
         * @param cn The CampaignNode to recursively process
         */
        private void depthFirstVisit(CampaignNode cn) {
            if (!visitedNodes.contains(cn)) {
                visitedNodes.add(cn);
                for (CampaignNode dependency : cn.dependencies) {
                    depthFirstVisit(dependency);
                }
                leadingNodesStack.add(cn);
           }
        }

        /**
         * @description Given a node and root node, assign the given node to
         * the strongly connected component associated with the root node if
         * the given node is not already assigned to an SSC.  This will
         * recursively assign each *in* edge of the given node.
         *
         * @param cn The CampaignNode to recursively assign
         * @param root The CampaignNode that is the root of the currently processing SSC
         */
        private void assignToStronglyConnectedComponent(CampaignNode cn, CampaignNode root) {
            if (!assignedNodes.contains(cn)) {
                if (!stronglyConnectedComponents.containsKey(root)) {
                    stronglyConnectedComponents.put(root, new Set<CampaignNode>());
                }
                stronglyConnectedComponents.get(root).add(cn);
                assignedNodes.add(cn);
                for (CampaignNode dependant : cn.dependants) {
                    assignToStronglyConnectedComponent(dependant, root);
                }
            }
        }

        /**
         * @description Given a set of nodes, determine the edges that exist
         * between these nodes.
         *
         * @param nodes
         * @return List<Edge>
         */
        private List<Edge> getEdgesBetweenNodes(Set<CampaignNode> nodes) {
            List<Edge> edges = new List<Edge>();

            for (CampaignNode cn : nodes) {
                for (CampaignNode dependency : cn.dependencies) {
                    if (nodes.contains(dependency)) {
                        edges.add(new Edge(cn, dependency));
                    }
                }
            }

            return edges;
        }

        /**
         * @description Given a list of edges that may contain a cycle, return
         * a list of edges that can be removed from the graph to break that
         * cycle.
         *
         * @param edges A list of edges, presumably constituting a cycle
         * @return List<Edge> A list of edges that can be removed to break that cycle
         */
        private List<Edge> getEdgesToRemove(List<Edge> edges) {
            /*
             * This works by partitioning the edges into three sets.  Given the
             * edge A -> B, if A is "less than" B then it is added to the
             * "left" set.  If A is "greater than" B then it is added to the
             * "right" set.  If A is "equal" to B, then it is added to the set
             * of edges definitely to be removed.
             *
             * Then, we know that neither the "left" and "right" partitions
             * contain a cycle, since if the set contained a cycle then there
             * must be an edge that connects a node to a node "earlier" in the
             * ordering-- which is not possible given how the sets were
             * partitioned.
             *
             * We can break any cycles in the given set of edges by choosing to
             * keep either the "left" set or the "right" set.  We choose to
             * keep the set that maintains the most number of edges from the
             * original set of edges.  So, we return (to be removed from the
             * graph) the set that contains the lesser number of edges.
             */
            List<Edge> edgesToRemove = new List<Edge>();
            List<Edge> left = new List<Edge>();
            List<Edge> right = new List<Edge>();

            for (Edge e : edges) {
                Integer compare = e.fromNode.compareTo(e.toNode);
                if (compare < 0) {
                    left.add(e);
                } else if (compare > 0) {
                    right.add(e);
                } else {
                    edgesToRemove.add(e);
                }
            }

            if (left.size() < right.size()) {
                edgesToRemove.addAll(left);
            } else {
                edgesToRemove.addAll(right);
            }

            return edgesToRemove;
        }

        /**
         * @description Remove the given list of edges from the CampaignGraph
         *
         * @param edges The edges to remove
         */
        private void removeEdges(List<Edge> edges) {
            for (Edge e : edges) {
                if (nodes.containsKey(e.fromNode.campaignId)) {
                    nodes.get(e.fromNode.campaignId).dependencies.remove(e.toNode);
                }
                if (nodes.containsKey(e.toNode.campaignId)) {
                    nodes.get(e.toNode.campaignId).dependants.remove(e.fromNode);
                }
            }
        }
    }

    /**
     * @description The set of values that are considered 'finished' states for
     * the Campaign refresh process.
     */
    private static Set<String> finishedStates = new Set<String>{
        'Completed',
        'Aborted',
        'Failed'
    };

    /**
     * @author Salesforce.org
     * @description A class that represents a node in the graph of campaign
     * dependencies.
     */
    @TestVisible
    private with sharing class CampaignNode implements Comparable {
        /**
         * @description The id of the Campaign associated with this node
         */
        public Id campaignId;

        /**
         * @description The id of the Campaign List associated with this node
         */
        public Id campaignListId;

        /**
         * @description The refresh status of this Campaign, i.e., 'Queued',
         * 'Processing', 'Completed', etc.
         */
        public String updateStatus = '';

        /**
         * @description A set of nodes that are dependencies of this node.
         * That is, if the campaign represented by this node depends on other
         * Campaigns as one of its sources, then the node that represents that
         * Campaign should be in this set.
         */
        public Set<CampaignNode> dependencies = new Set<CampaignNode>();

        /**
         * @description A set of nodes that are dependants of this node.  That
         * is, if the campaign represented by this node is dependended on as a
         * source by another Campaign, then the node that represents that
         * Campaign should be in this set.
         */
        public Set<CampaignNode> dependants = new Set<CampaignNode>();

        /**
         * @description Construct a CampaignNode for a Campaign by id, with
         * associated campaign list.
         *
         * @param campaignId The id of the Campaign this node represents
         * @param campaignListId The id of the CampaignList the Campaign is associated with
         */
        public CampaignNode(Id campaignId, Id campaignListId) {
            this.campaignId = campaignId;
            this.campaignListId = campaignListId;
        }

        /**
         * @description Determine if the Campaign represented by this node is
         * ready to be scheduled for a refresh.  This will check to see if all
         * dependencies of the Campaign have been resolved (i.e. are in a
         * finished state).
         *
         * @return Boolean
         */
        public Boolean isReady() {
            if ('Queued' != updateStatus) {
                return false;
            }

            Set<String> dependencyStatus = new Set<String>();

            for (CampaignNode cn : dependencies) {
                if (!String.isEmpty(cn.updateStatus)) {
                    dependencyStatus.add(cn.updateStatus);
                }
            }

            return !dependencyStatus.retainAll(finishedStates);
        }

        /**
         * @description Implementation of Object.hashCode(), so this
         * CampaignNode can be stored in a set.
         *
         * @return Integer
         */
        public Integer hashCode() {
            return ((Object) campaignId).hashCode();
        }

        /**
         * @description Implementation of Object.equals(), so this CampaignNode
         * can be stored in a set.
         *
         * @param other The object to check for equality
         * @return Boolean
         */
        public Boolean equals(Object other) {
            if (null == other) {
                return false;
            }
            return (0 == compareTo(other));
        }

        /**
         * @description Implementation of Comparable.compareTo().  This will
         * compare this node's campaignId to the other node's campaignId.
         *
         * @param other The other object to compare to.
         * @return Integer
         */
        public Integer compareTo(Object other) {
            Id otherId = ((CampaignNode) other).campaignId;
            if (campaignId == otherId) {
                return 0;
            }
            if (campaignId > otherId) {
                return 1;
            }
            return -1;
        }
    }

    /**
     * @author Salesforce.org
     * @description A class representing a directed edge in the CampaignGraph.
     */
    private with sharing class Edge {
        /**
         * @description The CampaignNode from which this edge connects to
         * another node.
         */
        public CampaignNode fromNode;

        /**
         * @description The CampaignNode to which this edge connects another
         * node.
         */
        public CampaignNode toNode;

        /**
         * @description Construct an Edge given two nodes.
         *
         * @param fromNode The inbound node for this edge
         * @param toNode The outbound node for this edge
         */
        public Edge(CampaignNode fromNode, CampaignNode toNode) {
            this.fromNode = fromNode;
            this.toNode = toNode;
        }
    }

    /**
     * @author Salesforce.org
     * @description The worker class that will schedule the refresh process for
     * any Campaigns that are determined to be ready to schedule.  This class
     * will attempt to schedule as many refresh processes as are determined to
     * be ready to be scheduled, while also maintaining a buffer of
     * availability in the FlexQueue.  If the worker is unable to schedule all
     * of the Campaigns needed to be scheduled, it will call itself
     * recursively.
     */
    public with sharing class Worker implements Queueable {
        /**
         * @description The CampaignGraph that represents the dependencies
         * between Campaigns to be updated.
         */
        @TestVisible
        private CampaignGraph cg;

        /**
         * @description Construct a Worker given a CampaignGraph
         *
         * @param cg
         */
        public Worker(CampaignGraph cg) {
            this.cg = cg;
        }

        /**
         * @description Implementation of Queueable.execute().  Will query for
         * Campaign update status, determine which Campaigns are ready to
         * schedule, and then will attempt to schedule as many of those refresh
         * processes as possible.  If there are still Campaigns to be
         * scheduled, it will invoke itself recursively.
         *
         * @param qc
         */
        public void execute(QueueableContext qc) {
            CampaignList.Service service = CampaignList.getService();

            List<Campaign> campaigns = getCampaignsByIds(cg.getCampaignIds());

            Integer queuedCount = cg.setCampaignStatus(campaigns);

            List<Campaign> readyCampaigns = cg.getReadyCampaigns();

            // how much room is in the flex queue if we want to leave at least
            // 50 slots open?
            Integer flexQueueCapacity = service.getFlexQueueCapacity(50);

            // regardless of how much room is in the flex queue, never try to
            // schedule more than 50 per execute
            Integer maxToSchedule = Math.min(
                flexQueueCapacity,
                50
            );

            List<Campaign> campaignsToSchedule = new List<Campaign>();

            // we can't schedule more than are ready...
            Integer numberOfCampaignsToSchedule = Math.min(
                maxToSchedule,
                readyCampaigns.size()
            );

            for (Integer i = 0; i < numberOfCampaignsToSchedule; i++) {
                Campaign campaignToSchedule = readyCampaigns.get(i);
                service.updateCampaignFromCampaignList(
                    campaignToSchedule.Id,
                    campaignToSchedule.Campaign_List__c,
                    false
                );
            }

            if (queuedCount > 0) {
                Worker w = new Worker(cg);
                service.enqueueJob(w);
            }
        }
    }
}