package com.example.restoreandbackup.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.example.restoreandbackup.BuildConfig;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.Models.SMSModel;
import com.example.restoreandbackup.R;
import com.google.gson.Gson;
import com.obsez.android.lib.filechooser.ChooserDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;

public class AllDataActivity extends AppCompatActivity {
    ImageView back_btn;
    TextView tv_total, tv_title;
    ImageView backup_btn, restore_btn, list_view_btn, view_backups_btn, upload_btn, delete_btn,iv_topbar;
    int i;
    EditText et_main;
    ProgressDialog progressDialog;
    private TextView path_backup;
    File dir;
    ArrayList<String> smsBuffer = new ArrayList<>();
    ArrayList<String> calllogBuffer = new ArrayList<>();
    ArrayList<SMSModel>  smsmodels = new ArrayList<>();
    ArrayList<String> calllog = new ArrayList<>();
    ArrayList<String> vCard = new ArrayList<>();
    JSONArray array;
    BufferedReader br;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);


        back_btn = findViewById(R.id.back_btn);
        tv_total = findViewById(R.id.tv_total);
        backup_btn = findViewById(R.id.backup_btn);
        restore_btn = findViewById(R.id.restore_btn);
        list_view_btn = findViewById(R.id.list_view_btn);
        view_backups_btn = findViewById(R.id.view_backups_btn);
        upload_btn = findViewById(R.id.upload_btn);
        delete_btn = findViewById(R.id.delete_btn);
        tv_title = findViewById(R.id.tv_title);
        iv_topbar = findViewById(R.id.iv_topbar);

        progressDialog = new ProgressDialog(AllDataActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Creating...");

        clickevent();


        Intent intent = getIntent();
        i = intent.getIntExtra("list", 0);

        setdata();
        size();

    }

    private void size() {


        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(iv_topbar,1080,152,true);

        HelperResizer.setSize(backup_btn,441,429);
        HelperResizer.setMargin(backup_btn,60,70,0,0);

        HelperResizer.setSize(restore_btn,441,429);
        HelperResizer.setMargin(restore_btn,0,70,60,0);

        HelperResizer.setSize(list_view_btn,441,429);
        HelperResizer.setMargin(list_view_btn,0,60,0,0);

        HelperResizer.setSize(view_backups_btn,441,429);
        HelperResizer.setMargin(view_backups_btn,0,60,0,0);

        HelperResizer.setSize(back_btn,33,52);
        HelperResizer.setMargin(back_btn,55,0,0,0);

        HelperResizer.setSize(upload_btn,441,429);
        HelperResizer.setMargin(upload_btn,0,60,0,0);

        HelperResizer.setSize(delete_btn,441,429);
        HelperResizer.setMargin(delete_btn,0,60,0,0);




    }

    @SuppressLint("SetTextI18n")
    private void setdata() {

        switch (i) {

            case 0:

                break;
            case 1:
                tv_title.setText("Contacts");
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                int count = cursor.getCount();
                tv_total.setText(String.valueOf(count));
                dir = new File(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Contacts");
                break;
            case 2:
                tv_title.setText("Message");
                 Cursor cursor1 = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
                int count1 = cursor1.getCount();
                tv_total.setText(String.valueOf(count1));
                dir = new File(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Messages");
                break;
//                if (cursor1.moveToFirst()) { // must check the result to prevent exception
//                    do {
//                        String msgData = "";
//                        for(int idx=0;idx<cursor1.getColumnCount();idx++)
//                        {
//                            msgData += " " + cursor1.getColumnName(idx) + ":" + cursor1.getString(idx);
//                        }
//                        // use msgData
//                    } while (cursor1.moveToNext());
//                } else {
//                    // empty box, no SMS
//                }
            case 3:
                tv_title.setText("Call Logs");
                Cursor cursor2 = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
                int count2 = cursor2.getCount();
                tv_total.setText(String.valueOf(count2));
                dir = new File(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Call Log");
                break;

            case 4:
                tv_title.setText("Calender");
                Cursor cursor3 = getContentResolver().query(Uri.parse("content://com.android.calendar/events"), new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation"}, null, null, null);
                int count3 = cursor3.getCount();
                tv_total.setText(String.valueOf(count3));
                dir = new File(Environment.getExternalStorageDirectory() + "/Download//All Restore Data/Calenders");

                break;


        }


    }

    private void clickevent() {

        back_btn.setOnClickListener(view -> onBackPressed());


        backup_btn.setOnClickListener(view -> {

            Dialog dialog = new Dialog(AllDataActivity.this);
            dialog.setContentView(R.layout.dialog_backup_view);
            dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
            dialog.setCancelable(true);
            dialog.show();

            ImageView cancel_btn = dialog.findViewById(R.id.cancel_btn);
            ImageView save_btn = dialog.findViewById(R.id.save_btn);
            ConstraintLayout cl_dialog_name = dialog.findViewById(R.id.cl_dialog_name);
            ImageView popup_topbbar = dialog.findViewById(R.id.imageView6);

            et_main = dialog.findViewById(R.id.et_main);
            path_backup = dialog.findViewById(R.id.path_backup);

            HelperResizer.setSize(cl_dialog_name,942,609);

            HelperResizer.setSize(et_main,864,123);
            HelperResizer.setMargin(et_main,0,10,35,0);
            HelperResizer.setPadding(et_main,8,0,0,0);

            HelperResizer.setSize(save_btn,302,94);
            HelperResizer.setMargin(save_btn,0,0,125,0);

            HelperResizer.setSize(popup_topbbar,942,99);

            HelperResizer.setSize(cancel_btn,302,94);


            et_main.setText(GenerateName());
            path_backup.setText(dir.toString());


            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                    progressDialog.show();

                    Thread thread = new Thread(runnable);
                    thread.start();
//                        try {
//                            getVcardString();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        getVCF();


//
//                        try {
//
//                            File vdfdirectory = new File(Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
//
//                            if (!vdfdirectory.exists()) {
//                                vdfdirectory.mkdirs();
//                            }
//
//                            File vcfFile = new File(vdfdirectory, et_main.getText() + ".vcf");
//
//                            FileWriter fw = null;
//                            fw = new FileWriter(vcfFile);
//                            fw.close();
//
//                    Intent i = new Intent(); //this will import vcf in contact list
//                    i.setAction(android.content.Intent.ACTION_VIEW);
//                    i.setDataAndType(Uri.fromFile(vcfFile), "text/x-vcard");
//                    startActivity(i);
//
//                            Toast.makeText(AllDataActivity.this, "Created!", Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                }
            });


            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        });

        restore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                File file = new File(path);
//                MimeTypeMap map = MimeTypeMap.getSingleton();
//                String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
//                String type = map.getMimeTypeFromExtension(ext);
//                if (type == null)
//                    type = "*/*";
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                Uri data = Uri.fromFile(file);
//                intent.setDataAndType(data, type);
//                startActivity(intent);
                switch (i) {

                    case 1:
                        new ChooserDialog(AllDataActivity.this)
                                .withFilter(false, false, "vcf")
                                .withStartFile(dir.toString())
                                .withFileIcons(false,getResources().getDrawable(R.drawable.folder),getResources().getDrawable(R.drawable.folder))
                                .withResources(R.string.app_name, R.string.title_choose, R.string.dialog_cancel)
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {
                                        Toast.makeText(AllDataActivity.this, "FILE: " + path, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        File file = new File(path);
                                        Uri data = FileProvider.getUriForFile(AllDataActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                                        intent.setDataAndType(data, "text/x-vcard");
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        startActivity(intent);

                                    }
                                })
                                .build()
                                .show();



                        break;

                    case 2:
                        new ChooserDialog(AllDataActivity.this)
                                .withFilter(false, false, "xml")
                                .withStartFile(dir.toString())
                                .withResources(R.string.app_name, R.string.title_choose, R.string.dialog_cancel)
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {
//                                        progressDialog.setMessage("uploading...");
//                                        progressDialog.show();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.show();
                                            }
                                        });


                                            String ret = "";

                                            try {


//                                                if ( inputStream != null ) {
                                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile));
                                                    String receiveString = "";
                                                    StringBuilder stringBuilder = new StringBuilder();

                                                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                                                        stringBuilder.append("\n").append(receiveString);
                                                    }

//                                                    inputStream.close();
                                                    ret = stringBuilder.toString();
                                                    array = new JSONArray(ret);
//                                                }
                                            }
                                            catch (FileNotFoundException e) {
                                                Log.e("login activity", "File not found: " + e.toString());
                                            } catch (IOException e) {
                                                Log.e("login activity", "Can not read file: " + e.toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        Thread thread = new Thread(f);
                                        thread.start();


//                                        BufferedReader br = null;
//                                        try {
//                                            br = new BufferedReader(new FileReader(pathFile));
//                                        } catch (FileNotFoundException e) {
//                                            e.printStackTrace();
//                                        }
//                                        String line = null;
//                                        try {
//                                            line = br.readLine();
//                                            while (line != null) {
//                                                String[] ans = line.split(",");
//                                                Log.d("TAG", "onChoosePath: " + ans[0]);
//                                                Log.d("TAG", "onChoosePath: " + ans[1]);
//                                                Log.d("TAG", "onChoosePath: " + ans[2]);
//                                                Log.d("TAG", "onChoosePath: " + ans[3]);
//                                                Log.d("TAG", "onChoosePath: " + ans[4]);
////                                                Log.d("StackOverflow", line);
////                                                line = br.readLine();
////                                                Log.d("TAG", "onChoosePath: 1" + br.readLine());
////                                                line = br.readLine();
//
//                                                RestoreSms(ans[0], ans[1], ans[2], ans[3], ans[4]);
//
//                                                line = br.readLine();
//
//                                            }
//
//                                            Toast.makeText(AllDataActivity.this, "backup success", Toast.LENGTH_SHORT).show();
//
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
////                                        Thread thread = new Thread(runu);
////                                        thread.start();
                                    }
                                })
                                .build()
                                .show();
                        break;
                    case 3:

                        new ChooserDialog(AllDataActivity.this)
                                .withFilter(false, false, "xml")
                                .withStartFile(dir.toString())
                                .withResources(R.string.app_name, R.string.title_choose, R.string.dialog_cancel)
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {
//                                        progressDialog.setMessage("uploading...");
//                                        progressDialog.show();
//                                        try {
                                        progressDialog.show();

                                        try {
                                            br = new BufferedReader(new FileReader(pathFile));
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        Thread thread = new Thread(runu);
                                        thread.start();
                                    }


                                })
                                .build()
                                .show();
                        break;

                    case 4:

                        new ChooserDialog(AllDataActivity.this)
                                .withFilter(false, false, "xml")
                                .withStartFile(dir.toString())
                                .withResources(R.string.app_name, R.string.title_choose, R.string.dialog_cancel)
                                .withChosenListener(new ChooserDialog.Result() {
                                    @Override
                                    public void onChoosePath(String path, File pathFile) {
//                                        progressDialog.setMessage("uploading...");
//                                        progressDialog.show();

                                        BufferedReader br = null;
                                        try {
                                            br = new BufferedReader(new FileReader(pathFile));
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        String line = null;
                                        try {
                                            line = br.readLine();
                                            while (line != null) {
                                                String[] ans = line.split(",");
                                                Log.d("TAG", "onChoosePath: " + ans[0]);
                                                Log.d("TAG", "onChoosePath: " + ans[1]);
                                                Log.d("TAG", "onChoosePath: " + ans[2]);
                                                Log.d("TAG", "onChoosePath: " + ans[3]);
                                                Log.d("TAG", "onChoosePath: " + ans[4]);
                                                Log.d("TAG", "onChoosePath: " + ans[5]);
                                                Log.d("TAG", "onChoosePath: " + ans[6]);
//                                                Log.d("StackOverflow", line);
//                                                line = br.readLine();
//                                                Log.d("TAG", "onChoosePath: 1" + br.readLine());
//                                                line = br.readLine()
                                                RestoreCalenderEvent(ans[0], ans[1], ans[2], ans[3], ans[4], ans[5],ans[6]);

                                                line = br.readLine();

                                            }

                                            Toast.makeText(AllDataActivity.this, "restore success", Toast.LENGTH_SHORT).show();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
//                                        String[] ans = line.split(",");
//
//                                            Log.d("TAG", "onChoosePath: "+ans[0]);
//                                            Log.d("TAG", "onChoosePath: "+ans[1]);
//                                            Log.d("TAG", "onChoosePath: "+ans[2]);
//                                            Log.d("TAG", "onChoosePath: "+ans[3]);

//                                            while ((line = br.readLine()) != null) {
//                                                text.append(line);
//                                                Log.d("TAG", "onChoosePath: "+line);
//
//                                            }
//
//                                        }
//                                        catch (IOException e) {
//                                            //You'll need to add proper error handling here
//                                        }

//                                        Thread thread = new Thread(f);
//                                        thread.start();


                                    }


                                })
                                .build()
                                .show();
                        break;


                }

            }
        });


        list_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllDataActivity.this, ContactslistActivity.class);
                intent.putExtra("path", dir.toString());

                switch (i) {

                    case 1:
                        intent.putExtra("List", 1);
                        break;
                    case 2:
                        intent.putExtra("List", 2);
                        break;
                    case 3:
                        intent.putExtra("List", 3);
                        break;
                    case 4:
                        intent.putExtra("List", 4);
                        break;
                }

                startActivity(intent);
            }
        });

        view_backups_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AllDataActivity.this, SelectBackupFileActivity.class);
                intent.putExtra("path", dir.toString());
                intent.putExtra("Contacts", 1);
                startActivity(intent);

            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AllDataActivity.this, SelectBackupFileActivity.class);
                intent.putExtra("path", dir.toString());
                intent.putExtra("Contacts", 2);
                startActivity(intent);

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AllDataActivity.this, SelectBackupFileActivity.class);
                intent.putExtra("path", dir.toString());
                intent.putExtra("Contacts", 3);
                startActivity(intent);
            }
        });

    }


    public void RestoreCallLogs(String name, String number, String date, String type, String duration) {
        ContentValues values = new ContentValues();
        if (number.equals("null") || number.isEmpty()) number = "0";
        values.put(CallLog.Calls.NUMBER, number);

        if (date.equals("null") || date.isEmpty()) date = "0";
        values.put(CallLog.Calls.DATE, date);

        if (type.equals("null") || type.isEmpty()) type = "0";
        values.put(CallLog.Calls.TYPE, type);

        if (duration.equals("null") || duration.isEmpty()) duration = "0";
        values.put(CallLog.Calls.DURATION, duration);

        if (name != null) {
            if (!name.equals(""))
                values.put(CallLog.Calls.CACHED_NAME, name);
        }
try {
    Log.i("TAG", "RestoreCallLogs: "+values);

    getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
}catch (Exception e){
    Log.i("TAG", "RestoreCallLogs: "+e);
}




    }

    public void RestoreSms(String address, String name, String Date, String body, String type) {
        ContentValues values = new ContentValues();

        if (address.equals("null") || address.isEmpty()) address = "0";
        values.put("address", address);

        if (name.equals("null") || name.isEmpty()) name = "0";
        values.put("person", name);

        if (Date.equals("null") || Date.isEmpty()) Date = "0";
            values.put("date", Date);

        if (body.equals("null") || body.isEmpty()) body = "0";
        values.put("body", body);

        if (type.equals("null") || type.isEmpty()) type = "0";
        values.put("type", type);


        getContentResolver().insert(Telephony.Sms.CONTENT_URI, values);
    }

    public void RestoreCalenderEvent(String calender_id, String title, String description, String DateSt, String DateEnd, String location,String allday) {
        ContentValues values = new ContentValues();
        if (calender_id.equals("null") || calender_id.isEmpty()) calender_id = "0";
        values.put(CalendarContract.Events.CALENDAR_ID, calender_id);
        if (title.equals("null") || title.isEmpty()) title = "0";
        values.put(CalendarContract.Events.TITLE, title);
        if (description.equals("null") || description.isEmpty()) description = "0";
        values.put(CalendarContract.Events.DESCRIPTION, description);
        if (DateSt.equals("null") || DateSt.isEmpty()) DateSt = "0";
        values.put(CalendarContract.Events.DTSTART, DateSt);
        if (DateEnd.equals("null ") || DateEnd.isEmpty()) DateEnd = "0";
        values.put(CalendarContract.Events.DTEND, DateSt);
        if (location.equals("null") || location.isEmpty()) location = "0";
        values.put(CalendarContract.Events.EVENT_LOCATION, location);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, DateSt);
        values.put(CalendarContract.Events.ALL_DAY, allday);

        try {

            getContentResolver().insert(Uri.parse("content://com.android.calendar/events"), values);
        }catch (Exception e){
            Log.i("TAG", "RestoreCalenderEvent: "+e);
        }
    }

    @SuppressLint("Range")
    public String getContactName(String phoneNumber) {

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }

    Runnable runu = new Runnable() {
        @Override
        public void run() {

            String line = null;
            try {
                assert br != null;
                line = br.readLine();
                while (line != null) {
                    String[] ans = line.split(",");
                    Log.d("TAG", "onChoosePath: " + ans[0]);
                    Log.d("TAG", "onChoosePath: " + ans[1]);
                    Log.d("TAG", "onChoosePath: " + ans[2]);
                    Log.d("TAG", "onChoosePath: " + ans[3]);

//                                                Log.d("StackOverflow", line);
//                                                line = br.readLine();
//                                                Log.d("TAG", "onChoosePath: 1" + br.readLine());
//                                                line = br.readLine();

                    switch (ans[1]) {
                        case "OUTGOING":

                            ans[1] = "2";

                            break;
                        case "INCOMING":
                            ans[1] = "1";
                            break;

                        default:
                            ans[1] = "3";
                            break;
                    }

                    String name = getContactName(ans[0]);
                    Executors.newSingleThreadExecutor().execute(() -> RestoreCallLogs(name, ans[0], ans[2], ans[1], ans[3]));
                    line = br.readLine();

                }
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AllDataActivity.this, "Restore success", Toast.LENGTH_SHORT).show();

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
//                                        String[] ans = line.split(",");
//
//                                            Log.d("TAG", "onChoosePath: "+ans[0]);
//                                            Log.d("TAG", "onChoosePath: "+ans[1]);
//                                            Log.d("TAG", "onChoosePath: "+ans[2]);
//                                            Log.d("TAG", "onChoosePath: "+ans[3]);

//                                            while ((line = br.readLine()) != null) {
//                                                text.append(line);
//                                                Log.d("TAG", "onChoosePath: "+line);
//
//                                            }
//
//                                        }
//                                        catch (IOException e) {
//                                            //You'll need to add proper error handling here
//                                        }

//                                        Thread thread = new Thread(f);
//                                        thread.start();

            progressDialog.dismiss();

        }
    };

    Runnable f = new Runnable() {
        @Override
        public void run() {

            ContentResolver contentResolver = getContentResolver();

            ContentValues values = new ContentValues();

            for (int i = 0; i < array.length(); i++) {

                JSONObject object = null;
                try {
                    object = array.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String Date = null;
                try {
                    Date = object.getString("Date");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                String date1 = getDate(Long.parseLong(Date));

//                                           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

//                                           Date date1 = null;
//                                           try {
//                                               date1 = format.parse(lastModifiedDate.toString());
//                                           } catch (ParseException e) {
//                                               Log.d("TAG", "onChoosePath: 0 ");
//
//                                               e.printStackTrace();
//                                           }

                try {
                    values.put("address", object.getString("Address"));

                } catch (JSONException e) {
                    Log.d("TAG", "onChoosePath: 0 ");
                    e.printStackTrace();
                }

                try {
                    values.put("body", object.getString("Body"));
                } catch (JSONException e) {
                    Log.d("TAG", "onChoosePath: 1 ");

                    e.printStackTrace();
                }
                try {
                    values.put("type", object.getString("Type"));
                } catch (JSONException e) {
                    Log.d("TAG", "onChoosePath: 2");

                    e.printStackTrace();
                }

                contentResolver.insert(Telephony.Sms.Inbox.CONTENT_URI, values);
            }
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        progressDialog.dismiss();
    }
});



//
//            ContentValues values = new ContentValues();
//
//            for (int i = 0; i < array.length(); i++) {
//
//                JSONObject object = null;
//                try {
//                    object = array.getJSONObject(i);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                String Date = null;
//                try {
//                    Date = object.getString("Date");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                long date12 = Long.parseLong(Date);
//
//                String date1 = getDate(date12);
//
////                                           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//
////                                           Date date1 = null;
////                                           try {
////                                               date1 = format.parse(lastModifiedDate.toString());
////                                           } catch (ParseException e) {
////                                               Log.d("TAG", "onChoosePath: 0 ");
////
////                                               e.printStackTrace();
////                                           }
//
//                try {
//                    values.put("address", object.getString("Address"));
//
//                } catch (JSONException e) {
//                    Log.d("TAG", "onChoosePath: 0 ");
//                    e.printStackTrace();
//                }
////                                           values.put("read", object.getString("read"));
//
////                                           values.put("date", date1);
//                try {
//                    values.put("body", object.getString("Body"));
//                } catch (JSONException e) {
//                    Log.d("TAG", "onChoosePath: 1 ");
//
//                    e.printStackTrace();
//                }
//                try {
//                    values.put("type", object.getString("Type"));
//                } catch (JSONException e) {
//                    Log.d("TAG", "onChoosePath: 2");
//
//                    e.printStackTrace();
//                }
//
//                getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
//            }
//
//            progressDialog.dismiss();

        }
    };

    public String GenerateName() {

        Random random = new Random();
        int id = random.nextInt(100);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd");
        Date myDate = new Date();
        String date = timeStampFormat.format(myDate);
        return tv_title.getText().toString().replaceAll("\\s+", "") + date + id + id;
    }

    public void getVCF() throws FileNotFoundException {

        final String vfile = et_main.getText().toString() + ".vcf";

        Cursor phones = AllDataActivity.this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path = dir.toString() + File.separator + vfile;

        FileOutputStream mFileOutputStream = new FileOutputStream(path, true);


        if (phones.getCount() > 0) {

            phones.moveToFirst();

            for (int i = 0; i < phones.getCount(); i++) {

                @SuppressLint("Range") String lookupKey = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);

//           String string =  uri.getQueryParameter("VERSION");
//            Log.i("TAG", "getVCF: "+uri);

                AssetFileDescriptor fd;

                try {

                    fd = getContentResolver().openAssetFileDescriptor(uri, "r");

                    if (fd != null) {
                        FileInputStream fis = fd.createInputStream();
                        byte[] buf = readBytes(fis);

                        fis.read(buf);

                        String VCard = new String(buf);
                        vCard.add(VCard);

                        mFileOutputStream.write(VCard.getBytes());

                        phones.moveToNext();

                        Log.d("Vcard", VCard);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

            Intent intent = new Intent(AllDataActivity.this, CloudBackupActivity.class);
            intent.putExtra("path",dir.toString());
            intent.putExtra("name",et_main.getText().toString());
            intent.putExtra("extension",".vcf");
            startActivity(intent);
            progressDialog.dismiss();
                }
            });
        }else{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
           progressDialog.dismiss();
        Toast.makeText(AllDataActivity.this, "There is no data in Contacts", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

//    public void getVCF() {
//
//        final String vfile = "Contacts.vcf";
//
//        Cursor phones = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//
//        phones.moveToFirst();
//
//        for (int i = 0; i < phones.getCount(); i++) {
//
//            @SuppressLint("Range") String lookupKey = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
//            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
//            AssetFileDescriptor fd;
//            try {
//                fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");
//                FileInputStream fis = fd.createInputStream();
//                byte[] buf = new byte[(int) fd.getDeclaredLength()];
//                fis.read(buf);
//                String VCard = new String(buf);
//                String path = Environment.getExternalStorageDirectory()
//                        .toString() + File.separator + vfile;
//                FileOutputStream mFileOutputStream = new FileOutputStream(path,
//                        true);
//                mFileOutputStream.write(VCard.toString().getBytes());
//                phones.moveToNext();
//                File filevcf = new File(path);
//                Log.d("Vcard", VCard);
//            } catch (Exception e1) {
//
//                e1.printStackTrace();
//            }
//        }
//    }

    Runnable runnable = new Runnable() {
        public void run() {
            switch (i) {
                case 0:
                    break;
                case 1:
                    try {
                        getVCF();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    backupSMS();
                    break;
                case 3:
                    getCallDetails();
                    break;
                case 4:
                    getCalenderdata();
                    break;

            }


        }
    };

    @SuppressLint("Range")
    private void getCalenderdata() {
        {

            calllogBuffer.clear();

            Cursor cursor3 = getContentResolver().query(Uri.parse("content://com.android.calendar/events"), new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation","allDay"}, null, null, null);

            String[] columns1 = new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "eventLocation","allDay"};

            if (cursor3.getCount() > 0) {
                String count = Integer.toString(cursor3.getCount());

                Log.d("Count", count);

                while (cursor3.moveToNext()) {

                    String calenderId = cursor3.getString(cursor3.getColumnIndex(columns1[0]));
                    if( calenderId == null || calenderId.equals("") || calenderId.isEmpty()){
                        calenderId = "0";
                    }
                    String titledId = cursor3.getString(cursor3.getColumnIndex(columns1[1]));
                    if(titledId == null || titledId.equals("") || titledId.isEmpty()){
                        titledId = "0";
                    }
                    String allday = cursor3.getString(cursor3.getColumnIndex(columns1[6]));
                    if(allday == null || allday.equals("") || allday.isEmpty()){
                        allday = "0";
                    }
                    String description = cursor3.getString(cursor3.getColumnIndex(columns1[2]));
                    if(description == null || description.equals("")){
                        description = "no description";
                    }
                    String dtstart = cursor3.getString(cursor3.getColumnIndex(columns1[3]));
                    if(dtstart == null || dtstart.equals("") ){
                        dtstart = "0";
                    }
                    String dtend = cursor3.getString(cursor3.getColumnIndex(columns1[4]));
                    if(dtend == null || dtend.equals("") || dtend.isEmpty()){
                        dtend = "0";
                    }
                    String eventLocation = cursor3.getString(cursor3.getColumnIndex(columns1[5]));
                    if(eventLocation == null || eventLocation.equals("") || eventLocation.isEmpty()){
                        eventLocation = "none";
                    }


                    calllogBuffer.add(calenderId + "," + titledId + "," + description + "," + dtstart + "," + dtend + " ," + eventLocation+","+allday);

                }
                generatecalllog(calllogBuffer);

            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AllDataActivity.this, "There is No Data in Calender", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        }


    }

    private void getCallDetails() {

        calllog.clear();
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        if(managedCursor.getCount() > 0) {
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                String callDuration = managedCursor.getString(duration);
                String dir1 = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir1 = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir1 = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir1 = "MISSED";
                        break;

                }
//            sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir1+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//            sb.append("\n----------------------------------");
                calllog.add(phNumber + "," + dir1 + " ," + callDate + " ," + callDuration);

            }

            managedCursor.close();
            genratelistofcalllog(calllog);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
            Toast.makeText(AllDataActivity.this, "There is no data in CallLog", Toast.LENGTH_SHORT).show();
                }
            });
        }
        progressDialog.dismiss();
    }


    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    @SuppressLint("Range")
    private void backupSMS() {

        smsBuffer.clear();

        if (!dir.exists()) {
            dir.mkdirs();
        }
        Cursor cursor1 = getContentResolver().query(Telephony.Sms.CONTENT_URI, new String[]{"address", "person", "date", "body", "type"}, null, null, null);

        String[] columns = new String[]{"address", "person", "date", "body", "type"};

        if (cursor1.getCount() > 0) {
            String count = Integer.toString(cursor1.getCount());

            Log.d("Count", count);

            while (cursor1.moveToNext()) {

                String address = cursor1.getString(cursor1.getColumnIndex(columns[0]));
                String name = cursor1.getString(cursor1.getColumnIndex(columns[1]));
                String date = cursor1.getString(cursor1.getColumnIndex(columns[2]));
                String msg = cursor1.getString(cursor1.getColumnIndex(columns[3]));
                String type = cursor1.getString(cursor1.getColumnIndex(columns[4]));

/**/
                smsmodels.add(new SMSModel(address, name, date, msg, type));
//                smsmodels.add(address + "," + name + "," + date + "," + msg + "," + type);


            }
            cursor1.close();

//            Gson gson = new Gson();
//            String arrayData = gson.toJson(smsmodels);
            Gson gson = new Gson();
            String arrayData = gson.toJson(smsmodels);
            generateCSVFileForSMS(arrayData);
        }else {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
              progressDialog.dismiss();
            Toast.makeText(AllDataActivity.this, "There is no data in SMS", Toast.LENGTH_SHORT).show();
             }
         });
        }
    }
//    private void generateCSVFileForSMS(String list) {
//
//        try {
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
//            String storage_path = dir + File.separator + et_main.getText().toString() + ".xml";
//            FileWriter write = new FileWriter(storage_path);
//
//
//            write.append(list);
//
//
//            write.flush();
//            write.close();
//        } catch (NullPointerException e) {
//            System.out.println("Nullpointer Exception " + e);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        progressDialog.dismiss();
//
//    }
    private void genratelistofcalllog(ArrayList<String> list) {

        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String storage_path = dir + File.separator + et_main.getText().toString() + ".xml";
            FileWriter write = new FileWriter(storage_path);


            for (String s : list) {
                write.append(s);
                write.append('\n');
            }
            write.flush();
            write.close();

            Intent intent = new Intent(AllDataActivity.this, CloudBackupActivity.class);
            intent.putExtra("path",dir.toString());
            intent.putExtra("name",et_main.getText().toString());
            intent.putExtra("extension",".xml");
            startActivity(intent);

        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception " + e);
            //  e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }

    private void generatecalllog(ArrayList<String> list) {

        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String storage_path = dir + File.separator + et_main.getText().toString() + ".xml";
            FileWriter write = new FileWriter(storage_path);

            for (String s : list) {
                write.append(s);
                write.append('\n');
            }
            write.flush();
            write.close();

            Intent intent = new Intent(AllDataActivity.this, CloudBackupActivity.class);
            intent.putExtra("path",dir.toString());
            intent.putExtra("name",et_main.getText().toString());
            intent.putExtra("extension",".xml");
            startActivity(intent);

        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception " + e);
            //  e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }

    private void generateCSVFileForSMS(String list)
    {

        try
        {
            if(!dir.exists()){
                dir.mkdir();
            }
            String storage_path = dir + File.separator + et_main.getText().toString()+".xml";
            FileWriter write = new FileWriter(storage_path);


            write.append(list);


            write.flush();
            write.close();

        }

        catch (NullPointerException e)
        {
            System.out.println("Nullpointer Exception "+e);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        progressDialog.dismiss();
        Intent intent = new Intent(AllDataActivity.this, CloudBackupActivity.class);
        intent.putExtra("path",dir.toString());
        intent.putExtra("name",et_main.getText().toString());
        intent.putExtra("extension",".xml");
        startActivity(intent);

    }

    }


