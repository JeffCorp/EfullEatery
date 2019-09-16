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
import com.efulltech.efulleatery.table.models.CardItem;

import java.util.ArrayList;

public class ChairCardAdapter extends RecyclerView.Adapter<ChairCardAdapter.CardViewHolder> {

    private ArrayList<CardItem> mCardList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onMenuDotsClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView imageMenu;
        public Context mContext;

        public CardViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.menu_icon);
            mTextView1 = itemView.findViewById(R.id.menu_text);
            mTextView2 = itemView.findViewById(R.id.occupied);
            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public ChairCardAdapter(ArrayList<CardItem> cardList){
        mCardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chairs_card, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(v, mListener);



        return cvh;

    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int position) {
        CardItem currentItem = mCardList.get(position);
        cardViewHolder.mImageView.setImageResource(currentItem.getImageResource());
        cardViewHolder.mTextView1.setText(currentItem.getText1());
        cardViewHolder.mTextView2.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}
