package com.efulltech.efulleatery.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterOrder;
import com.efulltech.efulleatery.contract.db.FoodContract;
import com.efulltech.efulleatery.contract.db.ItemsContract;
import com.efulltech.efulleatery.contract.db.TableOrdersContract;
import com.efulltech.efulleatery.dbHelper.MenuDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.dbHelper.TableOrderDBHelper;
import com.efulltech.efulleatery.table.models.Orders;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    ImageButton btnEditOrder;
    ImageButton btnCheckOut;
    ImageButton btnRefresh;
    Toolbar toptoolbar;

    private Boolean isDeleted = false;

    public String title, subtitle, mOrder_id;
    private List<Orders> ordersArray;

    List<Double> total;
    TextView total_price, grand_total_price;

    private RecyclerView order_recycler;
    RecyclerAdapterOrder recyclerAdapterOrder;
    private int order_id;

    private SharedPreferences sharedPreferences, preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


//        btnViewOrder = findViewById(R.id.viewOrder);
        btnEditOrder = (ImageButton) findViewById(R.id.editOrder);
        btnCheckOut = (ImageButton) findViewById(R.id.checkOut);
        toptoolbar = findViewById(R.id.toolbar1);

        ordersArray = new ArrayList<>();

        order_recycler = findViewById(R.id.order_recycler);

        recyclerAdapterOrder = new RecyclerAdapterOrder(this, ordersArray);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        order_recycler.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(order_recycler);
        order_recycler.setAdapter(recyclerAdapterOrder);


        sharedPreferences = getSharedPreferences("table_info", MODE_PRIVATE);
        preferences = getSharedPreferences("Chair_pref", MODE_PRIVATE);



//        if (getIntent().hasExtra("Chair")){
            title = sharedPreferences.getString("table_name", "No table");
            subtitle = preferences.getString("Chair", "No Chair");
            TextView textView = findViewById(R.id.table_title);
//            getSupportActionBar().setTitle(message);
//            setSupportActionBar(toptoolbar);
//            getSupportActionBar().setTitle(message);
            toptoolbar.setTitle(title);
            toptoolbar.setSubtitle(subtitle);

            setSupportActionBar(toptoolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
//        }


        btnEditOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = getIntent().getStringExtra("Table");
                Intent intent = new Intent(MenuActivity.this, AllItems.class);
                intent.putExtra("table_name", title);
                intent.putExtra("subtitle", subtitle);
                finish();
                startActivity(intent);
            }
        });


        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CheckOutActivity.class);
                intent.putExtra("table", title);
                intent.putExtra("subtitle", subtitle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        String table_id, chair_id, mOrder_id;
        Integer menu_id;
        Double menu_price;
        Integer quantity;
        Double amount_payable;
        Boolean has_paid;
        total = new ArrayList<>();


        total_price = (TextView) findViewById(R.id.total_price);
        grand_total_price = (TextView) findViewById(R.id.grand_total_price);

        TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(this);
        SQLiteDatabase database = tableOrderDBHelper.getReadableDatabase();
        Cursor cursor = tableOrderDBHelper.readTableOrders(database);

            while (cursor.moveToNext()){
                if (cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.TABLE_ID)).equals(title) &&
                        cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.CHAIR_ID)).equals(subtitle))
                {
                    order_id = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.ORDER_ID));
                    table_id = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.TABLE_ID));
                    chair_id = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.CHAIR_ID));
                    menu_id = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.MENU_ID));
                    menu_price = cursor.getDouble(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.MENU_PRICE));
                    quantity = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.QUANTITY));
                    amount_payable = cursor.getDouble(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT));
                    has_paid = false;

                    Double amt_payable = Math.round(amount_payable * 100.0) / 100.0;

                    String menu_name = getMenuName(menu_id);

                    String menu_price_string = Double.toString(menu_price);
                    String amount_payable_string = Double.toString(amt_payable);

                    // Inserting the cursor values into the orders array to display on the individual table groups.
                    ordersArray.add(new Orders(order_id, table_id, chair_id, menu_name, menu_price_string, quantity, amount_payable_string, has_paid));
                    total.add(amt_payable);
                }
//
                    Double total_sum = sum(total);
                    Double grand_total = totalSum(total_sum);
                    total_price.setText(Html.fromHtml("&#8358;") + Double.toString(total_sum));
                    grand_total_price.setText(Html.fromHtml("&#8358;") + Double.toString(grand_total));


                    // Work on Check out page and payment options
            }
            cursor.close();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
            Button yes, no;


            final Dialog optDialog = new Dialog(MenuActivity.this);
            optDialog.setContentView(R.layout.option_yes_or_no);
            yes = (Button) optDialog.findViewById(R.id.yes);
            no = (Button) optDialog.findViewById(R.id.no);
            optDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optDialog.cancel();
                    Integer deletedRows = null;
                    if(ordersArray.size() > 1) {

                        int order_id_this = ordersArray.get(viewHolder.getLayoutPosition()).getOrder_id();
                        mOrder_id = Integer.toString(order_id_this);
                        TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(MenuActivity.this);
                        deletedRows = tableOrderDBHelper.deleteOrders(Integer.toString(ordersArray.get(viewHolder.getLayoutPosition()).getOrder_id()));
                        ordersArray.remove(viewHolder.getAdapterPosition());

                        // Create a dialog to confirm the swipe to delete
                        if (deletedRows > 0) {
                            Toast.makeText(MenuActivity.this, "Item deleted ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuActivity.this, "No item deleted", Toast.LENGTH_SHORT).show();
                        }

                        recyclerAdapterOrder.notifyDataSetChanged();

                    }else {
                        // Dialog to ask if you are sure of emptying that chair order
                        int order_id_this = ordersArray.get(viewHolder.getLayoutPosition()).getOrder_id();
                        mOrder_id = Integer.toString(order_id_this);
                        TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(MenuActivity.this);
                        deletedRows = tableOrderDBHelper.deleteOrders(Integer.toString(ordersArray.get(viewHolder.getAdapterPosition()).getOrder_id()));
                        ordersArray.remove(viewHolder.getAdapterPosition());

                        if (deletedRows > 0) {
                            Toast.makeText(MenuActivity.this, "Last item deleted ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MenuActivity.this, "No item deleted", Toast.LENGTH_SHORT).show();
                        }
                        recyclerAdapterOrder.notifyDataSetChanged();
                    }
                    finish();
                    startActivity(getIntent());

                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optDialog.cancel();
                    finish();
                    startActivity(getIntent());
                }
            });
            optDialog.show();
        }
    };

    //calculate total
    public static Double sum(List<Double> list){
        Double sum = 0.0;
        for (Double i: list){
            sum += i;
        }
        return sum;
    }

    //Calculate VAT on total purchase
    public static Double totalSum(Double total){
        final Double vat = total * (0.05);
        final Double grand_total = vat + total;
        final Double grand_total_round = Math.round(grand_total * 100.0) / 100.0;
        return grand_total_round;
    }

    // To get the name of the food item from the id.
    public String getMenuName (int menu_no){
        MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(MenuActivity.this);
        SQLiteDatabase sqLiteDatabase = menuItemsDBHelper.getReadableDatabase();
        Cursor cursor1 = menuItemsDBHelper.readItemsByCatId(sqLiteDatabase, Integer.toString(menu_no));
        String menu_id_name = null;

        while (cursor1.moveToNext()){
                menu_id_name = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
        }
        return menu_id_name;
    }


}
