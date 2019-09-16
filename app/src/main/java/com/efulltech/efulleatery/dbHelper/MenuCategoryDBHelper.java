package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.table.models.Category;

import java.util.ArrayList;

public class MenuCategoryDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "category_db";
    public static final Integer DATABASE_VERSION = 1;

    // Creates table for Menu item:- Food, Drinks ... with their corresponding id for relationships
    public static final String CREATE_TABLE = "create table "+ MenuCategoryContract.MenuCategory.TABLE_NAME + " ( " + MenuCategoryContract.MenuCategory.CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MenuCategoryContract.MenuCategory.CATEGORY_NAME + " text, " + MenuCategoryContract.MenuCategory.CATEGORY_DESCRIPTION + " text " +  " );";

    public static final String DROP_TABLE = "drop table if exists "+ MenuCategoryContract.MenuCategory.TABLE_NAME;

    public MenuCategoryDBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("Database operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addCategory(String category_name, String category_description, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MenuCategoryContract.MenuCategory.CATEGORY_NAME, category_name);
        contentValues.put(MenuCategoryContract.MenuCategory.CATEGORY_DESCRIPTION, category_description);

        sqLiteDatabase.insert(MenuCategoryContract.MenuCategory.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "Item added");
    }

    public Cursor readCategory(SQLiteDatabase database){
        String[] projections = {MenuCategoryContract.MenuCategory.CATEGORY_ID, MenuCategoryContract.MenuCategory.CATEGORY_NAME};

        Cursor cursor = database.query(MenuCategoryContract.MenuCategory.TABLE_NAME,projections,null,null,null,null,null);

        return cursor;
    }

    public Cursor readSelectedCategory(String cat_name, SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + MenuCategoryContract.MenuCategory.TABLE_NAME + " WHERE "
                + MenuCategoryContract.MenuCategory.CATEGORY_NAME + "=?", new String[]{cat_name});

        return cursor;
    }

    public Cursor readSelectedByCatId(String cat_id, SQLiteDatabase database){
        Cursor cursor = database.rawQuery("SELECT * FROM " + MenuCategoryContract.MenuCategory.TABLE_NAME + " WHERE "
                + MenuCategoryContract.MenuCategory.CATEGORY_ID + "=?", new String[]{cat_id});

        return cursor;
    }

    public ArrayList<String> getAllCategories(){
        ArrayList<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();


        try{
            String selectQuery = "SELECT * FROM " + MenuCategoryContract.MenuCategory.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String cat_name = cursor.getString(cursor.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
                    int cat_id = cursor.getInt(cursor.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_ID));
                    categories.add(cat_name);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
        return categories;
    }
}
