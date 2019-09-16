package com.efulltech.efulleatery;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.efulltech.efulleatery.dbHelper.AuthDbHelper;

public class Tab2Signup extends Fragment {

    private Button bnSave;
    EditText Name, Email, PhoneNumber, Password, Confirm_Password;

    public Tab2Signup(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2signup, container, false);

        bnSave = rootView.findViewById(R.id.signUp);
        Name = rootView.findViewById(R.id.username);
        Email = rootView.findViewById(R.id.email);
        PhoneNumber = rootView.findViewById(R.id.phone);
        Password = rootView.findViewById(R.id.password);
        Confirm_Password = rootView.findViewById(R.id.conf_pass);

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = 1;
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String phone = PhoneNumber.getText().toString();
                String password = Password.getText().toString();
                String conf_pass = Confirm_Password.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty() && !conf_pass.isEmpty()){
                    if (password.equals(conf_pass)){
                        AuthDbHelper authDbHelper =  new AuthDbHelper(getActivity());//Make activity an object of AuthDBHelper in this activity
                        SQLiteDatabase database = authDbHelper.getWritableDatabase();// Get writable database method in a new object of SQLiteDatabase
                        authDbHelper.addUser(id,name,email,phone,password,database);
                        authDbHelper.close();
                        Name.setText("");
                        Email.setText("");
                        PhoneNumber.setText("");
                        Password.setText("");
                        Confirm_Password.setText("");

                        Toast.makeText(getActivity(), "Sign up successful", Toast.LENGTH_SHORT).show();
                    }else {
                        Password.setText("");
                        Confirm_Password.setText("");
                        Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Fill all necessary information", Toast.LENGTH_LONG).show();
                }
            }



        });

        return rootView;
    }
}
