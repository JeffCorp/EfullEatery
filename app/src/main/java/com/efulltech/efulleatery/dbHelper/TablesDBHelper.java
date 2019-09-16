package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.TablesContract;

public class TablesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "tables_db1";
    public static final Integer DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table " + TablesContract.TablesEntry.TABLE_NAME + " ( " + TablesContract.TablesEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TablesContract.TablesEntry.TABLE_ID + " text, "+ TablesContract.TablesEntry.CHAIRS_NO + " number," + TablesContract.TablesEntry.HAS_ORDERS + " number);";

    public static final String DROP_TABLE = "drop table if exists " + TablesContract.TablesEntry.TABLE_NAME;

    public TablesDBHelper(Context context){
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
        Log.d("Database operations", "Table Upgraded");

    }

    public void addTables(String table_id, int chairs_no, int has_orders, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesContract.TablesEntry.TABLE_ID, table_id);
        contentValues.put(TablesContract.TablesEntry.CHAIRS_NO, chairs_no);
        contentValues.put(TablesContract.TablesEntry.HAS_ORDERS, has_orders);
        database.insert(TablesContract.TablesEntry.TABLE_NAME, null, contentValues);

        Log.d("Database activity", "Table added");
    }


    public Cursor readTables(SQLiteDatabase database){
        String[] projections = {TablesContract.TablesEntry.ID, TablesContract.TablesEntry.TABLE_ID, TablesContract.TablesEntry.CHAIRS_NO, TablesContract.TablesEntry.HAS_ORDERS};
        Cursor cursor = database.query(TablesContract.TablesEntry.TABLE_NAME, projections, null, null,null,null,null);

        return cursor;
    }

    public Cursor readTablesColor(SQLiteDatabase database, String has_orders){
        Cursor cursor = database.rawQuery("SELECT * FROM " + TablesContract.TablesEntry.TABLE_NAME + " WHERE " +
                TablesContract.TablesEntry.HAS_ORDERS + " = ?", new String[]{has_orders});
        return cursor;
    }

    public boolean updateTables(@Nullable int id, String table_id,  int chairs_no, int has_orders){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesContract.TablesEntry.ID, id);
        contentValues.put(TablesContract.TablesEntry.TABLE_ID, table_id);
        contentValues.put(TablesContract.TablesEntry.CHAIRS_NO, chairs_no);
        contentValues.put(TablesContract.TablesEntry.HAS_ORDERS, has_orders);

        db.update(TablesContract.TablesEntry.TABLE_NAME, contentValues, "table_id = ?", new String[] { table_id });
        return true;
    }

    public boolean updateChairs(@Nullable int id, String table_id, int chairs_no, int has_orders){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesContract.TablesEntry.ID, id);
        contentValues.put(TablesContract.TablesEntry.TABLE_ID, table_id);
        contentValues.put(TablesContract.TablesEntry.CHAIRS_NO, chairs_no);
        contentValues.put(TablesContract.TablesEntry.HAS_ORDERS, has_orders);

        db.update(TablesContract.TablesEntry.TABLE_NAME, contentValues, "table_id = ?", new String[] { table_id });
        return true;
    }

    public boolean deleteChairs(@Nullable int id, String table_id, int chairs_no, int has_orders){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TablesContract.TablesEntry.ID, id);
        contentValues.put(TablesContract.TablesEntry.TABLE_ID, table_id);
        contentValues.put(TablesContract.TablesEntry.CHAIRS_NO, chairs_no);
        contentValues.put(TablesContract.TablesEntry.HAS_ORDERS, has_orders);

        db.delete(TablesContract.TablesEntry.TABLE_NAME,"table_id = ?", new String[] { table_id});

        return true;
    }
}
