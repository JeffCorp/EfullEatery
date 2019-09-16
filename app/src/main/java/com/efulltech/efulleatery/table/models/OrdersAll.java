package com.efulltech.efulleatery.table.models;

public class OrdersAll {

    private String order_list_table_name;
    private String order_list_chair_name;
    private String order_list_items;
    private String order_list_price;

    public OrdersAll(String order_list_table_name, String order_list_chair_name, String order_list_items, String order_list_price) {
        this.order_list_table_name = order_list_table_name;
        this.order_list_chair_name = order_list_chair_name;
        this.order_list_items = order_list_items;
        this.order_list_price = order_list_price;
    }

    public String getOrder_list_table_name() {
        return order_list_table_name;
    }

    public void setOrder_list_table_name(String order_list_table_name) {
        this.order_list_table_name = order_list_table_name;
    }

    public String getOrder_list_chair_name() {
        return order_list_chair_name;
    }

    public void setOrder_list_chair_name(String order_list_chair_name) {
        this.order_list_chair_name = order_list_chair_name;
    }

    public String getOrder_list_items() {
        return order_list_items;
    }

    public void setOrder_list_items(String order_list_items) {
        this.order_list_items = order_list_items;
    }

    public String getOrder_list_price() {
        return order_list_price;
    }

    public void setOrder_list_price(String order_list_price) {
        this.order_list_price = order_list_price;
    }
}
