package com.efulltech.efulleatery.table.models;

public class AllMenuItems {
    private int food_name_id;
    private String food_name_menu;
    private Double food_price_menu;
    private String food_info_menu;
    private String food_category;
    private int menu_image_id;


    public AllMenuItems(int food_name_id, String food_name_menu, Double food_price_menu, String food_info_menu, String food_category, int menu_image_id) {
        this.food_name_id = food_name_id;
        this.food_name_menu = food_name_menu;
        this.food_price_menu = food_price_menu;
        this.food_info_menu = food_info_menu;
        this.food_category = food_category;
        this.menu_image_id = menu_image_id;
    }

    public int getFood_name_id() {
        return food_name_id;
    }

    public void setFood_name_id(int food_name_id) {
        this.food_name_id = food_name_id;
    }

    public String getFood_name_menu() {
        return food_name_menu;
    }

    public void setFood_name_menu(String food_name_menu) {
        this.food_name_menu = food_name_menu;
    }

    public Double getFood_price_menu() {
        return food_price_menu;
    }

    public void setFood_price_menu(Double food_price_menu) {
        this.food_price_menu = food_price_menu;
    }

    public String getFood_info_menu() {
        return food_info_menu;
    }

    public void setFood_info_menu(String food_info_menu) {
        this.food_info_menu = food_info_menu;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public int getMenu_image_id() {
        return menu_image_id;
    }

    public void setMenu_image_id(int menu_image_id) {
        this.menu_image_id = menu_image_id;
    }
}
