package com.efulltech.efulleatery.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.table.models.Drinks;
import com.efulltech.efulleatery.R;

import java.util.List;

public class RecyclerAdapterDrinks extends RecyclerView.Adapter<RecyclerAdapterDrinks.DViewHolder> {

    Context context;
    List<Drinks> dData;
    Dialog dialog;

    public RecyclerAdapterDrinks(Context context, List<Drinks> dData) {
        this.context = context;
        this.dData = dData;
    }

    @NonNull
    @Override
    public DViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_listitem, viewGroup, false);
        final DViewHolder dViewHolder = new DViewHolder(view);

        //Dialog Init

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        dViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView dialog_name = (TextView) myDialog.findViewById(R.id.dialog_name_id);
//                ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialog_image);

        dViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name = (TextView) dialog.findViewById(R.id.dialog_name_id);
                ImageView dialog_image = (ImageView) dialog.findViewById(R.id.dialog_image);

                dialog_name.setText(dData.get(dViewHolder.getAdapterPosition()).getDrink_name());
                dialog_image.setImageResource(dData.get(dViewHolder.getAdapterPosition()).getDrink_id());
                Toast.makeText(context, "This is "+String.valueOf(dViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                dialog.show();

            }
        });

        return dViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DViewHolder myViewHolder, int position) {
        myViewHolder.imageView.setImageResource(dData.get(position).getDrink_id());
        myViewHolder.textView.setText(dData.get(position).getDrink_name());

    }

    @Override
    public int getItemCount() {
        return dData.size();
    }


    public static class DViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private LinearLayout linearLayout;

        public DViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_food);
            textView = (TextView) itemView.findViewById(R.id.image_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
