package com.efulltech.efulleatery.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterOrders;
import com.efulltech.efulleatery.contract.db.FoodContract;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.TableOrdersContract;
import com.efulltech.efulleatery.dbHelper.MenuDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.dbHelper.TableOrderDBHelper;
import com.efulltech.efulleatery.table.models.OrdersAll;

import java.util.ArrayList;
import java.util.List;


public class ViewOrdersActivity extends AppCompatActivity {

    List<OrdersAll> ordersAll;
    RecyclerView totalOrdersRecycler;

    String view_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersAll = new ArrayList<>();
        totalOrdersRecycler = findViewById(R.id.orders_recycler);

        RecyclerAdapterOrders recyclerAdapterOrders = new RecyclerAdapterOrders(this, ordersAll);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        totalOrdersRecycler.setLayoutManager(layoutManager);
        totalOrdersRecycler.setAdapter(recyclerAdapterOrders);

        getSupportActionBar().setTitle("View orders");



        TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(this);
        SQLiteDatabase database = tableOrderDBHelper.getReadableDatabase();
        Cursor cursor = tableOrderDBHelper.sendToGeneralOrders(database);

        while (cursor.moveToNext()){
            String table_name = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.TABLE_ID));
            Integer items = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.MENU_ID));
            String price = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT));
            String chair_name = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.CHAIR_ID));


            String menu_name = getMenuName(items);

            ordersAll.add(new OrdersAll(table_name, chair_name, menu_name,price));


        }

        cursor.close();

    }
    // To get the name of the food item from the id.
    public String getMenuName (int menu_no){
        MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(ViewOrdersActivity.this);
        SQLiteDatabase sqLiteDatabase = menuItemsDBHelper.getReadableDatabase();
        Cursor cursor1 = menuItemsDBHelper.readItems(sqLiteDatabase);
        String menu_id_name = null;

        while (cursor1.moveToNext()){
            int item_id = cursor1.getInt(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_ID));

            if (menu_no == item_id){
                menu_id_name = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
            }
        }
        return menu_id_name;
    }

}
