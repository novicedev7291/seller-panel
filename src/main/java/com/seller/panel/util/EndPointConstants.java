package com.seller.panel.util;

public final class EndPointConstants {

    private EndPointConstants() {
        //
    }

    public static final String ENDPOINTS_PREFIX = "/api/v1";

    public static final class Invitation {
        private Invitation() {
            //
        }

        public static final String INVITE = ENDPOINTS_PREFIX+"/invite";

    }

}
