package com.efulltech.efulleatery.table.models;

public class SettingsItems {

    private int image_id;
    private String settings_text;


    public SettingsItems(int image_id, String settings_text) {
        this.image_id = image_id;
        this.settings_text = settings_text;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getSettings_text() {
        return settings_text;
    }

    public void setSettings_text(String settings_text) {
        this.settings_text = settings_text;
    }
}
