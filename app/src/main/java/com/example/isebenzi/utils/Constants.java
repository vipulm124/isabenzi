package com.example.isebenzi.utils;

public class Constants {
    public static final String DEBUG_KEY = "**Debug";
    public static String NETWORK_SERVICE_BASE_URL = "http://isebenzi.wekanexplain.com/";
//    http://isebenzi.wekanexplain.com/login

    public static final String SARVER_MSG = "message";

    public static class NetworkServiceMethods {
        public static class User {
            public static String USER_LOGIN = NETWORK_SERVICE_BASE_URL + "login";
            public static String USER_REGISTER = NETWORK_SERVICE_BASE_URL + "signup";
        }
    }

    public static class KeyValues {
        public static final String USER = "User";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";

        public static final String FIRST_NAME = "firstname";
        public static final String LAST_NAME = "lastname";
        public static final String PHOEN = "phone";
        public static final String TYPE = "type";
        public static final String PASSPORT_ID = "passport";
        public static final String PHOTO = "image";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ADDRESS = "address";
        public static final String DAILY_RATE = "rate";
        public static final String RADIUS = "radius";
        public static final String TO = "to";
        public static final String FROM = "from";
        public static final String PASSPORT_PHOTO = "passport";
        public static final String ACCOUNT_NUMBER = "account_number";
        public static final String BRANCH_CODE = "branch_code";
        public static final String BANK_NAME = "bank_name";
        public static final String ACCOUNT_NAME = "account_name";
        public static final String STATUS = "status";
        public static final String ID ="id" ;
        public static final String USER_ID ="user_id" ;
        public static final String PROVIDER_USER_ID ="provider" ;
        public static final String SEEKER_USER_ID ="seeker" ;
        public static final String OCCUPATION ="occupation" ;
        public static final String FROM_DATE ="from_date" ;
        public static final String TO_DATE ="to_date" ;
        public static final String PER_DAY ="per_day" ;
        public static final String PER_HOUR ="per_hour" ;
        public static final String PERSONS ="persons" ;
        public static final String BANK_TYPE ="bank_type" ;
        public static final String EXPIRY ="expiry" ;
        public static final String STC ="stc" ;
        public static final String NUMBER ="number" ;
        public static final String LOCATION_NAME ="loc_name" ;



    }

    public static class Messages {

        public static final String NO_INTERNET = "Internet is not connected";

    }
}
