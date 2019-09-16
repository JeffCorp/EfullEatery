package com.efulltech.efulleatery.table.models;

public class Food {

    private int food_id;
    private int food_image_id;
    private String food_name;
    private String food_price;
    private String quantity;

    public Food(int food_id, int food_image_id, String food_name, String food_price, String quantity) {
        this.food_id = food_id;
        this.food_image_id = food_image_id;
        this.food_name = food_name;
        this.food_price = food_price;
        this.quantity = quantity;
    }

    public int getFood_id(){
        return food_id;
    }

    public int getFood_image_id() {
        return food_image_id;
    }

    public String getFood_price() {
        return food_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getFood_name(){
        return food_name;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
