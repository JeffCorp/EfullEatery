package com.efulltech.efulleatery.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.efulltech.efulleatery.R;

public class SharedPrefConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPrefConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.operation_preference), Context.MODE_PRIVATE);
    }

    public void writeOpsStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.operation_status_preference), status);
        editor.commit();
    }

    public boolean readOpsStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.operation_status_preference),false);
        return status;
    }
}
