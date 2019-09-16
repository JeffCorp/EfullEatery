package com.efulltech.efulleatery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.OrdersAll;

import java.util.List;

public class RecyclerAdapterOrders extends RecyclerView.Adapter<RecyclerAdapterOrders.OrderListView> {

    Context mContext;
    List<OrdersAll> ordersAll;

    public RecyclerAdapterOrders(Context mContext, List<OrdersAll> ordersAll) {
        this.mContext = mContext;
        this.ordersAll = ordersAll;
    }

    @NonNull
    @Override
    public OrderListView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.orders_list_items, viewGroup, false);
        final OrderListView orderListView = new OrderListView(view);


        return orderListView;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListView orderListView, int position) {
        orderListView.order_table_name.setText(ordersAll.get(position).getOrder_list_table_name());
        orderListView.order_table_items.setText(ordersAll.get(position).getOrder_list_items());
        orderListView.order_table_price.setText(ordersAll.get(position).getOrder_list_price());
        orderListView.order_chair_name.setText(ordersAll.get(position).getOrder_list_chair_name());

    }

    @Override
    public int getItemCount() {
        return ordersAll.size();
    }

    public static class OrderListView extends RecyclerView.ViewHolder{

        private TextView order_table_name, order_table_items, order_table_price, order_chair_name;

        public OrderListView(@NonNull View itemView) {
            super(itemView);

            order_table_name = (TextView) itemView.findViewById(R.id.orders_table_name);
            order_table_items = (TextView) itemView.findViewById(R.id.orders_table_items);
            order_table_price = (TextView) itemView.findViewById(R.id.orders_table_prices);
            order_chair_name = (TextView) itemView.findViewById(R.id.orders_chair_name);
        }
    }

}
