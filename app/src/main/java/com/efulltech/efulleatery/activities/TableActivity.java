package com.efulltech.efulleatery.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.sharedPref.SharedPrefConfig;
import com.efulltech.efulleatery.sharedPref.SharedPreferenceConfig;

public class TableActivity extends AppCompatActivity {

    private CardView btnSignOut;
    private CardView btnSettings;
    private CardView btnViewOrders;

    private SharedPreferenceConfig preferenceConfig;
    private SharedPrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        btnSignOut = findViewById(R.id.signOut);
        btnSettings = findViewById(R.id.settings_btn);
        btnViewOrders = findViewById(R.id.view_orders);


        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        prefConfig = new SharedPrefConfig(getApplicationContext());



//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() == null){
//                    startActivity(new Intent(TableActivity.this, LoginActivity.class));
//                }
//            }
//        };

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceConfig.writeLoginStatus(false);
                Intent intent = new Intent(TableActivity.this, TabbedActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefConfig.writeOpsStatus(false);
                startActivity(new Intent(TableActivity.this, SettingsActivity.class));
            }
        });

        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TableActivity.this, ViewOrdersActivity.class));
            }
        });


    }

    public void table(View view){
        Intent intent = new Intent(this, RecyclerTable.class);
        startActivity(intent);
    }

}
