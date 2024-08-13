package com.example.restoreandbackup.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;

public class SpalshScreen extends AppCompatActivity {

    ImageView imageView4,textView,gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        imageView4 = findViewById(R.id.imageView4);
        textView = findViewById(R.id.textView);
        gif = findViewById(R.id.gif);


        size();

        Glide.with(this).load(R.drawable.gif).into(gif);

       Handler handler = new Handler();
        handler.postDelayed(() -> {

            Intent intent = new Intent(SpalshScreen.this, StartActivity.class);
            startActivity(intent);


        },3500);



    }

    private void size() {

        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(imageView4,1026,1068);
        HelperResizer.setMargin(imageView4,0,95,0,0);

         HelperResizer.setSize(textView,647,51);
        HelperResizer.setMargin(textView,0,100,0,0);

         HelperResizer.setSize(gif,500,500);

    }
}