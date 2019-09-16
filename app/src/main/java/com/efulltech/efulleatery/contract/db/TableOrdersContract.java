package com.efulltech.efulleatery.contract.db;

public class TableOrdersContract {
    private TableOrdersContract() {
    }

    public static class TableOrdersEntry{
        public static final String TABLE_NAME = "table_orders";
        public static final String ORDER_ID = "id";
        public static final String TABLE_ID = "table_id";
        public static final String CHAIR_ID = "chair_id";
        public static final String MENU_ID = "menu_id";
        public static final String QUANTITY = "quantity";
        public static final String MENU_PRICE = "menu_price";
        public static final String PAYABLE_AMOUNT = "amount_payable";
        public static final String HAS_PAID = "payment_state";
    }
}
