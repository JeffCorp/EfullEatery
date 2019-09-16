package com.efulltech.efulleatery.contract.db;

public final class AuthContract {
    private AuthContract(){}

    public static class AuthEntry{
        public static final String TABLE_NAME = "user_info";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String PASSWORD = "password";

    }
}
