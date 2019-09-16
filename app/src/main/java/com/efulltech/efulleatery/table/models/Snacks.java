package com.efulltech.efulleatery.table.models;

public class Snacks {

    private int snack_id;
    private String snack_name;

    public Snacks(int snack_id, String snack_name) {
        this.snack_id = snack_id;
        this.snack_name = snack_name;
    }

    public int getSnack_id() {
        return snack_id;
    }

    public String getSnack_name() {
        return snack_name;
    }

    public void setSnack_id(int snack_id) {
        this.snack_id = snack_id;
    }

    public void setSnack_name(String snack_name) {
        this.snack_name = snack_name;
    }
}
