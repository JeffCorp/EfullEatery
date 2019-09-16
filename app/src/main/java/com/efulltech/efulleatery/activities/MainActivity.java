package com.efulltech.efulleatery.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.efulltech.efulleatery.R;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    ImageView bgapp, dialog_search_icon;
    Animation bganim, bgprog, frombottom, bounce_search;
    ProgressBar prog;
    LinearLayout loader, texthome, menubtns;
    Button btn1, btn2,btn3,btn4,btn5;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgapp = (ImageView) findViewById(R.id.bgapp);
        loader = (LinearLayout) findViewById(R.id.loader);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menubtns = (LinearLayout) findViewById(R.id.menubtns);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

//
//        bganim = AnimationUtils.loadAnimation(this, R.anim.bganim);
//        bgprog = AnimationUtils.loadAnimation(this,R.anim.bgprog);
        bounce_search = AnimationUtils.loadAnimation(this,R.anim.bounce_search);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        prog = (ProgressBar) findViewById(R.id.prog);

        bgapp.animate().translationY(-950).setDuration(800).setStartDelay(4000);
//        prog.animate().translationX(-1500).setDuration(800).setStartDelay(1800);
        loader.animate().alpha(0).setDuration(800).setStartDelay(4000);

        texthome.startAnimation(frombottom);

        menubtns.animate().alpha(1).setDuration(800).setStartDelay(2000);

        btn1.animate().translationX(0).setDuration(800).setStartDelay(4000);
        btn2.animate().translationX(0).setDuration(800).setStartDelay(4500);
        btn3.animate().translationX(0).setDuration(800).setStartDelay(5000);
        btn4.animate().translationX(0).setDuration(800).setStartDelay(5500);
        btn5.animate().translationX(0).setDuration(800).setStartDelay(6000);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, RecyclerTable.class));
                myDialog = new Dialog(MainActivity.this);
                myDialog.setContentView(R.layout.order_menu_dialog);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog_search_icon = (ImageView) myDialog.findViewById(R.id.dialog_search_icon);
                Button table_orders = (Button) myDialog.findViewById(R.id.table_orders);

                dialog_search_icon.startAnimation(bounce_search);
                myDialog.show();
                table_orders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.cancel();
                        startActivity(new Intent(MainActivity.this, RecyclerTable.class));
                    }
                });

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewOrdersActivity.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllItems.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });



//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this,OperateChoice.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_TIME_OUT);


    }
}
