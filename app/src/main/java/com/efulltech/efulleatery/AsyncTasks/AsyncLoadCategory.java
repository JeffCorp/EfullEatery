package com.efulltech.efulleatery.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.efulltech.efulleatery.contract.db.MenuCategoryContract;
import com.efulltech.efulleatery.dbHelper.MenuCategoryDBHelper;
import com.efulltech.efulleatery.table.models.AllMenuItems;
import com.efulltech.efulleatery.table.models.EmenuItems;

import java.util.List;

public class AsyncLoadCategory extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private List<EmenuItems> emenuItems;
    private ProgressDialog progressDialog;

    public AsyncLoadCategory(Context mContext, List<EmenuItems> emenuItems) {
        this.mContext = mContext;
        this.emenuItems = emenuItems;
    }

    @Override
    protected Void doInBackground(Void... lists) {
        MenuCategoryDBHelper menuCategoryDBHelper = new MenuCategoryDBHelper(mContext);
        SQLiteDatabase database = menuCategoryDBHelper.getReadableDatabase();
        Cursor cursor = menuCategoryDBHelper.readCategory(database);

        String cat_name;

        while (cursor.moveToNext()){
            cat_name = cursor.getString(cursor.getColumnIndex(MenuCategoryContract.MenuCategory.CATEGORY_NAME));
            emenuItems.add(new EmenuItems(cat_name));
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(mContext);

        progressDialog.setTitle("Loading database");
        progressDialog.setMessage("please wait");
        progressDialog.setIndeterminate(false);

        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });

        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
    }
}
