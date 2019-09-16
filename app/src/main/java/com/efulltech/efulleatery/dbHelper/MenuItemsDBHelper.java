package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.ItemsContract;

public class MenuItemsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "items";
    public static final int DATABASE_VERSION = 4;

    public static final String CREATE_TABLE = "create table "+ ItemsContract.ItemsEntry.TABLE_NAME_ITEMS +
            "( " + ItemsContract.ItemsEntry.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ItemsContract.ItemsEntry.ITEM_NAME +" text,"+
            ItemsContract.ItemsEntry.START_QUANTITY + " number, " +
            ItemsContract.ItemsEntry.ITEM_QUANTITY +" number, "+ ItemsContract.ItemsEntry.ITEM_PRICE + " number, " + ItemsContract.ItemsEntry.ITEM_IMAGE_ID + " number," +
            ItemsContract.ItemsEntry.CATEGORY_ID +" text);";

    public static final String DROP_TABLE = "drop table if exists "+ ItemsContract.ItemsEntry.TABLE_NAME_ITEMS;


    public MenuItemsDBHelper(Context context){
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

    public void addItem(String name,int s_quantity, int quantity, Double price, int cat_id, int image_id, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemsContract.ItemsEntry.ITEM_NAME, name);
        contentValues.put(ItemsContract.ItemsEntry.START_QUANTITY, s_quantity);
        contentValues.put(ItemsContract.ItemsEntry.ITEM_QUANTITY, quantity);
        contentValues.put(ItemsContract.ItemsEntry.ITEM_PRICE, price);
        contentValues.put(ItemsContract.ItemsEntry.CATEGORY_ID, cat_id);
        contentValues.put(ItemsContract.ItemsEntry.ITEM_IMAGE_ID, image_id);

        sqLiteDatabase.insert(ItemsContract.ItemsEntry.TABLE_NAME_ITEMS,null, contentValues);

        Log.d("Database operations", "Item added");
    }

    public Cursor readItems(SQLiteDatabase database){
        String[] projections = {ItemsContract.ItemsEntry.ITEM_ID, ItemsContract.ItemsEntry.ITEM_NAME, ItemsContract.ItemsEntry.ITEM_PRICE, ItemsContract.ItemsEntry.CATEGORY_ID, ItemsContract.ItemsEntry.START_QUANTITY, ItemsContract.ItemsEntry.ITEM_QUANTITY, ItemsContract.ItemsEntry.ITEM_IMAGE_ID};

        Cursor cursor = database.query(ItemsContract.ItemsEntry.TABLE_NAME_ITEMS,projections,null,null,null,null,null);

        return cursor;
    }

    public Cursor readItemsByCatId(SQLiteDatabase database, String string){
        String[] projections = {ItemsContract.ItemsEntry.ITEM_ID, ItemsContract.ItemsEntry.ITEM_NAME, ItemsContract.ItemsEntry.CATEGORY_ID, ItemsContract.ItemsEntry.ITEM_QUANTITY, ItemsContract.ItemsEntry.ITEM_IMAGE_ID};

        Cursor cursor = database.rawQuery("SELECT * FROM " + ItemsContract.ItemsEntry.TABLE_NAME_ITEMS +
                " WHERE " + ItemsContract.ItemsEntry.CATEGORY_ID + " =? ", new String[]{string});

        return cursor;
    }
}
