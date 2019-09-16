package com.efulltech.efulleatery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.StockItems;

import java.util.List;

public class RecyclerAdapterStock extends RecyclerView.Adapter<RecyclerAdapterStock.StockViewHolder> {

    private Context mContext;
    List<StockItems> stockItems;

    public RecyclerAdapterStock(Context mContext, List<StockItems> stockItems) {
        this.mContext = mContext;
        this.stockItems = stockItems;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_container, viewGroup, false);
        StockViewHolder viewHolder = new StockViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int position) {
        stockViewHolder.itemName.setText(stockItems.get(position).getStock_item_name());
        stockViewHolder.price.setText(stockItems.get(position).getStock_price());
        stockViewHolder.s_quantity.setText(stockItems.get(position).getStock_start_quantity());
        stockViewHolder.c_quantity.setText(stockItems.get(position).getStock_current_quantity());
        stockViewHolder.category.setText(stockItems.get(position).getStock_category());

    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, price, s_quantity, c_quantity, category;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.stock_item_name);
            price = (TextView) itemView.findViewById(R.id.stock_price);
            s_quantity = (TextView) itemView.findViewById(R.id.stock_start_quantity);
            c_quantity = (TextView) itemView.findViewById(R.id.stock_current_quantity);
            category = (TextView) itemView.findViewById(R.id.stock_item_category);
        }
    }
}
