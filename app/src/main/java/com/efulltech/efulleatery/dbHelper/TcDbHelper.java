package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.TableChairsContract;
import com.efulltech.efulleatery.contract.db.TablesContract;

public class TcDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tc_db";
    public static final Integer DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table " + TableChairsContract.TableChairsEntry.TC_TABLE_NAME + " ( " + TableChairsContract.TableChairsEntry.TABLES_ID + " text, " +
            TableChairsContract.TableChairsEntry.CHAIRS_ID + " text, "+ TableChairsContract.TableChairsEntry.TC_HAS_ORDERS + " number);";

    public static final String DROP_TABLE = "drop table if exists " + TableChairsContract.TableChairsEntry.TC_TABLE_NAME;

    public TcDbHelper(Context context){
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

    public void addTables(String table_id, String chair_id, int has_orders, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableChairsContract.TableChairsEntry.TABLES_ID, table_id);
        contentValues.put(TableChairsContract.TableChairsEntry.CHAIRS_ID, chair_id);
        contentValues.put(TableChairsContract.TableChairsEntry.TC_HAS_ORDERS, has_orders);
        database.insert(TableChairsContract.TableChairsEntry.TC_TABLE_NAME, null, contentValues);

        Log.d("Database activity", "Table added");
    }

    public Cursor readChairs(SQLiteDatabase database){
        String[] projections = {TableChairsContract.TableChairsEntry.TABLES_ID,
                TableChairsContract.TableChairsEntry.CHAIRS_ID, TableChairsContract.TableChairsEntry.TC_HAS_ORDERS};
        Cursor cursor = database.query(TableChairsContract.TableChairsEntry.TC_TABLE_NAME, projections, null, null,null,null,null);
        return cursor;
    }
}
