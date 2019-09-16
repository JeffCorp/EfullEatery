package com.efulltech.efulleatery.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.Chairs;

import java.util.ArrayList;

public class RecyclerAdapterChair extends RecyclerView.Adapter<RecyclerAdapterChair.ChairViewHolder> {

    private ArrayList<Chairs> chairs = new ArrayList<>();

    @NonNull
    @Override
    public ChairViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);

        return new ChairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChairViewHolder chairViewHolder, int position) {

        chairViewHolder.line1.setText(chairs.get(position).getChair_name());

    }

    @Override
    public int getItemCount() {
        return chairs.size();
    }

    public static class ChairViewHolder extends RecyclerView.ViewHolder{

        TextView line1, line2;
        CardView mCardView;

        public ChairViewHolder(@NonNull View itemView) {
            super(itemView);
            line1 = itemView.findViewById(R.id.menu_text);
            line2 = itemView.findViewById(R.id.occupied);
            mCardView = itemView.findViewById(R.id.table_card);

        }
    }
}
