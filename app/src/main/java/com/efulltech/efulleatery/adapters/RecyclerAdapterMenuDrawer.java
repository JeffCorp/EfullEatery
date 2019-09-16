package com.efulltech.efulleatery.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.EmenuItems;

import java.util.List;

public class RecyclerAdapterMenuDrawer extends RecyclerView.Adapter<RecyclerAdapterMenuDrawer.DrawerViewHolder> {

    Context mContext;
    List<EmenuItems> emenuItems;
    SharedPreferences sharedPreferences;
    private OnNoteListener mOnNoteListener;

    public RecyclerAdapterMenuDrawer(Context mContext, List<EmenuItems> emenuItems, OnNoteListener onNoteListener) {
        this.mContext = mContext;
        this.emenuItems = emenuItems;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_layout, viewGroup, false);
        final DrawerViewHolder viewHolder = new DrawerViewHolder(view, mOnNoteListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DrawerViewHolder drawerViewHolder, final int position) {

        drawerViewHolder.menuItem.setText(emenuItems.get(position).getItemName());

    }

    @Override
    public int getItemCount() {
        return emenuItems.size();
    }

    public static class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView menuItem;
        private LinearLayout rowLayout;
        OnNoteListener onNoteListener;


        public DrawerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            menuItem = itemView.findViewById(R.id.tx_item);
            rowLayout = itemView.findViewById(R.id.row_layout);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}
