package com.efulltech.efulleatery.table.models;

public class Table {

    private int table_image_id;
    private String table_name;

    public Table(int table_image_id, String table_name) {
        this.table_image_id = table_image_id;
        this.table_name = table_name;
    }

    public int getTable_image_id() {
        return table_image_id;
    }

    public String getTable_name() {
        return table_name;
    }
}
