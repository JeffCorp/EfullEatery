package com.efulltech.efulleatery.table.models;

public class Fruits {

    private int fruit_id;
    private String fruit_name;

    public Fruits(int fruit_id, String fruit_name) {
        this.fruit_id = fruit_id;
        this.fruit_name = fruit_name;
    }

    public int getFruit_id() {
        return fruit_id;
    }

    public String getFruit_name() {
        return fruit_name;
    }

    public void setFruit_name(String fruit_name) {
        this.fruit_name = fruit_name;
    }

    public void setFruit_id(int fruit_id) {
        this.fruit_id = fruit_id;
    }
}
