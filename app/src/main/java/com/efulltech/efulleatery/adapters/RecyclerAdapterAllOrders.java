package com.efulltech.efulleatery.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.dbHelper.TableOrderDBHelper;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.dbHelper.TcDbHelper;
import com.efulltech.efulleatery.table.models.AllMenuItems;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterAllOrders extends RecyclerView.Adapter<RecyclerAdapterAllOrders.AllOrdersViewHolder> {

    private Context mContext;
    private List<AllMenuItems> allMenuItems = new ArrayList<>();
    private Dialog dialog, mDialog;
    private SharedPreferences sharedPreferences, preferences;

    public RecyclerAdapterAllOrders(Context mContext, List<AllMenuItems> allMenuItems) {
        this.mContext = mContext;
        this.allMenuItems = allMenuItems;
    }

    @NonNull
    @Override
    public AllOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.menu_items_container, viewGroup, false);
        final AllOrdersViewHolder viewHolder = new AllOrdersViewHolder(view);

        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.view_image);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.items_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView imageView = (ImageView) mDialog.findViewById(R.id.image_expanded);

                imageView.setImageResource(allMenuItems.get(viewHolder.getAdapterPosition()).getMenu_image_id());

                mDialog.show();

            }
        });

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.allorders_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.menu_items_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView allorders_item = (TextView) dialog.findViewById(R.id.allorders_food_name);
                final TextView allorders_price = (TextView) dialog.findViewById(R.id.allorders_price);
                final TextView allorders_cat = (TextView) dialog.findViewById(R.id.allorders_cat);
                final EditText allorders_quantity = (EditText) dialog.findViewById(R.id.allorders_quantity);
                final EditText allorders_notes = (EditText) dialog.findViewById(R.id.allorders_note);
                Button go = (Button) dialog.findViewById(R.id.allorders_go);
                Button cancel = (Button) dialog.findViewById(R.id.allorders_cancel);

                //Set image id to display in dialog


                allorders_item.setText(allMenuItems.get(viewHolder.getAdapterPosition()).getFood_name_menu());
                allorders_price.setText(Double.toString(allMenuItems.get(viewHolder.getAdapterPosition()).getFood_price_menu()));
                allorders_cat.setText(allMenuItems.get(viewHolder.getAdapterPosition()).getFood_category());

                sharedPreferences = mContext.getSharedPreferences("table_info", Context.MODE_PRIVATE);
                preferences = mContext.getSharedPreferences("Chair_pref", Context.MODE_PRIVATE);


                //Display dialog box
                dialog.show();

                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        String table_name = sharedPreferences.getString("table_name", "No table");
                        String chair_name = preferences.getString("Chair", "No Chair");
                        Integer item_id = allMenuItems.get(viewHolder.getAdapterPosition()).getFood_name_id();
                        String menu_id =  allorders_item.getText().toString();
                        Double price = Double.parseDouble(allorders_price.getText().toString());
                        Integer quantity = Integer.parseInt(allorders_quantity.getText().toString());
                        String cat = allorders_cat.getText().toString();
                        Double payable_amount = price * quantity;
                        Boolean has_paid = false;


                        TableOrderDBHelper tableOrderDBHelper = new TableOrderDBHelper(mContext);
                        SQLiteDatabase database = tableOrderDBHelper.getWritableDatabase();
                        tableOrderDBHelper.addOrders(table_name, chair_name, item_id, price, quantity, payable_amount,has_paid,database);
                        TcDbHelper tcDbHelper = new TcDbHelper(view.getContext());
                        SQLiteDatabase db = tcDbHelper.getWritableDatabase();
                        tcDbHelper.addTables(table_name,chair_name,1, db);
                        Toast.makeText(view.getContext(), "Add to TC table", Toast.LENGTH_SHORT).show();
                        tcDbHelper.close();
                        TablesDBHelper tablesDBHelper = new TablesDBHelper(view.getContext());
                        SQLiteDatabase sqLiteDatabase = tablesDBHelper.getReadableDatabase();
                        Cursor cursor = tablesDBHelper.readTables(sqLiteDatabase);
                        int id;
                        String new_table_name;
                        int chair_no;
                        while (cursor.moveToNext()){
                            int has_orders;
                            id = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.ID));
                            new_table_name = cursor.getString(cursor.getColumnIndex(TablesContract.TablesEntry.TABLE_ID));
                            chair_no = cursor.getInt(cursor.getColumnIndex(TablesContract.TablesEntry.CHAIRS_NO));
                            if (new_table_name.equals(table_name)){
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
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        allorders_quantity.setText("");
                        allorders_notes.setText("");
                        dialog.cancel();
                    }
                });
            }
        });







        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersViewHolder allOrdersViewHolder, int position) {
       allOrdersViewHolder.food_name.setText(allMenuItems.get(position).getFood_name_menu());
       allOrdersViewHolder.food_price.setText(Double.toString(allMenuItems.get(position).getFood_price_menu()));
//       allOrdersViewHolder.food_notes.setText(allMenuItems.get(position).getFood_info_menu());
       allOrdersViewHolder.food_category.setText(allMenuItems.get(position).getFood_category());
       allOrdersViewHolder.items_image.setImageResource(allMenuItems.get(position).getMenu_image_id());


    }

    @Override
    public int getItemCount() {
        return allMenuItems.size();
    }

    public static class AllOrdersViewHolder extends RecyclerView.ViewHolder{
        private TextView food_name, food_price, food_notes, food_category;
        private ImageView items_image;
        private LinearLayout menu_items_container;

        public AllOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            food_name = itemView.findViewById(R.id.food_name_items);
            food_price = itemView.findViewById(R.id.food_price_item);
//            food_notes = itemView.findViewById(R.id.food_notes);
            food_category = itemView.findViewById(R.id.foodCategory);
            items_image = itemView.findViewById(R.id.items_image);
            menu_items_container = itemView.findViewById(R.id.menu_items_container);
        }
    }
}
