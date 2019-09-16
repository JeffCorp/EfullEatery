package com.efulltech.efulleatery.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.efulltech.efulleatery.AsyncTasks.AsyncLoadStock;
import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.adapters.RecyclerAdapterStock;
import com.efulltech.efulleatery.table.models.StockItems;

import java.util.ArrayList;
import java.util.List;

public class ItemsStock extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapterStock adapterStock;
    private List<StockItems> stockItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_stock);

        stockItems = new ArrayList<>();

        recyclerView = findViewById(R.id.stockRecycler);

        adapterStock = new RecyclerAdapterStock(this, stockItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        new AsyncLoadStock(adapterStock,recyclerView,this,stockItems).execute();
//        recyclerView.setAdapter(adapterStock);





//        stockItems.add(new StockItems("Rice", "30", "3", "3", "food"));




    }
}
