/*
 Copyright (c) 2019 Kevin Jones, All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.
 */

package com.nawforce.platform.Component;

import com.nawforce.platform.Internal.Object$;
import com.nawforce.platform.System.Boolean;
import com.nawforce.platform.System.Id;
import com.nawforce.platform.System.String;

@SuppressWarnings("unused")
public class Chatter {
    public static class Feed {
        public Id EntityId;
        public String FeedItemType;
        public Id Id;
        public String OnComplete;
        public Boolean Rendered;
        public Object$ ReRender;
        public Boolean ShowPublisher;
    }
    public static class FeedWithFollowers {
        public Id EntityId;
        public Id Id;
        public String OnComplete;
        public Boolean Rendered;
        public Object$ ReRender;
        public Boolean ShowHeader;
    }
    public static class Follow {
        public Id EntityId;
        public Id Id;
        public String OnComplete;
        public Boolean Rendered;
        public Object$ ReRender;
    }
    public static class Followers {
        public Id EntityId;
        public Id Id;
        public Boolean Rendered;
    }
    public static class Newsfeed {
        public Id Id;
        public String OnComplete;
        public Boolean Rendered;
        public Object$ ReRender;
    }
}
