package com.efulltech.efulleatery.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.efulltech.efulleatery.dbHelper.MenuDBHelper;
import com.efulltech.efulleatery.R;

public class UploadStockActivity extends AppCompatActivity {

    private EditText food_name, food_price, food_quantity;
    private Button upload_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_stock);

        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_quantity = findViewById(R.id.food_quantity);
        upload_food = findViewById(R.id.upload_food);

        upload_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = food_name.getText().toString();
                String price = food_price.getText().toString();
                String quantity = food_quantity.getText().toString();

                if(!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()){
                    MenuDBHelper menuDBHelper = new MenuDBHelper(UploadStockActivity.this);
                    SQLiteDatabase database = menuDBHelper.getWritableDatabase();
                    menuDBHelper.addFood(name,quantity,price,database);
                    menuDBHelper.close();

                    Toast.makeText(getApplicationContext(), "Food saved", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
