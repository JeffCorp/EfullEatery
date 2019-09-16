package com.efulltech.efulleatery.table.models;

public class Drinks {

    private int drink_id;
    private String drink_name;

    public Drinks(int drink_id, String drink_name) {
        this.drink_id = drink_id;
        this.drink_name = drink_name;
    }

    public int getDrink_id() {
        return drink_id;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }
}
