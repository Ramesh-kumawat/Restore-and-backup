package com.example.restoreandbackup.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar,progressBar1;
    TextView tv_sd_storage,tv_data_storage;
    ConstraintLayout cl_sd_card;
    ImageView back_btn_main,contacts_btn,application_btn,messages_btn,call_logs_btn,calenders,topbar,phone_img,sd_img;
    private static final long KILOBYTE = 1024;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar1 = findViewById(R.id.progressBar1);
        tv_data_storage = findViewById(R.id.tv_data_storage);
        topbar = findViewById(R.id.topbar);
        phone_img = findViewById(R.id.phone_img);
        sd_img = findViewById(R.id.sd_img);
        back_btn_main = findViewById(R.id.back_btn_main);
        tv_sd_storage = findViewById(R.id.tv_sd_storage);
        cl_sd_card = findViewById(R.id.cl_sd_card);
        application_btn = findViewById(R.id.application_btn);
        messages_btn = findViewById(R.id.messages_btn);
        call_logs_btn = findViewById(R.id.call_logs_btn);
        calenders = findViewById(R.id.calenders);
        contacts_btn = findViewById(R.id.contacts_btn);


        findStorage();
        clickevent();
        size();

    }

    private void size() {

        HelperResizer.getheightandwidth(this);
        HelperResizer.setSize(back_btn_main,33,52);
        HelperResizer.setMargin(back_btn_main,55,0,0,0);

        HelperResizer.setSize(phone_img,118,118);
        HelperResizer.setMargin(phone_img,70,0,0,0);

        HelperResizer.setSize(sd_img,118,118);
        HelperResizer.setMargin(sd_img,65,0,0,0);

        HelperResizer.setSize(progressBar,720,17);
        HelperResizer.setMargin(progressBar,0,27,0,20);

        HelperResizer.setSize(progressBar1,720,17);
        HelperResizer.setMargin(progressBar1,0,27,0,20);

        HelperResizer.setSize(application_btn,1034,219);
        HelperResizer.setMargin(application_btn,0,40,0,0);

        HelperResizer.setSize(contacts_btn,1034,219);
        HelperResizer.setMargin(contacts_btn,0,30,0,0);

        HelperResizer.setSize(messages_btn,1034,219);
        HelperResizer.setMargin(messages_btn,0,30,0,0);

        HelperResizer.setSize(call_logs_btn,1034,219);
        HelperResizer.setMargin(call_logs_btn,0,30,0,0);

        HelperResizer.setSize(calenders,1034,219);
        HelperResizer.setMargin(calenders,0,30,0,0);

        HelperResizer.setSize(topbar,1080,152,true);


    }

    private void clickevent() {

        application_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ApplicationActivity.class);
                startActivity(intent);
            }
        });

        contacts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllDataActivity.class);
                intent.putExtra("list",1);
                startActivity(intent);
            }
        });
        messages_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllDataActivity.class);
                intent.putExtra("list",2);
                startActivity(intent);
            }
        });
        call_logs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllDataActivity.class);
                intent.putExtra("list",3);
                startActivity(intent);
            }
        });
        calenders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllDataActivity.class);
                intent.putExtra("list",4);
                startActivity(intent);
            }
        });

        back_btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void findStorage() {
        StatFs externalStatFs = new StatFs( Environment.getExternalStorageDirectory().getPath());
        long externalTotal;
        long externalFree;
        externalTotal = (externalStatFs.getBlockCountLong() * externalStatFs.getBlockSizeLong() ) / (KILOBYTE * KILOBYTE);
        externalFree = (externalStatFs.getAvailableBlocksLong() * externalStatFs.getBlockSizeLong() ) / (KILOBYTE * KILOBYTE);
        long total =  externalTotal;
        long free =  externalFree;
        long used = total - free;
        progressBar.setMax((int) ((int) total * 0.001));
        progressBar.setProgress((int) ((int) used * 0.001));
        tv_data_storage.setText(String.format("%.2f", used * 0.001)+" GB"+"/"+String.format("%.2f",total * 0.001)+" GB");

        if(readSdCard() != null){
            cl_sd_card.setVisibility(View.VISIBLE);
            long i = getExternalSdCardSize() - getExternalSdCardFreeSize();
            progressBar1.setMax((int) ((int) Math.abs(getExternalSdCardSize()/ (KILOBYTE * KILOBYTE))));
            Log.d("TAG", "findStorage: "+ ((int) getExternalSdCardSize()));
            progressBar1.setProgress((int) ((int) Math.abs( i/ (KILOBYTE * KILOBYTE))) );
            Log.d("TAG", "findStorage: "+ ((int) Math.abs( getExternalSdCardFreeSize()/ (KILOBYTE * KILOBYTE))));
            String fill = formatSize(i);
            String im = formatSize(getExternalSdCardSize());
            tv_sd_storage.setText(fill+"/"+im);
        }else{
            cl_sd_card.setVisibility(View.GONE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public File readSdCard() {
        File[] files = getExternalFilesDirs(null);
        for (File file : files) {
            try {
                if (Environment.isExternalStorageRemovable(file)) {
                    return file;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public long getExternalSdCardSize() {
        File storage = new File("/storage");
        String external_storage_path = "";
        long size = 0;

        if (storage.exists()) {
            File[] files = storage.listFiles();

            for (File file : files) {
                if (file.exists()) {
                    try {
                        if (Environment.isExternalStorageRemovable(file)) {

                            external_storage_path = file.getAbsolutePath();
                            break;
                        }
                    } catch (Exception e) {
                        Log.e("TAG", e.toString());
                    }
                }
            }
        }

        if (!external_storage_path.isEmpty()) {
            File external_storage = new File(external_storage_path);
            if (external_storage.exists()) {
                size = totalSize(external_storage);

            }
        }
        return size;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public long getExternalSdCardFreeSize() {
        File storage = new File("/storage");
        String external_storage_path = "";
        long size = 0;

        if (storage.exists()) {
            File[] files = storage.listFiles();

            for (File file : files) {
                if (file.exists()) {
                    try {
                        if (Environment.isExternalStorageRemovable(file)) {

                            external_storage_path = file.getAbsolutePath();
                            break;
                        }
                    } catch (Exception e) {
                        Log.e("TAG", e.toString());
                    }
                }
            }
        }

        if (!external_storage_path.isEmpty()) {
            File external_storage = new File(external_storage_path);
            if (external_storage.exists()) {
               size =  AvailableSize(external_storage);

            }
        }
        return size;
    }


    private static long totalSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize, totalBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }

        return totalBlocks * blockSize;
    }
    private static long AvailableSize(File file) {
        StatFs stat = new StatFs(file.getPath());
        long blockSize, availableBlocks;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }

        return availableBlocks * blockSize;
    }

    private static String formatSize(float size) {
        String suffix = null;

        Log.d("TAG", "formatSize: 1" + size);

    if (size >= 1024) {
            suffix = " KB";
            size /= 1024;
            Log.d("TAG", "formatSize: 2" + size);
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
                Log.d("TAG", "formatSize: 3" + size);
                if (size >= 1024) {
                 suffix = " GB";
                    size /= 1024;
                    Log.d("TAG", "formatSize: 4 " + size);

                }
            }

        }
        StringBuilder resultBuilder = new StringBuilder(String.format("%.2f",Float.parseFloat(size+"")));
        int commaOffset = resultBuilder.length() - 3;
        while (commaOffset > 0) {
            commaOffset -= 3;
        }
        if (suffix != null) resultBuilder.append(suffix);
        return resultBuilder.toString();
    }
    }
