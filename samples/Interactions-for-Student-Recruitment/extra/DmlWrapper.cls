    /*******************************************************************************************************
    * @description Class that wraps records that need to have DML performed on them. One DML operation of each
    * kind will be performed at the end of the transaction.
    */     
    global class DmlWrapper {
        global List<SObject> objectsToInsert = new List<SObject>(); 
        global List<SObject> objectsToUpdate = new List<SObject>();
        global List<SObject> objectsToDelete = new List<SObject>();
        global List<SObject> objectsToUndelete = new List<SObject>();
        
        /*******************************************************************************************************
        * @description Groups the records in each of the property lists by object type.
        * @return void
        */
        public void groupByType() {
            objectsToInsert = groupSObjects(objectsToInsert);
            objectsToUpdate = groupSObjects(objectsToUpdate);
            objectsToDelete = groupSObjects(objectsToDelete);
            objectsToUndelete = groupSObjects(objectsToUndelete);
        }
        
        /*******************************************************************************************************
        * @description Groups records by object type to avoid the following error:
        * "Cannot have more than 10 chunks in a single operation. Please rearrange the data to reduce chunking".
        * Also, remove duplicate records to avoid the "Duplicate id in list" error.
        * @param sObjects The records to group by type.
        * @return List<SObject> A list with the records passed as parameters ordered by type. Duplicates removed.
        */
        private List<SObject> groupSObjects(List<SObject> sObjects) {                        
            Map<String, List<SObject>> sObjectsByType = new Map<String, List<SObject>>();
            List<SObject> sorted = new List<SObject>();

            //Remove duplicates
            sObjects = new List<SObject>(new Set<SObject>(sObjects));

            //Group records by sObjectType
            for (SObject sObj : sObjects) {
                String sObjType = sObj.getSObjectType().getDescribe().getName();

                if (sObjectsByType.containsKey(sObjType)) {
                    sObjectsByType.get(sObjType).add(sObj);
                } else {
                    sObjectsByType.put(sObjType, new List<SObject>{ sObj });
                }
            }

            for (List<SObject> groupedObjects : sObjectsByType.values()) {
                sorted.addAll(groupedObjects);
            }

            return sorted;            
        }
    }
