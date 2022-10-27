package com.remember.core.consts;

public final class Urls {
    public static class USERS {
        public final static String ROOT = "/users";

        public static class ME {
            public final static String ROOT = USERS.ROOT + "/me";

            public static class QUESTIONS {
                public final static String ROOT = ME.ROOT + "/questions";
                public final static String ID = ROOT + "/{id}";
            }
        }
    }

    public static class QUESTIONS {
        public final static String ROOT = "/questions";
        public final static String ID = ROOT + "/{id}";
    }

    public static class ALGORITHMS {
        public final static String ROOT = "/algorithms";
    }

    public static class PLATFORMS {
        public final static String ROOT = "/platforms";
    }

    public static class PRACTICESTATUSUS {
        public final static String ROOT = "/practiceStatusus";
        public final static String NAME = ROOT + "/{name}";
    }
}
