package com.efulltech.efulleatery.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.table.models.CardItem;
import com.efulltech.efulleatery.R;

import java.util.ArrayList;

public class RecyclerTable extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView occupied;
    private Integer has_orders;
    private Button approve, decline;
    private EditText chairs_number;
    private String h_o;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_table);

        occupied = (TextView) findViewById(R.id.occupied);

        Toolbar toolbar = findViewById(R.id.toolbar_tables);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_orange);



//        for (int i = 1; i <= 10; i++){
//            String cardNo = "Table " + i;
//            cardItems.add(new CardItem(R.drawable.dine, cardNo, "Not occupied"));
//        }

        final ArrayList<CardItem> cardItems = new ArrayList<>();
        TablesDBHelper tablesDBHelper = new TablesDBHelper(RecyclerTable.this);
        final SQLiteDatabase database = tablesDBHelper.getReadableDatabase();

        final Cursor cursor = tablesDBHelper.readTables(database);

        RelativeLayout relativeLayout = findViewById(R.id.empty_table_message);


        if (cursor.getCount() > 1){
            while (cursor.moveToNext()){
                String table_id = cursor.getString(cursor.getColumnIndex(TablesContract.TablesEntry.TABLE_ID));
                has_orders = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.HAS_ORDERS));

                if (has_orders == 0){
                    h_o = "Not occupied";
//                occupied.setTextColor(getResources().getColor(R.color.colorAccent));

                }else {
                    h_o = "Occupied";
//                occupied.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                cardItems.add(new CardItem(R.drawable.ic_restaurant, table_id, h_o));

            }
        }else {
            relativeLayout.animate().alpha(1).setDuration(2000).setStartDelay(100);
        }

//

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(RecyclerTable.this, 2);
        mAdapter = new CardAdapter(cardItems, RecyclerTable.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





        mAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                final Intent intent = new Intent(RecyclerTable.this, ChairsActivity.class);
                preferences = getSharedPreferences("table_info", MODE_PRIVATE);
                preferences.edit().putString("table_name", "Table " + (position + 1)).apply();
                intent.putExtra("table", "Table " + (position + 1) );
                final String table_name = "Table " + (position + 1);
                TablesDBHelper tablesDBHelper = new TablesDBHelper(RecyclerTable.this);
                SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
                Cursor cursor1 = tablesDBHelper.readTables(sqLiteDatabase);
                while (cursor1.moveToNext()){
                    if (cursor1.getString(cursor1.getColumnIndex(TablesContract.TablesEntry.TABLE_ID)).equals(table_name)){
                        has_orders = cursor1.getInt(cursor1.getColumnIndex(TablesContract.TablesEntry.HAS_ORDERS));
                    }
                }
                if (has_orders < 1){
                    final Dialog dialog = new Dialog(RecyclerTable.this);
                    dialog.setContentView(R.layout.dialog_chair_setup);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    approve = dialog.findViewById(R.id.go);
                    decline = dialog.findViewById(R.id.cancel);
                    chairs_number = dialog.findViewById(R.id.chairs_number);




                    approve.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            boolean isUpdated = true;
                            final String ch_no = chairs_number.getText().toString();
                            final Integer chairs_no = Integer.parseInt(ch_no);
                            TablesDBHelper tablesDBHelper = new TablesDBHelper(RecyclerTable.this);
                            SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
                            Cursor cursor1 = tablesDBHelper.readTables(sqLiteDatabase);
                            int id;
                            while (cursor1.moveToNext()){
                                if (!chairs_number.equals(0)){
                                    id = (position + 1);
                                    isUpdated = tablesDBHelper.updateChairs(id,table_name,chairs_no,1);
                                }
                            }
                            if (isUpdated){
                                Toast.makeText(RecyclerTable.this, chairs_no + " Chairs have been setup ", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RecyclerTable.this, "Chairs not updated", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                            startActivity(intent);
                        }
                    });

                    decline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           dialog.cancel();
                        }
                    });

                    dialog.show();
                }else {
                    while (cursor1.moveToNext()){
                        if (cursor1.getString(cursor1.getColumnIndex(TablesContract.TablesEntry.TABLE_ID)).equals(table_name)){
                        has_orders = cursor1.getInt(cursor1.getColumnIndex(TablesContract.TablesEntry.HAS_ORDERS));
                        }
                    }
                    finish();
                    startActivity(intent);
                }
//                startActivity(intent);
            }

            @Override
            public void onMenuDotsClicked(int position) {
                Intent intent = new Intent(RecyclerTable.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
