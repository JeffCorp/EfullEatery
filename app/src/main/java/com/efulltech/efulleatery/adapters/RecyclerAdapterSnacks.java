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

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.Snacks;

import java.util.List;

public class RecyclerAdapterSnacks extends RecyclerView.Adapter<RecyclerAdapterSnacks.SViewHolder> {

    Context context;
    List<Snacks> sData;
    Dialog dialog;


    public RecyclerAdapterSnacks(Context context, List<Snacks> sData) {
        this.context = context;
        this.sData = sData;
    }

    @NonNull
    @Override
    public SViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_listitem, viewGroup, false);
        final SViewHolder sViewHolder = new SViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        sViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name = (TextView) dialog.findViewById(R.id.dialog_name_id);
                ImageView dialog_image = (ImageView) dialog.findViewById(R.id.dialog_image);

                dialog_name.setText(sData.get(sViewHolder.getAdapterPosition()).getSnack_name());
                dialog_image.setImageResource(sData.get(sViewHolder.getAdapterPosition()).getSnack_id());
                Toast.makeText(context, "This is "+String.valueOf(sViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                dialog.show();

            }
        });


        return sViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SViewHolder sViewHolder, int position) {
        sViewHolder.imageView.setImageResource(sData.get(position).getSnack_id());
        sViewHolder.textView.setText(sData.get(position).getSnack_name());

    }

    @Override
    public int getItemCount() {
        return sData.size();
    }

    public static class SViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        private LinearLayout linearLayout;

        public SViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_food);
            textView = (TextView) itemView.findViewById(R.id.image_name);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent_layout);

        }
    }
}
