package com.efulltech.efulleatery.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.sharedPref.SharedPrefConfig;

public class OperateChoice extends AppCompatActivity {

    public Button btnOnline;
    public Button btnOffline;
    private SharedPrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_choice);

        prefConfig = new SharedPrefConfig(getApplicationContext());

        btnOnline = findViewById(R.id.btn_online);
        btnOffline = findViewById(R.id.btn_offline);

//        if (prefConfig.readOpsStatus()){
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }

//        btnOnline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OperateChoice.this, MenuActivity.class );// Will edit when I create an online login
//                startActivity(intent);
//
//                prefConfig.writeOpsStatus(true);
//                finish();
//            }
//        });

        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OperateChoice.this, MainActivity.class);
                startActivity(intent);

                Integer version = 1;

//                MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(OperateChoice.this);
//                SQLiteDatabase sqLiteDatabase = menuCategoryDBHelper.getWritableDatabase();
//                menuCategoryDBHelper.onUpgrade(sqLiteDatabase, version, version++);
//                menuCategoryDBHelper.addCategory("Foods", null, sqLiteDatabase);
//                menuCategoryDBHelper.addCategory("Drinks", null, sqLiteDatabase);
//                menuCategoryDBHelper.addCategory("Snacks", null, sqLiteDatabase);
//                menuCategoryDBHelper.addCategory("Fruits", null, sqLiteDatabase);
//                version++;
//
//                menuCategoryDBHelper.close();

//                MenuItemsDBHelper itemsDBHelper = new MenuItemsDBHelper(OperateChoice.this);
//                SQLiteDatabase db = itemsDBHelper.getWritableDatabase();
//                itemsDBHelper.addItem("Rice", 10, 250.05, "Foods", R.drawable.food1, db);
//                itemsDBHelper.addItem("Coke", 10, 250.05, "Drinks", R.drawable.burger_drink, db);
//                itemsDBHelper.addItem("Burrito", 10, 250.05, "Snacks", R.drawable.food2, db);
//                itemsDBHelper.addItem("Coconut", 10, 250.05, "Fruits", R.drawable.food3, db);
//                itemsDBHelper.close();



//                TablesDBHelper tablesDBHelper = new TablesDBHelper(OperateChoice.this);
//                SQLiteDatabase database = tablesDBHelper.getWritableDatabase();
//                tablesDBHelper.addTables(1,"Table 1", 0, 0, database);
//                tablesDBHelper.addTables(2,"Table 2", 0,0, database);
//                tablesDBHelper.addTables(3,"Table 3", 0, 0,database);
//                tablesDBHelper.addTables(4,"Table 4", 0, 0,database);
//                tablesDBHelper.addTables(5,"Table 5", 0, 0,database);
//                tablesDBHelper.addTables(6,"Table 6", 0, 0,database);
//                tablesDBHelper.addTables(7,"Table 7", 0, 0,database);
//                tablesDBHelper.addTables(8,"Table 8", 0, 0,database);
//                tablesDBHelper.addTables(9,"Table 9", 0, 0,database);
//                tablesDBHelper.addTables(10,"Table 10", 0, 0,database);
//
//                tablesDBHelper.close();

                Toast.makeText(OperateChoice.this, "Category table created", Toast.LENGTH_SHORT).show();


//                prefConfig.writeOpsStatus(true);
                finish();
            }
        });
    }
}
