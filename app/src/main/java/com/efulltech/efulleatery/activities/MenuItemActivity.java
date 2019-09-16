package com.efulltech.efulleatery.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterAllOrders;
import com.efulltech.efulleatery.adapters.RecyclerAdapterMenuDrawer;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.table.models.AllMenuItems;
import com.efulltech.efulleatery.table.models.EmenuItems;

import java.util.ArrayList;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MenuItemActivity extends AppCompatActivity implements RecyclerAdapterMenuDrawer.OnNoteListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView pageIndicator;

    private List<EmenuItems> emenuItems;
    private List<AllMenuItems> allMenuItems = new ArrayList<>();

    private RecyclerView recyclerView, itemsRecycler;
    RecyclerAdapterMenuDrawer recyclerAdapterMenuDrawer;

    RecyclerAdapterAllOrders adapterAllOrders;

    private SharedPreferences sharedPreferences, sharedPrefs;

    private SharedPreferences preferences;
    private String Category_name;
    private FloatingTextButton floatingTextButton;
    private CardView cardView;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        toolbar = findViewById(R.id.toolbar_menu_items);
        sharedPreferences = getSharedPreferences("item_drawer", MODE_PRIVATE);
        preferences = getSharedPreferences("my_options", MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout_menu);

        emenuItems = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewMenu);
        itemsRecycler = findViewById(R.id.itemsRecycler);
        floatingTextButton = (FloatingTextButton) findViewById(R.id.action_button);
        cardView = (CardView) findViewById(R.id.search_drawer);
        search = (EditText) findViewById(R.id.search_area);

        setSupportActionBar(toolbar);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        if (preferences.getString("selected", "nothing selected") != "nothing selected"){
            actionBar.setTitle("Menu Items (" + preferences.getString("selected", "nothing") + ") " );
        }else{
            actionBar.setTitle("Menu Items");
        }


        recyclerAdapterMenuDrawer = new RecyclerAdapterMenuDrawer(this,emenuItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapterMenuDrawer);

        emenuItems.add(new EmenuItems("Show all"));
//        emenuItems.add(new EmenuItems("Foods"));
//        emenuItems.add(new EmenuItems("Drinks"));
//        emenuItems.add(new EmenuItems("Snacks"));
//        emenuItems.add(new EmenuItems("Fruits"));
        MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(this);
        SQLiteDatabase mdb = menuCategoryDBHelper.getReadableDatabase();
        Cursor c = menuCategoryDBHelper.readCategory(mdb);

        while (c.moveToNext()){
            emenuItems.add(new EmenuItems(c.getString(c.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME))));
        }

        adapterAllOrders = new RecyclerAdapterAllOrders(this, allMenuItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemsRecycler.setLayoutManager(linearLayoutManager);
        itemsRecycler.setAdapter(adapterAllOrders);

        floatingTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        final String state;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardView.animate().translationX(100).setDuration(1000).setStartDelay(500);
                    onChanged(true);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setFocusableInTouchMode(true);
            }
        });



        String food_name, food_info, food_cat;
        Double food_price;
        int item_image, food_name_id;

        sharedPrefs = getSharedPreferences("item_id", MODE_PRIVATE);


        String food_cat_id = null;
        SQLiteDatabase sqLiteDatabase = menuCategoryDBHelper.getReadableDatabase();
        Cursor cursor2 = menuCategoryDBHelper.readSelectedByCatId("1", sqLiteDatabase);

        while (c.moveToNext()){
            food_cat_id = cursor2.getString(cursor2.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
        }

        // Responsible for displaying the menu on the menu page

        if (preferences.getString("selected", "no_result") == "Show all"){
            MenuItemsDBHelper itemsDBHelper = new MenuItemsDBHelper(MenuItemActivity.this);
            SQLiteDatabase db = itemsDBHelper.getReadableDatabase();
            Cursor cursor1 = itemsDBHelper.readItems(db);
            int quantity;

            while (cursor1.moveToNext()){
                food_name = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
                food_price = 250.05;
                food_info = "Made with Love";
                food_cat = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
                item_image = cursor1.getInt(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_IMAGE_ID));
                food_name_id = cursor1.getInt(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
                allMenuItems.add(new AllMenuItems(food_name_id, food_name, food_price, food_info, food_cat_id, item_image));
            }

        }else if (preferences.getString("selected", "no_result") != "no_result"){
            MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(MenuItemActivity.this);
            SQLiteDatabase database = menuItemsDBHelper.getReadableDatabase();
            Cursor cursor = menuItemsDBHelper.readItemsByCatId(database, preferences.getString("selected", "no_result"));

            while (cursor.moveToNext()){
                food_name = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
                food_price = 250.05;
                food_info = "Made with Love";
                food_cat = cursor.getString(cursor.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
                item_image = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_IMAGE_ID));
                food_name_id = cursor.getInt(cursor.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
                allMenuItems.add(new AllMenuItems(food_name_id, food_name, food_price, food_info, food_cat, item_image));
            }


        }


    }


    // Add table prefixes to tables
    @Override
    public void OnNoteClick(int position) {
        Log.d("Testing", emenuItems.get(position).getItemName());
//        intent.putExtra("itemClick", emenuItems.get(position).getItemName());

        preferences = getSharedPreferences("my_options", MODE_PRIVATE);
        preferences.edit().putString("selected", emenuItems.get(position).getItemName()).apply();

        allMenuItems.clear();


        //Removed doInBackground content from here
        new MyAsyncTask(MenuItemActivity.this).execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void onChanged(boolean isChecked){
        if (isChecked){
            cardView.animate().translationX(100).setDuration(500).setStartDelay(50);
            isChecked = false;
        }else {
            cardView.animate().translationX(600).setDuration(1000).setStartDelay(50);
            isChecked = true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(MenuItemActivity.this, MenuActivity.class));
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, List>{

        private ProgressDialog progressDialog = null;
        private Context mContext = null;

        public MyAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();

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
        protected List<AllMenuItems> doInBackground(Void... voids) {

            //Loads the data gotten from the MenuItems (Orders) DB helper into the List<AllItems> array

            Intent intent = new Intent(MenuItemActivity.this, MenuItemActivity.class);
            MenuItemsDBHelper itemsDBHelper = new MenuItemsDBHelper(MenuItemActivity.this);
            SQLiteDatabase db = itemsDBHelper.getReadableDatabase();
            Cursor cursor1 = itemsDBHelper.readItemsByCatId(db, preferences.getString("selected", "no_result"));


            String food_name, food_info, food_cat_id = null;
            int quantity;
            Double food_price;
            int item_image, food_name_id;

            MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(MenuItemActivity.this);
            SQLiteDatabase database = menuCategoryDBHelper.getReadableDatabase();
            Cursor cursor = menuCategoryDBHelper.readSelectedByCatId("1", database);

            while (cursor.moveToNext()){
                food_cat_id = cursor.getString(cursor.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
            }

            while (cursor1.moveToNext()){

//                    food_cat = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.CATEGORY_ID));
//                Cursor cursor = menuCategoryDBHelper.readSelectedByCatId(food_cat, database);
//
//                while (cursor.moveToNext()) {
                    food_name_id = cursor1.getInt(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));
                    food_name = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
                    food_price = 250.05;
                    food_info = "Made with Love";
                    item_image = cursor1.getInt(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_IMAGE_ID));
                    allMenuItems.add(new AllMenuItems(food_name_id, food_name, food_price, food_info, food_cat_id, item_image));
//                }
            }
            finish();
            startActivity(intent);

            return allMenuItems;
        }

        @Override
        protected void onPostExecute(List list) {
//            super.onPostExecute(list);

            progressDialog.dismiss();
        }
    }
}
