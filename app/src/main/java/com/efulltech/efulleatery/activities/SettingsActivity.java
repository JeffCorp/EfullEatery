package com.efulltech.efulleatery.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.efulltech.efulleatery.R;
import com.efulltech.efulleatery.SettingsFragment;
import com.efulltech.efulleatery.adapters.RecyclerAdapterSettings;
import com.efulltech.efulleatery.sharedPref.SharedPrefConfig;
import com.efulltech.efulleatery.sharedPref.SharedPreferenceConfig;
import com.efulltech.efulleatery.table.models.SettingsItems;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Button set1,set2;

    private SharedPreferenceConfig preferenceConfig;
    private SharedPrefConfig prefConfig;
    private RecyclerAdapterSettings recyclerAdapterSettings;
    private RecyclerView recyclerView;
    private List<SettingsItems> settingsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsItems = new ArrayList<>();

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerMenuOrg);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_orange);




        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState!= null)
                return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();
        }


//        prefConfig = new SharedPrefConfig(getApplicationContext());
//
//        set1 = findViewById(R.id.settings_item);
//        set2 = findViewById(R.id.settings_item2);
//
//
//        set1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SettingsActivity.this, UploadStockActivity.class));
//            }
//        });
//
//        set2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prefConfig.writeOpsStatus(false);
//                preferenceConfig.writeLoginStatus(false);
//                startActivity(new Intent(SettingsActivity.this, OperateChoice.class));
//            }
//        });
//
//        recyclerAdapterSettings = new RecyclerAdapterSettings(settingsItems, this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(recyclerAdapterSettings);
//
//
//        settingsItems.add(new SettingsItems(R.drawable.ic_settings_black, "New Settings"));
    }
}
