package com.example.restoreandbackup.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;

public class StartActivity extends AppCompatActivity {


    ImageView logo_img,rate_app_btn,share_app_btn,privacy_app_btn,start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        start_btn = findViewById(R.id.start_btn);
        logo_img = findViewById(R.id.logo_img);
        rate_app_btn = findViewById(R.id.rate_app_btn);
        share_app_btn = findViewById(R.id.share_app_btn);
        privacy_app_btn = findViewById(R.id.privacy_app_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permission()) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(StartActivity.this, new String[]{"android.permission.READ_CALENDAR", "android.permission.READ_CONTACTS", "android.permission.READ_SMS", "android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_CALL_LOG","android.permission.GET_PACKAGE_SIZE","android.permission.WRITE_SMS","android.permission.WRITE_CALL_LOG","android.permission.WRITE_CALENDAR"},12);

                }
            }

        });
        size();
    }

    private void size() {


        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(logo_img,1080,1061,true);

        HelperResizer.setSize(start_btn,940,361);
        HelperResizer.setMargin(start_btn,0,50,0,0);

        HelperResizer.setSize(rate_app_btn,255,192);
        HelperResizer.setMargin(rate_app_btn,78,0,0,0);

        HelperResizer.setSize(share_app_btn,255,192);

        HelperResizer.setSize(privacy_app_btn,255,192);


    }

    private boolean permission() {
        return ((((ContextCompat.checkSelfPermission(this, "android.permission.READ_CALENDAR") + ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS")) + ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS")) + ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE")) + ContextCompat.checkSelfPermission(this, "android.permission.READ_CALL_LOG")) +  ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")  +  ContextCompat.checkSelfPermission(this, "android.permission.GET_PACKAGE_SIZE")+  ContextCompat.checkSelfPermission(this, "android.permission.WRITE_CALL_LOG") +ContextCompat.checkSelfPermission(this, "android.permission.WRITE_CALENDAR") == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(StartActivity.this,ExitActivity.class);
        startActivity(intent);

    }
}