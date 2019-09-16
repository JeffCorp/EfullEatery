package com.efulltech.efulleatery;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.efulltech.efulleatery.activities.MainActivity;
import com.efulltech.efulleatery.activities.TableActivity;
import com.efulltech.efulleatery.contract.db.AuthContract;
import com.efulltech.efulleatery.dbHelper.AuthDbHelper;
import com.efulltech.efulleatery.sharedPref.SharedPreferenceConfig;

public class Tab1Signin extends Fragment {

    private SharedPreferenceConfig preferenceConfig;
    public EditText UserEmail, UserPassword;


    SQLiteDatabase database;
    SQLiteOpenHelper openHelper;
    Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1signin, container, false);
        openHelper = new AuthDbHelper(getActivity());
        database = openHelper.getReadableDatabase();

       preferenceConfig = new SharedPreferenceConfig(getActivity().getApplicationContext());
       UserEmail = rootView.findViewById(R.id.username);
       UserPassword = rootView.findViewById(R.id.password);

       if (preferenceConfig.readLoginStatus()){
           startActivity(new Intent(getActivity(), MainActivity.class));
           getActivity().finish();
       }


        Button button = (Button) rootView.findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                cursor = database.rawQuery("SELECT * FROM "+ AuthContract.AuthEntry.TABLE_NAME+ " WHERE "+ AuthContract.AuthEntry.EMAIL+ "=? AND " + AuthContract.AuthEntry.PASSWORD+ "=?", new String[]{email,password});
                if (cursor != null){
                    if (cursor.getCount() > 0)
                    {
                        cursor.moveToNext();
                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), MainActivity.class));


                        preferenceConfig.writeLoginStatus(true);
                        getActivity().finish();
                    }
                    else
                    {
                        UserEmail.setText("");
                        UserPassword.setText("");
                        Toast.makeText(getActivity(),"Unknown user", Toast.LENGTH_LONG).show();
                    }
                }

//                if (email.equals(getResources().getString(R.string.user_name)) && password.equals(getResources().getString(R.string.user_password))){
//                    startActivity(new Intent(getActivity(), TableActivity.class));
//                    preferenceConfig.writeLoginStatus(true);
//                    getActivity().finish();
//                }
//                else{
//                    Toast.makeText(getActivity(),"Invalid details... Please try again", Toast.LENGTH_SHORT).show();
//                    UserEmail.setText("");
//                    UserPassword.setText("");
//                }

//                EditText editText = rootView.findViewById(R.id.username);
//                String auth_name = editText.getText().toString();
//
//                Intent intent = new Intent(getActivity(), TableActivity.class);
//                intent.putExtra("Welcome", "Welcome " + auth_name);
//                Activity activity = getActivity();
//                activity.finish();
//                startActivity(intent);
            }
        });
        return rootView;
    }
    private void readContacts()
    {
        AuthDbHelper authDbHelper = new AuthDbHelper(getActivity());
        SQLiteDatabase database = authDbHelper.getReadableDatabase();

        Cursor cursor = authDbHelper.readContacts(database);
        String id;
        String email;
        String password;

        while (cursor.moveToNext()){
            id = Integer.toString(cursor.getInt(cursor.getColumnIndex(AuthContract.AuthEntry.USER_ID)));
            email = cursor.getString(cursor.getColumnIndex(AuthContract.AuthEntry.EMAIL));
            password = cursor.getString(cursor.getColumnIndex(AuthContract.AuthEntry.PASSWORD));
        }
    }

}
