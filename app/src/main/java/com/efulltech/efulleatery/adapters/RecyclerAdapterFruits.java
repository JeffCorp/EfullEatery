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

import com.efulltech.efulleatery.table.models.Fruits;
import com.efulltech.efulleatery.R;

import java.util.List;

public class RecyclerAdapterFruits extends RecyclerView.Adapter<RecyclerAdapterFruits.FVHolder> {

    Context context;
    List<Fruits> fData;
    Dialog dialog;

    public RecyclerAdapterFruits(Context context, List<Fruits> fData) {
        this.context = context;
        this.fData = fData;
    }

    @NonNull
    @Override
    public FVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_listitem, viewGroup, false);
        final FVHolder fvHolder = new FVHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        fvHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name = (TextView) dialog.findViewById(R.id.dialog_name_id);
                ImageView dialog_image = (ImageView) dialog.findViewById(R.id.dialog_image);

                dialog_name.setText(fData.get(fvHolder.getAdapterPosition()).getFruit_name());
                dialog_image.setImageResource(fData.get(fvHolder.getAdapterPosition()).getFruit_id());
                Toast.makeText(context, "This is "+String.valueOf(fvHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                dialog.show();

            }
        });


        return fvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FVHolder fvHolder, int position) {

        fvHolder.imageView.setImageResource(fData.get(position).getFruit_id());
        fvHolder.textView.setText(fData.get(position).getFruit_name());
    }

    @Override
    public int getItemCount() {
        return fData.size();
    }

    public static class FVHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private LinearLayout linearLayout;

        public FVHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_food);
            textView = (TextView) itemView.findViewById(R.id.image_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
