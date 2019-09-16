package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.TableOrdersContract;
import com.efulltech.efulleatery.contract.db.TablesContract;

public class TableOrderDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "table_orders_db_sql";
    public static final Integer DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table " + TableOrdersContract.TableOrdersEntry.TABLE_NAME + " ( " + TableOrdersContract.TableOrdersEntry.ORDER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TableOrdersContract.TableOrdersEntry.TABLE_ID + " text, "+ TableOrdersContract.TableOrdersEntry.CHAIR_ID + " text," +
            TableOrdersContract.TableOrdersEntry.MENU_ID + " number, " + TableOrdersContract.TableOrdersEntry.MENU_PRICE + " number, " +
            TableOrdersContract.TableOrdersEntry.QUANTITY + " number," +
            TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT + " number, " + TableOrdersContract.TableOrdersEntry.HAS_PAID + " boolean );";

    public static final String DROP_TABLE = "drop table if exists " + TableOrdersContract.TableOrdersEntry.TABLE_NAME;

    public TableOrderDBHelper(Context context){
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
     Log.d("Table operation", "Table upgraded");
    }

    public void addOrders(String table_id, String chair_id, int menu_id, Double menu_price, int order_quantity, Double payable_amount, boolean has_paid, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableOrdersContract.TableOrdersEntry.TABLE_ID, table_id);
        contentValues.put(TableOrdersContract.TableOrdersEntry.CHAIR_ID, chair_id);
        contentValues.put(TableOrdersContract.TableOrdersEntry.MENU_ID, menu_id);
        contentValues.put(TableOrdersContract.TableOrdersEntry.MENU_PRICE, menu_price);
        contentValues.put(TableOrdersContract.TableOrdersEntry.QUANTITY, order_quantity);
        contentValues.put(TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT, payable_amount);
        contentValues.put(TableOrdersContract.TableOrdersEntry.HAS_PAID, has_paid);

        sqLiteDatabase.insert(TableOrdersContract.TableOrdersEntry.TABLE_NAME, null, contentValues);
        Log.d("Database operations", "Item added");
    }

    public Cursor readTableOrders(SQLiteDatabase database){
        String[] projections = {TableOrdersContract.TableOrdersEntry.ORDER_ID, TableOrdersContract.TableOrdersEntry.TABLE_ID, TableOrdersContract.TableOrdersEntry.CHAIR_ID, TableOrdersContract.TableOrdersEntry.MENU_ID, TableOrdersContract.TableOrdersEntry.MENU_PRICE,
                TableOrdersContract.TableOrdersEntry.QUANTITY, TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT, TableOrdersContract.TableOrdersEntry.HAS_PAID};

        Cursor  cursor = database.query(TableOrdersContract.TableOrdersEntry.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    public Cursor sendToGeneralOrders(SQLiteDatabase database){
        String[] projections = {TableOrdersContract.TableOrdersEntry.TABLE_ID, TableOrdersContract.TableOrdersEntry.CHAIR_ID, TableOrdersContract.TableOrdersEntry.MENU_ID, TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT};
        Cursor cursor = database.query(TableOrdersContract.TableOrdersEntry.TABLE_NAME, projections, null, null, null, null, null);

        return cursor;
    }

    public Integer deleteOrders(String order_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TableOrdersContract.TableOrdersEntry.TABLE_NAME,"id = ? ", new String[] { order_id });
    }
}
