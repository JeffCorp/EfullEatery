package com.efulltech.efulleatery.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterStock;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.table.models.StockItems;

import java.util.List;

public class AsyncLoadStock extends AsyncTask<List, Void, List> {

    private RecyclerAdapterStock adapterStock;
    private RecyclerView recyclerView;
    private Context mContext;
    private List<StockItems> stockItems;
    private ProgressDialog progressDialog;

    public AsyncLoadStock(RecyclerAdapterStock adapterStock, RecyclerView recyclerView, Context mContext, List<StockItems> stockItems) {
        this.adapterStock = adapterStock;
        this.recyclerView = recyclerView;
        this.mContext = mContext;
        this.stockItems = stockItems;
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
    protected List doInBackground(List... lists) {

        MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(mContext);
        SQLiteDatabase database = menuItemsDBHelper.getReadableDatabase();
        Cursor cursor = menuItemsDBHelper.readItems(database);

        MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(mContext);
        SQLiteDatabase db = menuCategoryDBHelper.getReadableDatabase();
        String food_cat = null;

//            try {
        while (cursor.moveToNext()){
            int food_name_id = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
            String food_name = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
            Double food_price = cursor.getDouble(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_PRICE));
            int s_quantity = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.START_QUANTITY));
            int c_quantity = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_QUANTITY));
            String food_category = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
            Cursor cursor1 = menuCategoryDBHelper.readSelectedByCatId(food_category, db);
            while (cursor1.moveToNext()){
                food_cat = cursor1.getString(cursor1.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
            }

            int menu_image_id = R.drawable.food1;

            stockItems.add(new StockItems(food_name, Double.toString(food_price), Integer.toString(s_quantity), Integer.toString(c_quantity), food_cat));



            Log.d("data", food_name_id + food_name + food_price + s_quantity + food_cat);
        }


        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        recyclerView.setAdapter(new RecyclerAdapterStock(mContext, stockItems));
        progressDialog.dismiss();
    }
}
