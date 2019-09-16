package com.efulltech.efulleatery.adapters;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.TableOrderDBHelper;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.dbHelper.TcDbHelper;
import com.efulltech.efulleatery.table.models.Food;
import com.efulltech.efulleatery.R;

import java.util.List;

public class RecyclerAdapterFood extends RecyclerView.Adapter<RecyclerAdapterFood.ViewHolder> {

    Context mContext;
    List<Food> foodArray;
    Dialog myDialog;

    private TextView table_name;

    String table_id, chair_id;


    public RecyclerAdapterFood(Context context, List<Food> foodArray) {
        this.mContext = context;
        this.foodArray = foodArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_listitem, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);

        //Dialog init

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_menu);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog_title = myDialog.findViewById(R.id.dialog_title);




        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView dialog_name = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                final TextView dialog_price = (TextView) myDialog.findViewById(R.id.dialog_price);
                ImageView dialog_image = (ImageView) myDialog.findViewById(R.id.dialog_image);
                final EditText dialog_quantity = (EditText) myDialog.findViewById(R.id.dialog_quantity);
                Button dialog_submit = (Button) myDialog.findViewById(R.id.dialog_btn_submit);
                final TextView food_id = (TextView) myDialog.findViewById(R.id.dialog_food_id);
                TextView table_top = (TextView) myDialog.findViewById(R.id.dialog_title);

//              dialog_name.setText(mImageNames.get(holder.getAdapterPosition()).getFood_name());
                dialog_name.setText(foodArray.get(holder.getAdapterPosition()).getFood_name());
                dialog_price.setText(foodArray.get(holder.getAdapterPosition()).getFood_price());
                dialog_image.setImageResource(foodArray.get(holder.getAdapterPosition()).getFood_image_id());
                food_id.setText(Integer.toString(foodArray.get(holder.getAdapterPosition()).getFood_id()));
                table_id = mContext.getSharedPreferences("my_pref", Context.MODE_PRIVATE).getString("table_name", "Empty");
                chair_id = mContext.getSharedPreferences("Chair_pref", Context.MODE_PRIVATE).getString("Chair", "Empty Chair");
                table_top.setText(table_id);


//                dialog_title.setText(table_title.getText().toString());
                myDialog.show();

                dialog_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer menu_id = Integer.parseInt(food_id.getText().toString());
                        String name = dialog_name.getText().toString();
                        Double price = Double.parseDouble(dialog_price.getText().toString());
                        Integer quantity = Integer.parseInt(dialog_quantity.getText().toString());
                        Double payable_amount = price * quantity;
                        Boolean has_paid = false;



//                        Toast.makeText(mContext, "Submitting successfully", Toast.LENGTH_SHORT).show();

                        if (!quantity.toString().isEmpty()){
//                            Toast.makeText(mContext, "Submitting successfully 2", Toast.LENGTH_SHORT).show();
                            TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(view.getContext());
                            SQLiteDatabase database = tableOrderDBHelper.getWritableDatabase();
                            tableOrderDBHelper.addOrders(table_id, chair_id, menu_id,price,quantity,payable_amount,has_paid,database);
                            tableOrderDBHelper.close();
                            TcDbHelper tcDbHelper = new TcDbHelper(view.getContext());
                            SQLiteDatabase db = tcDbHelper.getWritableDatabase();
                            tcDbHelper.addTables(table_id,chair_id,1, db);
                            Toast.makeText(view.getContext(), "Add to TC table", Toast.LENGTH_SHORT).show();
                            tcDbHelper.close();
                            TablesDBHelper tablesDBHelper = new TablesDBHelper(view.getContext());
                            SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
                            Cursor cursor = tablesDBHelper.readTables(sqLiteDatabase);
                            int id;
                            String table_name;
                            int chair_no;
                            while (cursor.moveToNext()){
                                int has_orders;
                                id = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.ID));
                                table_name = cursor.getString(cursor.getColumnIndex(TablesContract.TablesEntry.TABLE_ID));
                                chair_no = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.CHAIRS_NO));
                                if (table_name.equals(table_id)){
                                    boolean isUpdated = tablesDBHelper.updateTables(id, table_name,chair_no, 1);

                                    if (isUpdated){
                                        Toast.makeText(view.getContext(), "Table updated", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(view.getContext(), "Table not updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            Toast.makeText(mContext, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.imageName.setText(foodArray.get(position).getFood_name());
        viewHolder.image.setImageResource(foodArray.get(position).getFood_image_id());
        viewHolder.price.setText(foodArray.get(position).getFood_price());
        viewHolder.menu_id.setText(Integer.toString(foodArray.get(position).getFood_id()));
        viewHolder.table_name.setText(mContext.getSharedPreferences("my_pref", Context.MODE_PRIVATE).getString("table_name", "No table"));




//        viewHolder.table_top.setText(fr);

//        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "You have been toasted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return foodArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView imageName;
        private TextView price;
        private TextView menu_id;
        public TextView table_name;
        private LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menu_id = (TextView) itemView.findViewById(R.id.food_id);
            image = (ImageView) itemView.findViewById(R.id.image_food);
            imageName = (TextView) itemView.findViewById(R.id.image_name);
            price = (TextView) itemView.findViewById(R.id.image_price);
            table_name = (TextView) itemView.findViewById(R.id.table_name);



            parentLayout = (LinearLayout) itemView.findViewById(R.id.parent_layout);
        }
    }


}
