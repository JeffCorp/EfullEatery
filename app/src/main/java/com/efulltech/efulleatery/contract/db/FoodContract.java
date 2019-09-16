package com.efulltech.efulleatery.contract.db;

public class FoodContract {

    private FoodContract(){}

    public static class FoodEntry{
        public static final String TABLE_NAME = "food_table";
        public static final String FOOD_ID = "food_id";
        public static final String FOOD_NAME = "food_name";
        public static final String FOOD_QUANTITY = "food_quantity";
        public static final String FOOD_PRICE = "food_price";
    }
}
