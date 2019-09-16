package com.efulltech.efulleatery.table.models;

public class Orders {

    private int Order_id;
    private String Table_id;
    private String Chair_id;
    private String Menu_id;
    private String Menu_price;
    private int Quantity;
    private String Amount_payable;
    private Boolean Payment_state;


    public Orders(int order_id, String table_id, String chair_id, String menu_id, String menu_price, int quantity, String amount_payable, Boolean payment_state) {
        Order_id = order_id;
        Table_id = table_id;
        Chair_id = chair_id;
        Menu_id = menu_id;
        Menu_price = menu_price;
        Quantity = quantity;
        Amount_payable = amount_payable;
        Payment_state = payment_state;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public String getTable_id() {
        return Table_id;
    }

    public String getMenu_id() {
        return Menu_id;
    }

    public String getMenu_price() {
        return Menu_price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getAmount_payable() {
        return Amount_payable;
    }

    public Boolean getPayment_state() {
        return Payment_state;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public void setTable_id(String table_id) {
        Table_id = table_id;
    }

    public void setMenu_id(String menu_id) {
        Menu_id = menu_id;
    }

    public void setMenu_price(String menu_price) {
        Menu_price = menu_price;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void setAmount_payable(String amount_payable) {
        Amount_payable = amount_payable;
    }

    public void setPayment_state(Boolean payment_state) {
        Payment_state = payment_state;
    }

    public String getChair_id() {
        return Chair_id;
    }
}
