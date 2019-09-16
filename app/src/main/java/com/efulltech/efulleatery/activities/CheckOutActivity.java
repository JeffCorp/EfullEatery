package com.efulltech.efulleatery.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.PaymentOptionActivity;
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

public class CheckOutActivity extends AppCompatActivity {

    ImageButton btnEditOrder;
    ImageButton btnCheckOut;
    ImageButton btnRefresh;
    Toolbar toptoolbar;
    Button proceed;

    private Double grand_total;

    public String title, subtitle;
    private List<Orders> ordersArray;

    List<Double> total;
    TextView total_price, grand_total_price, payment_info;

    RecyclerView order_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        toptoolbar = (Toolbar) findViewById(R.id.toolbarCheckOut);

        ordersArray = new ArrayList<>();

        order_recycler = (RecyclerView) findViewById(R.id.order_recycler);
        proceed = (Button) findViewById(R.id.procced_to_pay);

        RecyclerAdapterOrder recyclerAdapterOrder = new RecyclerAdapterOrder(this, ordersArray);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        order_recycler.setLayoutManager(layoutManager);
        order_recycler.setAdapter(recyclerAdapterOrder);


        if (getIntent().hasExtra("table")){
            String message = getIntent().getStringExtra("table");
            subtitle = getIntent().getStringExtra("subtitle");
            TextView textView = findViewById(R.id.table_title);
//            getSupportActionBar().setTitle(message);
            setSupportActionBar(toptoolbar);
            getSupportActionBar().setTitle(message);
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Integer order_id;
        String table_id, chair_id;
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
//        title = getIntent().getStringExtra("table");

        title = getSupportActionBar().getTitle().toString();
        while (cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.TABLE_ID)).equals(title) &&
                    cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.CHAIR_ID)).equals(subtitle)){
                order_id = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.ORDER_ID));
                table_id = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.TABLE_ID));
                chair_id = cursor.getString(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.CHAIR_ID));
                menu_id = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.MENU_ID));
                menu_price = cursor.getDouble(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.MENU_PRICE));
                quantity = cursor.getInt(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.QUANTITY));
                amount_payable = cursor.getDouble(cursor.getColumnIndex(TableOrdersContract.TableOrdersEntry.PAYABLE_AMOUNT));
                has_paid = false;

                String menu_name = getMenuName(menu_id);

                String menu_price_string = Double.toString(menu_price);
                String amount_payable_string = Double.toString(amount_payable);

                // Inserting the cursor values into the orders array to display on the individual table groups.
                ordersArray.add(new Orders(order_id, table_id, chair_id, menu_name, menu_price_string, quantity, amount_payable_string, has_paid));
                total.add(amount_payable);
            }
//                Double total_sum = 0.0;
//
//                for (int i = 0; i <= total.size(); i++){
//                    total_sum = total.get(i) + total.get(i+1);
//                }
            Double total_sum = sum(total);
            grand_total = totalSum(total_sum);
            if (grand_total != 0){
                total_price.setText(Html.fromHtml("&#8358;") + Double.toString(total_sum));

                grand_total_price.setText(Html.fromHtml("&#8358;") + Double.toString(grand_total));
            }else{
                total_price.setText(Html.fromHtml("&#8358;") + " 0");

                grand_total_price.setText(Html.fromHtml("&#8358;") + " 0");
            }

        }
        cursor.close();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CheckOutActivity.this, PaymentOptionActivity.class));
                final Dialog optDialog = new Dialog(CheckOutActivity.this);
                optDialog.setContentView(R.layout.payment_option);
                Button card = (Button) optDialog.findViewById(R.id.card);
                Button cash = (Button) optDialog.findViewById(R.id.cash);
                payment_info = (TextView) optDialog.findViewById(R.id.payment_message);
                if (grand_total != 0){
                    payment_info.setText(Html.fromHtml("&#8358;") + Double.toString(grand_total));
                }else {
                    payment_info.setText(Html.fromHtml("&#8358;") + "0");
                }

                optDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

               final int amt = (int) Math.round(grand_total * 100);


                card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Here connect to POS payment gateway
                        Intent intent =  new Intent("com.arke.sdk.TransactParser");
                        intent.putExtra("trantype", 1);
                        intent.putExtra("batchno", 1);
                        intent.putExtra("seqno", 1);
//                        intent.putExtra("amount", amt);
                        intent.putExtra("amount", 100);
                        intent.putExtra("action", "makePayment");
                        intent.putExtra("appName", "EWA");
                        intent.putExtra("domainName", "com.efulltech.efulleatery");
                        startActivityForResult(intent, 7142);

                    }
                });

                cash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optDialog.cancel();
                        finish();
                        startActivity(getIntent());
                    }
                });
                optDialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7142){
            if (resultCode == Activity.RESULT_OK){
                String respCode = (String) data.getSerializableExtra("responseCode");
                Log.d("Response code", respCode);
            }
        }
    }

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
        return grand_total;
    }

    // To get the name of the food item from the id.
    public String getMenuName (int menu_no){
        MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(CheckOutActivity.this);
        SQLiteDatabase sqLiteDatabase = menuItemsDBHelper.getReadableDatabase();
        Cursor cursor1 = menuItemsDBHelper.readItemsByCatId(sqLiteDatabase, Integer.toString(menu_no));
        String menu_id_name = null;

        while (cursor1.moveToNext()){
            menu_id_name = cursor1.getString(cursor1.getColumnIndex(ItemsContract.ItemsEntry.ITEM_NAME));
        }
        return menu_id_name;
    }
}
