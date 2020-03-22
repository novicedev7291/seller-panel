package com.seller.panel.util;

public final class EndPointConstants {

    private EndPointConstants() {
        //
    }

    public static final String ENDPOINTS_PREFIX = "/api/v1";

    public static final class Ping {
        private Ping() {
            //
        }

        public static final String PING = "/ping";

    }

    public static final class Invitation {
        private Invitation() {
            //
        }

        public static final String INVITE = ENDPOINTS_PREFIX+"/invite";

    }

    public static final class Registration {
        private Registration() {
            //
        }

        public static final String REGISTER = ENDPOINTS_PREFIX+"/register/{id}";

    }

    public static final class SignUp {
        private SignUp() {
            //
        }

        public static final String SIGNUP = ENDPOINTS_PREFIX+"/signup";

    }

    public static final class Login {
        private Login() {
            //
        }

        public static final String LOGIN = ENDPOINTS_PREFIX+"/login";

    }


}
