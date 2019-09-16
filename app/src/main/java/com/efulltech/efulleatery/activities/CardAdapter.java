package com.efulltech.efulleatery.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.efulltech.efulleatery.contract.db.TableOrdersContract;
import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.TableOrderDBHelper;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.table.models.CardItem;
import com.efulltech.efulleatery.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private ArrayList<CardItem> mCardList;
    private OnItemClickListener mListener;
    private Context nContext;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onMenuDotsClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView, imageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView imageMenu;
        public CardView cardItem;
        public Context mContext;

        public CardViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.menu_icon);
            imageView = itemView.findViewById(R.id.menu_icon);
            mTextView1 = itemView.findViewById(R.id.menu_text);
            mTextView2 = itemView.findViewById(R.id.occupied);
            cardItem = itemView.findViewById(R.id.table_card);
            mContext = itemView.getContext();




//        cardViewHolder.cardItem.setBackgroundColor(nContext.getResources().getColor(R.color.colorAccent));


//            cardItem.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));

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

    public CardAdapter(ArrayList<CardItem> cardList, Context context){
        mCardList = cardList;
        nContext = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        CardViewHolder cvh = new CardViewHolder(v, mListener);
        return cvh;

    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int position) {
        CardItem currentItem = mCardList.get(position);
        cardViewHolder.mTextView1.setText(currentItem.getText1());
        cardViewHolder.mTextView2.setText(currentItem.getText2());

        if (cardViewHolder.mTextView2.getText()  == "Occupied"){
            cardViewHolder.mImageView.setImageResource(currentItem.getImageResource());
            cardViewHolder.cardItem.setBackgroundColor(cardViewHolder.mContext.getResources().getColor(R.color.colorAccent));
        }else{
            cardViewHolder.cardItem.setBackgroundColor(cardViewHolder.mContext.getResources().getColor(R.color.colorWhite));
            cardViewHolder.mTextView1.setTextColor(cardViewHolder.mContext.getResources().getColor(R.color.colorAccent));
            cardViewHolder.mTextView2.setTextColor(cardViewHolder.mContext.getResources().getColor(R.color.colorAccent));
            cardViewHolder.mImageView.setBackground(cardViewHolder.mContext.getResources().getDrawable(R.drawable.ic_restaurant_accent));

        }

//        TablesDBHelper tablesDBHelper = new TablesDBHelper(cardViewHolder.mContext);
//        SQLiteDatabase database = tablesDBHelper.getReadableDatabase();
//        Cursor cursor = tablesDBHelper.readTables(database);
//        Integer has_orders;
//        while (cursor.moveToNext()){
//            has_orders = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.HAS_ORDERS));
//
//            String ho = Integer.toString(has_orders);
//
//            SQLiteDatabase db = tablesDBHelper.getReadableDatabase();
//            Cursor c = tablesDBHelper.readTablesColor(db, "1");
//
//            while (c.moveToNext()){
//                if (has_orders < 1) {
////                    cardViewHolder.cardItem.setBackgroundColor(cardViewHolder.mContext.itemView.getResources().getColor(R.color.colorAccent));
////                    cardViewHolder.mTextView2.setVisibility(View.GONE);
//                    cardViewHolder.cardItem.setBackgroundColor(cardViewHolder.mContext.getResources().getColor(R.color.colorAccent));
//                }
//                cardViewHolder.cardItem.setBackgroundColor(cardViewHolder.mContext.getResources().getColor(R.color.colorTransparent));
//                break;
//
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}
