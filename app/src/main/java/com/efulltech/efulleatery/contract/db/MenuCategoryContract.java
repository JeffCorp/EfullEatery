package com.efulltech.efulleatery.contract.db;

public class MenuCategoryContract {
    private MenuCategoryContract(){}

    public static class MenuCategory{
        public static final String TABLE_NAME = "menu_category";
        public static final String CATEGORY_ID = "category_id";
        public static final String CATEGORY_NAME = "category_name";
        public static final String CATEGORY_DESCRIPTION = "category_description";
    }
}
