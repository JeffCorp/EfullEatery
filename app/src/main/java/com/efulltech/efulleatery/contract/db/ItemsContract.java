package com.efulltech.efulleatery.contract.db;

public class ItemsContract {

    private ItemsContract(){}

    public static class ItemsEntry{
        public static final String TABLE_NAME_ITEMS = "items_table";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String START_QUANTITY = "start_quantity";
        public static final String ITEM_QUANTITY = "item_quantity";
        public static final String ITEM_PRICE = "item_price";
        public static final String CATEGORY_ID = "category_id";
        public static final String ITEM_IMAGE_ID = "image_id";
    }
}

