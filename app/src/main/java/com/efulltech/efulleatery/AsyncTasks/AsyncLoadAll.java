package com.efulltech.efulleatery.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.table.models.AllMenuItems;

import java.util.List;

public class AsyncLoadAll extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private List<AllMenuItems> allMenuItems;
    private ProgressDialog progressDialog;

    public AsyncLoadAll(Context mContext, List<AllMenuItems> allMenuItems) {
        this.mContext = mContext;
        this.allMenuItems = allMenuItems;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);

        progressDialog.setTitle("Loading database");
        progressDialog.setMessage("please wait");
        progressDialog.setIndeterminate(false);

        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });

        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(mContext);
        SQLiteDatabase database = menuItemsDBHelper.getReadableDatabase();
        Cursor cursor = menuItemsDBHelper.readItems(database);

        MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(mContext);
        SQLiteDatabase db = menuCategoryDBHelper.getReadableDatabase();
        String food_cat = null;

        try {
            while (cursor.moveToNext()){
                int food_name_id = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
                String food_name = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
                Double food_price = cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_PRICE));
                String food_info = "Made with love";
                String food_category = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
                Cursor cursor1 = menuCategoryDBHelper.readSelectedByCatId(food_category, db);
                while (cursor1.moveToNext()){
                    food_cat = cursor1.getString(cursor1.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
                }

                int menu_image_id = R.drawable.food1;



                allMenuItems.add(new AllMenuItems(food_name_id, food_name,food_price,food_info,food_cat,menu_image_id));

                Log.d("data", food_name_id + food_name + food_price + food_info + food_cat);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
