package com.efulltech.efulleatery.table.models;

public class StockItems {
    public String stock_item_name, stock_price, stock_start_quantity, stock_current_quantity, stock_category;

    public StockItems(String stock_item_name, String stock_price, String stock_start_quantity, String stock_current_quantity, String stock_category) {
        this.stock_item_name = stock_item_name;
        this.stock_price = stock_price;
        this.stock_start_quantity = stock_start_quantity;
        this.stock_current_quantity = stock_current_quantity;
        this.stock_category = stock_category;
    }

    public String getStock_item_name() {
        return stock_item_name;
    }

    public void setStock_item_name(String stock_item_name) {
        this.stock_item_name = stock_item_name;
    }

    public String getStock_price() {
        return stock_price;
    }

    public void setStock_price(String stock_price) {
        this.stock_price = stock_price;
    }

    public String getStock_start_quantity() {
        return stock_start_quantity;
    }

    public void setStock_start_quantity(String stock_start_quantity) {
        this.stock_start_quantity = stock_start_quantity;
    }

    public String getStock_current_quantity() {
        return stock_current_quantity;
    }

    public void setStock_current_quantity(String stock_current_quantity) {
        this.stock_current_quantity = stock_current_quantity;
    }

    public String getStock_category() {
        return stock_category;
    }

    public void setStock_category(String stock_category) {
        this.stock_category = stock_category;
    }
}
