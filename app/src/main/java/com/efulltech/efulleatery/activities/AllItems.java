package com.efulltech.efulleatery.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.efulltech.efulleatery.AsyncTasks.AsyncLoadAll;
import com.efulltech.efulleatery.AsyncTasks.AsyncLoadMenu;
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

import com.efulltech.efulleatery.AsyncTasks.AsyncLoadCategory;

public class AllItems extends AppCompatActivity implements RecyclerAdapterMenuDrawer.OnNoteListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView pageIndicator;

    private List<EmenuItems> emenuItems;
    private List<com.efulltech.efulleatery.table.models.AllMenuItems> allMenuItems;

    private RecyclerView recyclerView, itemsRecycler;
    RecyclerAdapterMenuDrawer recyclerAdapterMenuDrawer;

    RecyclerAdapterAllOrders adapterAllOrders;

    private SharedPreferences sharedPreferences, sharedPrefs;

    private SharedPreferences preferences;
    private String Category_name;
    private FloatingTextButton floatingTextButton;
    private CardView cardView;
    private EditText search;
    private ActionBar actionBar;
    private int food_name_id;
    private String food_name;
    private Double food_price;
    private String food_info;
    private String food_category;
    private String food_cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        toolbar = findViewById(R.id.toolbar_menu_items);
        sharedPreferences = getSharedPreferences("item_drawer", MODE_PRIVATE);
        preferences = getSharedPreferences("my_options", MODE_PRIVATE);
        drawerLayout = findViewById(R.id.drawer_layout_menu);

        emenuItems = new ArrayList<>();
        allMenuItems = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        itemsRecycler = (RecyclerView) findViewById(R.id.itemsRecycler);
        floatingTextButton = (FloatingTextButton) findViewById(R.id.action_button);
        cardView = (CardView) findViewById(R.id.search_drawer);
        search = (EditText) findViewById(R.id.search_area);

        setSupportActionBar(toolbar);


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("Menu Items");


        //Adapter for the menu category in the menu drawer
        recyclerAdapterMenuDrawer = new RecyclerAdapterMenuDrawer(this,emenuItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapterMenuDrawer);

        emenuItems.add(new EmenuItems("Show all"));
        new AsyncLoadCategory(AllItems.this, emenuItems).execute();

        //Adapter for the menu lists
        adapterAllOrders = new RecyclerAdapterAllOrders(this, allMenuItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemsRecycler.setLayoutManager(linearLayoutManager);
        itemsRecycler.setAdapter(adapterAllOrders);


        new AsyncLoadAll(AllItems.this, allMenuItems).execute();


    }


    // Add table prefixes to tables
    @Override
    public void OnNoteClick(int position) {
        Log.d("Testing", emenuItems.get(position).getItemName());

        preferences = getSharedPreferences("my_options", MODE_PRIVATE);
        preferences.edit().putString("selected", emenuItems.get(position).getItemName()).apply();

        allMenuItems.clear();

        //Background task

            new AsyncLoadMenu(AllItems.this, allMenuItems, emenuItems.get(position).getItemName(), actionBar, adapterAllOrders, itemsRecycler, drawerLayout).execute();

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




}
