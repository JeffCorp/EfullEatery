package com.efulltech.efulleatery.AsyncTasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterAllOrders;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.table.models.AllMenuItems;

import java.util.List;

public class AsyncLoadMenu extends AsyncTask<List, Void, List> {

    private ProgressDialog progressDialog;
    private Context mContext;
    private List<AllMenuItems> allMenuItems;
    private String selected;
    private android.support.v7.app.ActionBar actionBar;
    private int food_name_id;
    private String food_name;
    private Double food_price;
    private String food_info;
    private String food_category;
    private String food_cat;
    RecyclerAdapterAllOrders adapterAllOrders;
    private RecyclerView recyclerView, itemsRecycler;

    private DrawerLayout drawerLayout;

    private Cursor cr, cursor;
    private SQLiteDatabase db;
    private int menu_image_id;
    private MenuCategoryDBHelper menuCategoryDBHelper;

    public AsyncLoadMenu(Context mContext, List<AllMenuItems> allMenuItems, String selected, ActionBar actionBar, RecyclerAdapterAllOrders adapterAllOrders, RecyclerView itemsRecycler, DrawerLayout drawerLayout) {
        this.mContext = mContext;
        this.allMenuItems = allMenuItems;
        this.selected = selected;
        this.actionBar = actionBar;
        this.adapterAllOrders = adapterAllOrders;
        this.itemsRecycler = itemsRecycler;
        this.drawerLayout = drawerLayout;
    }

    @Override
    protected List doInBackground(List... lists) {
        if (selected == "Show all"){
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

//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }else {


            MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(mContext);
            SQLiteDatabase database = menuItemsDBHelper.getReadableDatabase();
            Cursor cursor = menuItemsDBHelper.readItems(database);

            MenuCategoryDBHelper menuCategoryDBHelper;
            menuCategoryDBHelper = new MenuCategoryDBHelper(mContext);
            SQLiteDatabase db = menuCategoryDBHelper.getReadableDatabase();
//        String food_cat = null;
            String food_cat_name = null;

            Cursor c = menuCategoryDBHelper.readSelectedCategory(selected, db);

            while (c.moveToNext()) {
                food_cat_name = c.getString(c.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_ID));

                Log.d("Category", food_cat_name);
            }


            Cursor cr = menuItemsDBHelper.readItemsByCatId(database, food_cat_name);
            while (cr.moveToNext()) {
//            while (cursor.moveToNext()) {
                food_name_id = cr.getInt(cr.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
                food_name = cr.getString(cr.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
                food_price = cr.getDouble(cr.getColumnIndex(ItemsContract.ItemsEntry.ITEM_PRICE));
                food_info = "Made with love";
                food_category = cr.getString(cr.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
                Cursor cursor1 = menuCategoryDBHelper.readSelectedByCatId(food_category, db);
                while (cursor1.moveToNext()) {
                    food_cat = cursor1.getString(cursor1.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
                }

                int menu_image_id = R.drawable.food1;

                allMenuItems.add(new AllMenuItems(food_name_id, food_name, food_price, food_info, food_cat, menu_image_id));

                Log.d("data", food_name_id + food_name + food_price + food_info + food_cat);
//            }

            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(mContext);

        progressDialog.setTitle("Loading");
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
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        actionBar.setTitle("Menu Items (" + selected + ") " );
        drawerLayout.closeDrawers();
        //Send db result to UI
        itemsRecycler.setAdapter(new RecyclerAdapterAllOrders(mContext, allMenuItems));
        progressDialog.dismiss();
    }
}
