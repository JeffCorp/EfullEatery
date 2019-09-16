package com.efulltech.efulleatery.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.ChairCardAdapter;
import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.table.models.CardItem;

import java.util.ArrayList;

public class ChairsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChairCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView occupied;
    private Toolbar toolbar;
    private String getTableName, getChairName;
    public SharedPreferences sharedPreferences, preferences;
    private Integer chairs_no, table_id;
    private ImageButton add_chairs, delete_chair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chairs);

        final ArrayList<CardItem> cardItems = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar_chairs);

        //Initializing sharedPreferences
        sharedPreferences = getSharedPreferences("Chair_pref", MODE_PRIVATE);
        preferences = getSharedPreferences("table_info", MODE_PRIVATE);

        getTableName = preferences.getString("table_name", "No table");
        toolbar.setTitle(getTableName);

        setSupportActionBar(toolbar);


        add_chairs = (ImageButton) findViewById(R.id.add_chair);
        delete_chair = (ImageButton)findViewById(R.id.delete_chair);

        mRecyclerView = findViewById(R.id.chair_recycler);
        mLayoutManager = new GridLayoutManager(this, 2);
        mAdapter = new ChairCardAdapter(cardItems);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ChairCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ChairsActivity.this, MenuActivity.class);
                intent.putExtra("Table", getTableName);
                intent.putExtra("Chair", "Chair " + (position + 1));
                getChairName = "Chair " + (position + 1);
                sharedPreferences.edit().putString("Chair", getChairName).apply();
                startActivity(intent);
            }

            @Override
            public void onMenuDotsClicked(int position) {
                Intent intent = new Intent(ChairsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        final TablesDBHelper tablesDBHelper = new TablesDBHelper(ChairsActivity.this);
        SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
        Cursor cursor = tablesDBHelper.readTables(sqLiteDatabase);
        while (cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex(TablesContract.TablesEntry.TABLE_ID)).equals(getTableName)){
                chairs_no = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.CHAIRS_NO));
                table_id = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.ID));
            }
        }


        //Edit in activity_chairs layout
        for (int i = 1; i <= chairs_no; i++){
            cardItems.add(new CardItem(R.drawable.ic_restaurant_menu_black_24dp, "Chair " + i, "Active"));
        }




        add_chairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = true;
                TablesDBHelper tablesDBHelper = new TablesDBHelper(ChairsActivity.this);
                SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
                Cursor cursor = tablesDBHelper.readTables(sqLiteDatabase);
                int chair_update = chairs_no + 1;

                while (cursor.moveToNext()){
                    isUpdated = tablesDBHelper.updateChairs(table_id, getTableName, chair_update, 1);

                }
                if (isUpdated){
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(ChairsActivity.this, "New chair added", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChairsActivity.this, "Unable to add chair", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_chair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = true;
                TablesDBHelper tbh = new TablesDBHelper(ChairsActivity.this);
                SQLiteDatabase db = tbh.getReadableDatabase();
                Cursor cursor1 = tbh.readTables(db);
                int chair_decr = chairs_no - 1;

                while (cursor1.moveToNext()){
                    if (cursor1.getInt(cursor1.getColumnIndex(TablesContract.TablesEntry.CHAIRS_NO)) >= 1){
                        isDeleted = tbh.updateChairs(table_id,getTableName, chair_decr, 1);
                    }else {
                        isDeleted = tbh.updateChairs(table_id,getTableName,chair_decr,0);
                    }
                }
                if (isDeleted){
                    finish();
                    startActivity(getIntent());
                    Toast.makeText(ChairsActivity.this, "Last Chair deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ChairsActivity.this, "Unable to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        cardItems.add(new CardItem(R.drawable.ic_restaurant_menu, "Chair 2", "supported"));
//        cardItems.add(new CardItem(R.drawable.ic_restaurant_menu, "Chair 3", "supported"));
//        cardItems.add(new CardItem(R.drawable.ic_restaurant_menu, "Chair 4", "supported"));
//        cardItems.add(new CardItem(R.drawable.ic_restaurant_menu, "Chair 5", "supported"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(ChairsActivity.this, RecyclerTable.class));
    }
}
