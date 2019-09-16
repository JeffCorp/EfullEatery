package com.efulltech.efulleatery.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.efulltech.efulleatery.contract.db.AuthContract;

public class AuthDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "auth_db";
    public static final int DATABASE_VERSION = 3;

    public static final String CREATE_TABLE = "create table "+ AuthContract.AuthEntry.TABLE_NAME+
            "( "+ AuthContract.AuthEntry.USER_ID+" number,"+ AuthContract.AuthEntry.NAME+" text, "+
            AuthContract.AuthEntry.EMAIL+" text, "+ AuthContract.AuthEntry.PHONE_NUMBER+" number, "+
            AuthContract.AuthEntry.PASSWORD+" varchar );";

    public static final String DROP_TABLE = "drop table if exists "+ AuthContract.AuthEntry.TABLE_NAME;

    public AuthDbHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.d("Database operations", "Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addUser(int id, String name, String email, String phone_number,String password, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AuthContract.AuthEntry.USER_ID, id);
        contentValues.put(AuthContract.AuthEntry.NAME, name);
        contentValues.put(AuthContract.AuthEntry.EMAIL, email);
        contentValues.put(AuthContract.AuthEntry.PHONE_NUMBER, phone_number);
        contentValues.put(AuthContract.AuthEntry.PASSWORD, password);

        database.insert(AuthContract.AuthEntry.TABLE_NAME,null, contentValues);

        Log.d("Database operations", "Row inserted");
    }

    public Cursor readContacts(SQLiteDatabase database){

        String[] projections = {AuthContract.AuthEntry.EMAIL, AuthContract.AuthEntry.PASSWORD};

        Cursor cursor = database.query(AuthContract.AuthEntry.TABLE_NAME, projections, null, null,
        null, null, null);
        return cursor;
    }
}
