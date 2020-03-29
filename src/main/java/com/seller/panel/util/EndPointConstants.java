package com.seller.panel.util;

public final class EndPointConstants {

    private EndPointConstants() {
        //
    }

    public static final String ENDPOINTS_PREFIX = "";

    public static final class Categories {
        private Categories() {
            //
        }

        public static final String CATEGORIES = "/categories";

    }

    public static final class OAuth {
        private OAuth() {
            //
        }

        public static final String OAUTH_TOKEN = "/oauth/token";

    }

    public static final class Ping {
        private Ping() {
            //
        }

        public static final String PING = "/ping";

    }

    public static final class Join {
        private Join() {
            //
        }

        public static final String JOIN = ENDPOINTS_PREFIX+"/join";

    }

    public static final class Invitation {
        private Invitation() {
            //
        }

        public static final String INVITE = ENDPOINTS_PREFIX+"/"+AppConstants.INVITE+"/{access_token_id}";

    }

    public static final class Registration {
        private Registration() {
            //
        }

        public static final String REGISTER = ENDPOINTS_PREFIX+"/register";

    }

    public static final class Login {
        private Login() {
            //
        }

        public static final String LOGIN = ENDPOINTS_PREFIX+"/login";

    }

    public static final class Users {
        private Users() {
            //
        }

        public static final String USERS = ENDPOINTS_PREFIX+"/users";
        public static final String USERS_BY_ID = USERS+"/{id}";

    }


}
