package com.efulltech.efulleatery.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.table.models.SettingsItems;

import java.util.List;

public class RecyclerAdapterSettings extends RecyclerView.Adapter<RecyclerAdapterSettings.MyViewHolder> {

    List<SettingsItems> settingsItems;
    Context mContext;

    public RecyclerAdapterSettings(List<SettingsItems> settingsItems, Context mContext) {
        this.settingsItems = settingsItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.settings_item,viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.imageView.setImageResource(settingsItems.get(position).getImage_id());
        myViewHolder.settingsText.setText(settingsItems.get(position).getSettings_text());
    }

    @Override
    public int getItemCount() {
        return settingsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView settingsText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.settings_image);
            settingsText = (TextView) itemView.findViewById(R.id.settings_text);
        }
    }
}
