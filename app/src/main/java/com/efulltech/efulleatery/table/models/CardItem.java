package com.efulltech.efulleatery.table.models;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class CardItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private ColorDrawable mColor;


    public CardItem (int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1 = text1;
//        mColor = color;
        mText2 = text2;
    }

    public int getImageResource(){
        return mImageResource;
    }

//    public ColorDrawable getmColor() {
//        return mColor;
//    }

//    public void setmColor(ColorDrawable mColor) {
//        this.mColor = mColor;
//    }

    public String getText1(){

        return mText1;
    }

    public String getText2 (){
        return mText2;
    }
}
