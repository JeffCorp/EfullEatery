package com.efulltech.efulleatery;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.efulltech.efulleatery.activities.ItemsStock;
import com.efulltech.efulleatery.activities.OperateChoice;
import com.efulltech.efulleatery.activities.RecyclerTable;
import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.contract.db.TablesContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.dbHelper.MenuItemsDBHelper;
import com.efulltech.efulleatery.dbHelper.TablesDBHelper;
import com.efulltech.efulleatery.table.models.Category;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingsFragment extends PreferenceFragment {

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        String key = preference.getKey();
        if (key.equals("table_setup")){
            final Dialog myDialog = new Dialog(getActivity());
            myDialog.setContentView(R.layout.dialog_table_setup);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button create_yes = (Button) myDialog.findViewById(R.id.create_table_yes);
            Button create_no = (Button) myDialog.findViewById(R.id.create_table_no);
            final EditText table_no = (EditText) myDialog.findViewById(R.id.input_table_no);

            create_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TablesDBHelper tablesDBHelper = new TablesDBHelper(getActivity());
                    String table_name;

                    SQLiteDatabase db = tablesDBHelper.getReadableDatabase();
                    Cursor cursor = tablesDBHelper.readTables(db);

                    final String getTableNo = table_no.getText().toString() ;
                    int getIntTable = Integer.parseInt(getTableNo);
                    SQLiteDatabase database = tablesDBHelper.getWritableDatabase();

                    int db_array_length = cursor.getCount();

                        int i;
                        for (i = db_array_length + 1; i <= getIntTable + db_array_length; i++ ){
                            String table_name_new = "Table " + i;
//                            while (cursor.moveToNext()){
//                                table_name = cursor.getString(cursor.getColumnIndex(TablesContract.TablesEntry.TABLE_ID));
//                                    if (table_name_new != table_name){
//                                       //do nothing
                                        tablesDBHelper.addTables(table_name_new, 0, 0, database);
//                                    }
//                            }

                         }
                        Toast.makeText(getActivity(),"Table create with "+ i, Toast.LENGTH_SHORT ).show();
                    tablesDBHelper.close();
//                    Toast.makeText(getActivity(),"Table create with "+ i, Toast.LENGTH_SHORT ).show();

                    myDialog.cancel();
                }
            });

            create_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    table_no.setText("");
                    myDialog.cancel();
                }
            });
            myDialog.show();
            return true;
        }

        if (key.equals("edit_menu_setup")){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.add_categories);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final EditText catName = (EditText) dialog.findViewById(R.id.cat_name);
            final EditText catDesc = (EditText) dialog.findViewById(R.id.cat_desc);
            Button submitCat = (Button) dialog.findViewById(R.id.submit_cat);

            final MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(getActivity());
            final SQLiteDatabase database = menuCategoryDBHelper.getWritableDatabase();

            submitCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cat_name = catName.getText().toString();
                    String cat_desc = catDesc.getText().toString();



                    menuCategoryDBHelper.addCategory(cat_name, cat_desc, database);
                    Toast.makeText(getActivity(), "Upload " + cat_name + " to Categories table", Toast.LENGTH_SHORT).show();
                }
            });



            dialog.show();
        }

        if (key.equals("upload_menu_items")){
            final Dialog myDialog = new Dialog(getActivity());
            myDialog.setContentView(R.layout.upload_menu_item);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(getActivity());
            ArrayList<String> categories = menuCategoryDBHelper.getAllCategories();

            final EditText item_name = (EditText) myDialog.findViewById(R.id.item_name);
            final EditText item_price = (EditText) myDialog.findViewById(R.id.item_price);
            final EditText item_quantity = (EditText) myDialog.findViewById(R.id.item_quantity);

            Button upload = (Button) myDialog.findViewById(R.id.submit_upload_items);

            final Spinner spinner = myDialog.findViewById(R.id.spinner_bar);

            ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, R.id.spinner_txt, categories);

            spinner.setAdapter(categoryArrayAdapter);

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item_name_string = item_name.getText().toString();
                    int item_quantity_int = Integer.parseInt(item_quantity.getText().toString());
                    Double price = Double.parseDouble(item_price.getText().toString());
                    String cat_name = spinner.getSelectedItem().toString();

                    SQLiteDatabase db = menuCategoryDBHelper.getReadableDatabase();
                    Cursor c = menuCategoryDBHelper.readSelectedCategory(cat_name,db);

                    int cat_id = 0;
                    while (c.moveToNext()) {
                        cat_id = c.getInt(c.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_ID));
                        Toast.makeText(getActivity(), "Uploaded to db table", Toast.LENGTH_SHORT).show();
                    }


                    MenuItemsDBHelper menuItemsDBHelper = new MenuItemsDBHelper(getActivity());
                    SQLiteDatabase database = menuItemsDBHelper.getWritableDatabase();
                    menuItemsDBHelper.addItem(item_name_string, item_quantity_int, item_quantity_int, price, cat_id, R.drawable.food3, database);
                    Toast.makeText(getActivity(), "Uploaded successfully with " + cat_id, Toast.LENGTH_SHORT).show();
                }
            });








            myDialog.show();


        }

        if (key.equals("display_stock")){
            startActivity(new Intent(getActivity(), ItemsStock.class));
        }

        return false;
    }

}
