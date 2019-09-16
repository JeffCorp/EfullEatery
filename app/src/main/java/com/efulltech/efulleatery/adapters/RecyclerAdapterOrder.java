package com.efulltech.efulleatery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.activities.MenuActivity;
import com.efulltech.efulleatery.table.models.Orders;

import java.util.List;

public class RecyclerAdapterOrder extends RecyclerView.Adapter<RecyclerAdapterOrder.OrdersView> {


    Context oContext;
    List<Orders> ordersList;

    public RecyclerAdapterOrder(Context oContext, List<Orders> ordersList) {
        this.oContext = oContext;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrdersView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(oContext).inflate(R.layout.orders_list, viewGroup, false);
        final OrdersView ordersView = new OrdersView(view);



        return ordersView;

    }

    @Override
    public void onBindViewHolder(@NonNull OrdersView ordersView, int position) {
        ordersView.item_name.setText(ordersList.get(position).getMenu_id());
        ordersView.price_per_unit.setText(ordersList.get(position).getMenu_price());
        ordersView.price.setText(ordersList.get(position).getAmount_payable());

    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrdersView extends RecyclerView.ViewHolder{

        private TextView item_name;
        private TextView price_per_unit;
        private TextView price;

        private LinearLayout order_items;

        public OrdersView(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            price_per_unit = itemView.findViewById(R.id.price_per_unit);
            price = itemView.findViewById(R.id.order_price);
            order_items = itemView.findViewById(R.id.order_items);
        }
    }
}
