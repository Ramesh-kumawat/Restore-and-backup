package com.example.restoreandbackup.Activities;

import static com.example.restoreandbackup.Adapters.CalendarEventAdapter.clickcalender;
import static com.example.restoreandbackup.Adapters.EventAdapter.selectLong;
import static com.example.restoreandbackup.Adapters.ListOfContactsAdapter.longclick;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Adapters.CalendarEventAdapter;
import com.example.restoreandbackup.Adapters.EventAdapter;
import com.example.restoreandbackup.Adapters.ListOfContactsAdapter;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.Models.ListClickModel;
import com.example.restoreandbackup.Models.CallLogModel;
import com.example.restoreandbackup.Models.ContactDatamodel;
import com.example.restoreandbackup.Models.ListOfCalenderEvent;
import com.example.restoreandbackup.Models.ListOfContarcts;
import com.example.restoreandbackup.Models.ListofCalender;
import com.example.restoreandbackup.Models.ListofEvents;
import com.example.restoreandbackup.R;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ContactslistActivity extends AppCompatActivity {


    RecyclerView rv_list;
    ProgressDialog progressDialog;
    ImageView back_list_btn, import_cc;
    LinearLayout ll_list;
    TextView tv_title_list, tv_select_data;
    ArrayList<ListOfContarcts> listOfContarcts = new ArrayList<>();
    ArrayList<ListofEvents> listofEvents = new ArrayList<>();
    ArrayList<ListOfCalenderEvent> listOfCalenderEvents = new ArrayList<>();
    ArrayList<ListofCalender> listofCalenders = new ArrayList<>();
    ListOfContactsAdapter listOfContactsAdapter;
    ArrayList<ListClickModel> listselect = new ArrayList<>();
    ArrayList<ContactDatamodel> contactDatamodels = new ArrayList<>();
    ArrayList<String> calendereventbuffer = new ArrayList<>();
    ArrayList<CallLogModel> callLogModels = new ArrayList<>();
    ArrayList<String> calllogBuffer = new ArrayList<>();
    ArrayList<String> vCard = new ArrayList<>();
    EventAdapter eventAdapter;
    private int i;
    CalendarEventAdapter calendarEventAdapter;
    ProgressBar progress_bar;
    TextView tv_progress;
    Cursor cur;

    loadcalllog loadcalllog;
    private int list = 1;
    private EditText et_main;
    private TextView path_backup;
    private File dir;
    private ImageView delete_contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);

        rv_list = findViewById(R.id.rv_list);
        progress_bar = findViewById(R.id.progress_bar);
        tv_progress = findViewById(R.id.tv_progress);
        ImageView imageView7 = findViewById(R.id.imageView7);

        back_list_btn = findViewById(R.id.back_list_btn);
        tv_title_list = findViewById(R.id.tv_title_list);
        ll_list = findViewById(R.id.ll_list);
        tv_select_data = findViewById(R.id.tv_select_data);
        ll_list.setVisibility(View.INVISIBLE);
        import_cc = findViewById(R.id.import_cc);
        delete_contacts = findViewById(R.id.delete_contacts);


        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(imageView7,1080,152,true);

        HelperResizer.setSize(back_list_btn,33,55);
        HelperResizer.setMargin(back_list_btn,55,0,0,0);





        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


//        readContacts();


        Intent intent = getIntent();
        i = intent.getIntExtra("List", 0);
        dir = new File(intent.getStringExtra("path"));

        progress_bar.setVisibility(View.VISIBLE);
        tv_progress.setVisibility(View.VISIBLE);


        Thread thread = new Thread(runnable);
        thread.start();


        back_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();
            }
        });

        delete_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ContactslistActivity.this);
                dialog.setContentView(R.layout.dialog_for_delete);
                dialog.setCancelable(true);

                dialog.show();



                Button yes_delete_btn = dialog.findViewById(R.id.yes_delete_btn);
                Button no_delete_btn = dialog.findViewById(R.id.no_delete_btn);

                yes_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        switch (i){
                            case 0:
                                break;
                            case 1:
                                ContentResolver cr = getContentResolver();
                                for (int i = 0; i < contactDatamodels.size(); i++) {
                                    Uri uri = contactDatamodels.get(i).getUri();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                        cr.delete(uri, null, null);
                                    }
                                }
                                dialog.dismiss();
                                Toast.makeText(ContactslistActivity.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                });

                no_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    dialog.dismiss();

                     switch (i){
                         case 0:
                             break;
                         case 1:
                             for (int j = 0; j < contactDatamodels.size(); j++) {

                                 contactDatamodels.remove(j);
                             }
                             ll_list.setVisibility(View.GONE);
                             longclick = false;

                             break;
                     }

                    }
                });

            }
        });


        switch (i) {
            case 0:
                break;
            case 1:
                tv_title_list.setText("Contacts");
                listOfContactsAdapter = new ListOfContactsAdapter(listOfContarcts, this, new ListOfContactsAdapter.ContactClickListner() {
                    @Override
                    public void ClickEventHandel(int position, ListOfContarcts listOfContarcts) {

                        ll_list.setVisibility(View.VISIBLE);

                        if (contactDatamodels.size() > 0) {
                            for (int j = 0; j < contactDatamodels.size(); j++) {
                                if (contactDatamodels.get(j).getPosition().equals(String.valueOf(position))) {

                                    list = 0;
                                    contactDatamodels.remove(j);

                                    break;
                                } else {
                                    list = 1;
                                }
                            }

                        } else {
                            list = 1;
                        }

                        if (list != 0) {

                            contactDatamodels.add(new ContactDatamodel(String.valueOf(position), listOfContarcts.getUri()));
                        }

                        if (contactDatamodels.size() == 0) {
                            ll_list.setVisibility(View.GONE);
                            longclick = false;
                        }

                        tv_select_data.setText(String.valueOf(contactDatamodels.size()));

                    }
                });
                rv_list.setAdapter(listOfContactsAdapter);

                break;
            case 2:
                tv_title_list.setText("Messages");
                listOfContactsAdapter = new ListOfContactsAdapter(listOfContarcts, this, new ListOfContactsAdapter.ContactClickListner() {
                    @Override
                    public void ClickEventHandel(int position, ListOfContarcts listOfContarcts) {
                        ll_list.setVisibility(View.VISIBLE);

                        if (listselect.size() > 0) {
                            for (int j = 0; j < listselect.size(); j++) {
                                if (listselect.get(j).getPosition().equals(String.valueOf(position))) {

                                    list = 0;
                                    listselect.remove(j);

                                    break;
                                } else {

                                    list = 1;

                                }

                            }

                        } else {

                            list = 1;

                        }
                        if (list != 0) {
                            listselect.add(new ListClickModel(String.valueOf(position), listOfContarcts.getName(), listOfContarcts.getDate(), listOfContarcts.getNumber(), listOfContarcts.getType()));
                        }

                        if (listselect.size() == 0) {
                            ll_list.setVisibility(View.GONE);
                            longclick = false;
                        }

                        tv_select_data.setText(String.valueOf(listselect.size()));


                    }

                });
                rv_list.setAdapter(listOfContactsAdapter);

                break;
            case 3:
                tv_title_list.setText("Call Logs");
                eventAdapter = new EventAdapter(listofCalenders, this, new EventAdapter.ClickEventOfCalender() {
                    @Override
                    public void SelectMessage(int position, ListofCalender listofCalender) {

                        ll_list.setVisibility(View.VISIBLE);

                        if (callLogModels.size() > 0) {


                            for (int j = 0; j < callLogModels.size(); j++) {

                                if (callLogModels.get(j).getPosition().equals(String.valueOf(position))) {

                                    list = 0;
                                    callLogModels.remove(j);

                                    break;
                                } else {

                                    list = 1;

                                }

                            }

                        } else {

                            list = 1;

                        }
                        if (list != 0) {

                            callLogModels.add(new CallLogModel(String.valueOf(position), listofCalender.getPhNumber(), listofCalender.getDir1(), listofCalender.getCallDate(), listofCalender.getCallDuration()));
                        }
                        if (callLogModels.size() == 0) {
                            ll_list.setVisibility(View.GONE);
                            selectLong = false;
                        }
                        tv_select_data.setText(String.valueOf(callLogModels.size()));
                    }
                });
                rv_list.setAdapter(eventAdapter);
                break;
            case 4:

                tv_title_list.setText("Calendar");
                calendarEventAdapter = new CalendarEventAdapter(listofEvents, this, new CalendarEventAdapter.ClickEventForCalender() {
                    @Override
                    public void SelectCalender(int position, ListofEvents listofEvents) {

                        ll_list.setVisibility(View.VISIBLE);

                        if (listOfCalenderEvents.size() > 0) {


                            for (int j = 0; j < listOfCalenderEvents.size(); j++) {

                                if (listOfCalenderEvents.get(j).getPosition().equals(String.valueOf(position))) {

                                    list = 0;
                                    listOfCalenderEvents.remove(j);

                                    break;
                                } else {

                                    list = 1;

                                }

                            }

                        } else {

                            list = 1;

                        }
                        if (list != 0) {

                            listOfCalenderEvents.add(new ListOfCalenderEvent(String.valueOf(position), listofEvents.getCalender_id(), listofEvents.getTitle(), listofEvents.getDescription(), listofEvents.getDatestart(), listofEvents.getDtend(), listofEvents.getLocation(), listofEvents.getAllday()));


                        }

                        if (listOfCalenderEvents.size() == 0) {
                            ll_list.setVisibility(View.GONE);
                            clickcalender = false;
                        }

                        tv_select_data.setText(String.valueOf(listOfCalenderEvents.size()));

                    }
                });
                rv_list.setAdapter(calendarEventAdapter);
                break;

        }

        import_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (i) {
                    case 0:
                        break;
                    case 1:
                        Dialog dialog4 = new Dialog(ContactslistActivity.this);
                        dialog4.setContentView(R.layout.dialog_backup_view);
                        dialog4.setCancelable(true);
                        dialog4.show();

                        Button cancel_btn4 = dialog4.findViewById(R.id.cancel_btn);
                        Button save_btn4 = dialog4.findViewById(R.id.save_btn);
                        et_main = dialog4.findViewById(R.id.et_main);
                        path_backup = dialog4.findViewById(R.id.path_backup);

                        et_main.setText(GenerateName());
                        path_backup.setText(dir.toString());


                        save_btn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog4.dismiss();
                                progressDialog.show();

                                try {
                                    StoreData();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                        cancel_btn4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog4.dismiss();
                            }
                        });


                        break;
                    case 2:

                        Dialog dialog = new Dialog(ContactslistActivity.this);
                        dialog.setContentView(R.layout.dialog_backup_view);
                        dialog.setCancelable(true);
                        dialog.show();

                        Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                        Button save_btn = dialog.findViewById(R.id.save_btn);
                        et_main = dialog.findViewById(R.id.et_main);
                        path_backup = dialog.findViewById(R.id.path_backup);

                        et_main.setText(GenerateName());
                        path_backup.setText(dir.toString());


                        save_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                                progressDialog.show();

                                Gson gson = new Gson();
                                String arrayData = gson.toJson(listselect);
                                generateCSVFileForSMS(arrayData);


                            }
                        });


                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });


                        break;
                    case 3:

                        Dialog dialog1 = new Dialog(ContactslistActivity.this);
                        dialog1.setContentView(R.layout.dialog_backup_view);
                        dialog1.setCancelable(true);
                        dialog1.show();

                        Button cancel_btn1 = dialog1.findViewById(R.id.cancel_btn);
                        Button save_btn1 = dialog1.findViewById(R.id.save_btn);
                        et_main = dialog1.findViewById(R.id.et_main);
                        path_backup = dialog1.findViewById(R.id.path_backup);

                        et_main.setText(GenerateName());
                        path_backup.setText(dir.toString());


                        save_btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog1.dismiss();
                                progressDialog.show();

                                for (int i = 0; i < callLogModels.size(); i++) {

                                    calllogBuffer.add(callLogModels.get(i).getPhNumber() + "," + callLogModels.get(i).getDir1() + "," + callLogModels.get(i).getCallDate() + "," + callLogModels.get(i).getCallDuration());
                                }

                                genratelistofcalllog(calllogBuffer);

                            }
                        });


                        cancel_btn1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                            }
                        });


                        break;
                    case 4:
                        Dialog dialog2 = new Dialog(ContactslistActivity.this);
                        dialog2.setContentView(R.layout.dialog_backup_view);
                        dialog2.setCancelable(true);
                        dialog2.show();

                        Button cancel_btn2 = dialog2.findViewById(R.id.cancel_btn);
                        Button save_btn2 = dialog2.findViewById(R.id.save_btn);
                        et_main = dialog2.findViewById(R.id.et_main);
                        path_backup = dialog2.findViewById(R.id.path_backup);

                        et_main.setText(GenerateName());
                        path_backup.setText(dir.toString());


                        save_btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog2.dismiss();
                                progressDialog.show();

                                for (int i = 0; i < listOfCalenderEvents.size(); i++) {

                                    calendereventbuffer.add(listOfCalenderEvents.get(i).getCalender_id() + "," + listOfCalenderEvents.get(i).getTitle() + "," + listOfCalenderEvents.get(i).getDescription() + "," + listOfCalenderEvents.get(i).getDateSt() + "," + listOfCalenderEvents.get(i).getDateEd() + "," + listOfCalenderEvents.get(i).getLocation() + "," + listOfCalenderEvents.get(i).getAllday());

                                }

                                generatecalllog(calendereventbuffer);

                            }
                        });


                        cancel_btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.dismiss();
                            }
                        });
                        break;

                }

            }
        });

    }

    private void StoreData() throws FileNotFoundException {

        final String vfile = et_main.getText().toString() + ".vcf";

        String path = dir.toString() + File.separator + vfile;

        FileOutputStream mFileOutputStream = new FileOutputStream(path, true);

        for (int i = 0; i < contactDatamodels.size(); i++) {

            Uri uri = contactDatamodels.get(i).getUri();


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

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }


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
        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception " + e);
            //  e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            switch (i) {
                case 0:
                    break;
                case 1:
                    readContacts();
                    break;
                case 2:
                    showMessage();
                    break;
                case 3:
                    loadcalllog = (ContactslistActivity.loadcalllog) new loadcalllog().execute();
                    break;
                case 4:
                    showCalender();
                    break;

            }

        }
    };

    private class loadcalllog extends AsyncTask<String, Integer, Integer> {

        int count = 0;

        protected Integer doInBackground(String... strings) {

            if (strings != null) {
                calllog();
            }

            return count;
        }


        private void calllog() {

            cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION}, null, null, CallLog.Calls.DATE + " DESC");


            while (cur.moveToNext()) {
                String phNumber = cur.getString(0);
                String name = getContactName(phNumber);
                String callType = cur.getString(1);
                String callDate = cur.getString(2);
                String callDuration = cur.getString(3);
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
                count++;
                Log.d("TAG", "calllog: " + count);

                listofCalenders.add(new ListofCalender(name, phNumber, callType, callDate, callDate, callDuration, dir1));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eventAdapter.notifyDataSetChanged();
                    }
                });


            }
        }

        protected void onPostExecute(Integer result) {


            progress_bar.setVisibility(View.GONE);
            tv_progress.setVisibility(View.GONE);
            eventAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("Range")
    private void calllog() {

        Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION}, null, null, null);


        int count = 0;
        while (cur.moveToNext()) {
            String phNumber = cur.getString(0);
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phNumber));
            Cursor cursor = this.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            String name = cursor.getString(0);
//            String name = getContactName(phNumber);
            String callType = cur.getString(1);
            String callDate = cur.getString(2);
            String callDayTime = cur.getString(2);
            String callDuration = cur.getString(3);
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
            count++;
//            sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir1+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//            sb.append("\n----------------------------------");
            listofCalenders.add(new ListofCalender(name, phNumber, callType, callDate, callDayTime, callDuration, dir1));

//         runOnUiThread(new Runnable() {
//             @Override
//             public void run() {
//                 eventAdapter.notifyDataSetChanged();
//             }
//         });
        }

        cur.close();

        Collections.sort(listofCalenders, new sortCompare());

        for (ListofCalender d : listofCalenders) {
            System.out.println(d.getCallDate());

        }
//runnable = new Runnable() {
//    @Override
//    public void run() {
//
//        eventAdapter.notifyDataSetChanged();
//    }
//};


        Thread thread = new Thread(r);
        thread.start();
        Log.d("TAG", "calllog: ");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(listofCalenders, new sortCompare());
                progressDialog.dismiss();
                eventAdapter.notifyDataSetChanged();
            }
        });
//                progressDialog.dismiss();
//                Log.d("TAG", "run: ");
//                Collections.sort(listofCalenders, (item1, item2) -> {
//                    Date date1 = stringToDate(item1.getCallDate());
//                    Date date2 = stringToDate(item2.getCallDate());
//
//                    if (date1 != null && date2 != null) {
//                        boolean b1;
//                        boolean b2;
//
//                        b1 = date1.after(date2);
//                        b2 = date1.before(date2);
//
//                        if (b1 != b2) {
//                            if (b1) {
//                                return -1;
//                            }
//                            if (!b1) {
//                                return 1;
//                            }
//                        }
//                    }
//                    return 0;
//                });
//

//        runOnUiThread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void run() {
//
//
//                eventAdapter.notifyDataSetChanged();
//
//            }
//        });

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("TAG", "run: ");
                    progressDialog.dismiss();
                    eventAdapter.notifyDataSetChanged();

                }
            });
        }
    };

    class sortCompare implements Comparator<ListofCalender> {

        @Override
        public int compare(ListofCalender listofCalender, ListofCalender t1) {
            return t1.getCallDate().compareTo(listofCalender.getCallDate());
        }
    }

    public static Date stringToDate(String strDate) {
        if (strDate == null)
            return null;


        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a", Locale.US);
        Date date = null;
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getContactName(final String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String contactName = "";
        Cursor cursor = this.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
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

//    @SuppressLint("Range")
//
//    private void getSimContacts() {
//
//        Uri simUri = Uri.parse("content://icc/adn");
//        Cursor cursorSim = this.getContentResolver().query(simUri,null,null,null,null);
//
//        while (cursorSim.moveToNext())
//        {
//            ClsSimPhonename =cursorSim.getString(cursorSim.getColumnIndex("name"));
//            ClsSimphoneNo = cursorSim.getString(cursorSim.getColumnIndex("number"));
//
//            Log.d("TAG", "getSimContacts: "+ClsSimPhonename);
//            Log.d("TAG", "getSimContacts: "+ClsSimphoneNo);
//
//        }
//    }
//
//    @SuppressLint("Range")
//
//    public void getPhoneContact(){
//
//    try {
//        String[] PROJECTION=new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
//
//        Cursor c=managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                PROJECTION, null, null, null);
//        int count = 1;
//        if (c.moveToFirst()) {
//            String ClsPhonename = null;
//            String ClsphoneNo = null;
//
//
//            do
//            {
//                ClsPhonename = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                ClsphoneNo = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                Log.d("TAG", "onCreate: "+ClsPhonename);
//                Log.d("TAG", "onCreate: "+ClsPhonename);
//                Log.d("TAG", "onCreate: "+ClsphoneNo);
//                Log.d("TAG", "getPhoneContact: "+getContactsDetails(this,ClsPhonename).toString());
//                 count++;
//
////                    ClsphoneNo.replaceAll("\\D", "");
////                    ClsPhonename=ClsPhonename.replaceAll("&", "");
////                    ClsPhonename.replace("|","");
////                    String ClsPhoneName=ClsPhonename.replace("|","");
//
//
//
//            } while(c.moveToNext());
//
//            Log.d("TAG", "getPhoneContact: "+count);
//        }
//    }catch (Exception e){
//
//    }
//}
//
//    @SuppressLint("Range")
//
//    public   Bitmap getContactsDetails(Context context,String address) {
//        Bitmap bp = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.ic_baseline_filter_1_24);
//
//
//
//
//
//        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
//
//        // querying contact data store
//        Cursor phones = context.getContentResolver().query(contactUri, null, null, null, null);
//
//
//        while (phones.moveToNext()) {
//            String image_uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
//
//            if (image_uri != null) {
//
//                try {
//                    bp = MediaStore.Images.Media
//                            .getBitmap(context.getContentResolver(),
//                                    Uri.parse(image_uri));
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return   bp;
//
//    }
//
//
//    @SuppressLint("Range")
//    private void getContacts(){
//        ContentResolver contentResolver = getContentResolver();
//
////        contactsInfoList = new ArrayList<ContactsInfo>();
//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                @SuppressLint("Range") int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                if (hasPhoneNumber > 0) {
//
//                    ContactsInfo contactsInfo = new ContactsInfo();
//                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                    contactsInfo.setContactId(contactId);
//                    contactsInfo.setDisplayName(displayName);
//
//                    Cursor phoneCursor = getContentResolver().query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{contactId},
//                            null);
//
//                    if (phoneCursor.moveToNext()) {
//                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
////                        Type =  phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.CONTENT_TYPE));
//
////                        contactsInfo.setPhoneNumber(phoneNumber);
//
//                    }
//                    Log.d("TAG", "getContacts: Type "+Type);
//                    Log.d("TAG", "getContacts: Type "+displayName);
//                    Log.d("TAG", "getContacts: Type "+contactId);
//                    Log.d("TAG", "getContacts: Type "+phoneNumber);
//
//
//
//                    phoneCursor.close();
//
////                    contactsInfoList.add(contactsInfo);
//                }
//            }
//        }
//        cursor.close();
//
////        dataAdapter = new MyCustomAdapter(MainActivity.this, R.layout.contact_info, contactsInfoList);
////        listView.setAdapter(dataAdapter);
//    }
//

    @SuppressLint("Range")
    public void readContacts() {

        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = getContentResolver();
        cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, ContactsContract.Contacts.DISPLAY_NAME + " asc");
        String phone = null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";
        Bitmap bitmap = null;
        if (cur.getCount() > 0) {

            while (cur.moveToNext()) {

                String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);


                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);
                    sb.append("\n Contact Name:" + name);
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phone = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append("\n Phone number:" + phone);
                        System.out.println("phone" + phone);
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
                        System.out.println("Email " + emailContact
                                + " Email Type : " + emailType);

                    }

                    emailCur.close();
                }


                if (image_uri != null) {
                    System.out.println(Uri.parse(image_uri));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image_uri));
                        sb.append("\n Image in Bitmap:" + bitmap);
                        System.out.println(bitmap);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }


                listOfContarcts.add(new ListOfContarcts(bitmap, name, phone, uri));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listOfContactsAdapter.notifyDataSetChanged();

                    }
                });
                bitmap = null;
                sb.append("\n........................................");


            }

//            textDetail.setText(sb);


        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);

            }
        });
    }

    public void showMessage() {

        cur = getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, Telephony.Sms.DATE + " asc");

        if (cur != null) {

            if (cur.moveToFirst()) {
                for (int j = 0; j < cur.getCount(); j++) {
                    String smsDate = cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    String type = cur.getString(cur.getColumnIndexOrThrow(Telephony.Sms.TYPE));
                    String dateFormat = new Date(Long.valueOf(smsDate)).toString();


                    listOfContarcts.add(new ListOfContarcts(number, dateFormat, body, type));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listOfContactsAdapter.notifyDataSetChanged();

                        }
                    });

                    cur.moveToNext();
                }
            }
            cur.close();
        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_progress.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);
            }
        });

    }

    public void showCalender() {
        String[] projection = new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Events.DURATION, CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.EVENT_LOCATION};
        Cursor cursor = this.getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, null, null, CalendarContract.Events.DTSTART + " asc");
        try {
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    String title = cursor.getString(0);

                    String description = cursor.getString(1);

                    String dtstart = cursor.getString(2);
                    Date callDayTime = new Date(Long.parseLong(dtstart));
                    String dtend = cursor.getString(3);
                    String allday = cursor.getString(4);
                    String Event_timezone = cursor.getString(5);
                    String duration = cursor.getString(6);
                    String calender_id = cursor.getString(7);
                    String location = cursor.getString(8);
//                    Date duration1 = new Date(Long.parseLong(duration));


                    listofEvents.add(new ListofEvents(calender_id, title, description, dtstart, callDayTime, dtend, allday, location));


                }
                cursor.close();
                calendarEventAdapter.notifyDataSetChanged();

                progress_bar.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
            }
        } catch (AssertionError ex) {
            /*TODO: log exception and bail*/
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        switch (i) {
            case 0:
            case 2:
            case 1:
                break;
            case 3:
                cur.close();
                loadcalllog.cancel(true);

                break;


        }

    }


    public String GenerateName() {

        Random random = new Random();
        int id = random.nextInt(100);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMdd");
        Date myDate = new Date();
        String date = timeStampFormat.format(myDate);
        return tv_title_list.getText().toString().replaceAll("\\s+", "") + date + id + id;
    }

    private void generateCSVFileForSMS(String list) {

        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            String storage_path = dir + File.separator + et_main.getText().toString() + ".xml";
            FileWriter write = new FileWriter(storage_path);


            write.append(list);


            write.flush();
            write.close();
        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception " + e);

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }

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
        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception " + e);
            //  e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

    }
}