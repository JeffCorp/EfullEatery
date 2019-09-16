package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.AuthContract;
import com.efulltech.efulleatery.contract.db.FoodContract;
import com.efulltech.efulleatery.contract.db.ItemsContract;

public class MenuDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "menu1_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+ FoodContract.FoodEntry.TABLE_NAME +
            "( " + FoodContract.FoodEntry.FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ FoodContract.FoodEntry.FOOD_NAME +" text,"+ FoodContract.FoodEntry.FOOD_PRICE +" number, "+
            FoodContract.FoodEntry.FOOD_QUANTITY +" number);";

    public static final String DROP_TABLE = "drop table if exists "+ FoodContract.FoodEntry.TABLE_NAME;


    public MenuDBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("Table operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addFood(String name, String quantity, String price, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodContract.FoodEntry.FOOD_NAME, name);
        contentValues.put(FoodContract.FoodEntry.FOOD_QUANTITY, quantity);
        contentValues.put(FoodContract.FoodEntry.FOOD_PRICE, price);

        sqLiteDatabase.insert(FoodContract.FoodEntry.TABLE_NAME,null, contentValues);

        Log.d("Database operations", "Item added");
    }

    public Cursor readMenuFood(SQLiteDatabase database){
        String[] projections = {FoodContract.FoodEntry.FOOD_ID, FoodContract.FoodEntry.FOOD_NAME, FoodContract.FoodEntry.FOOD_PRICE, FoodContract.FoodEntry.FOOD_QUANTITY};

        Cursor cursor = database.query(FoodContract.FoodEntry.TABLE_NAME,projections,null,null,null,null,null);

        return cursor;
    }
}
