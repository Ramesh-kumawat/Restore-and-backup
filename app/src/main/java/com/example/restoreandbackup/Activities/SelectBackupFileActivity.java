package com.example.restoreandbackup.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Adapters.VcfFileAdapter;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.Models.RestoreContractsModel;
import com.example.restoreandbackup.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class SelectBackupFileActivity extends AppCompatActivity {

    ImageView back_select_btn;
    TextView path_restore;
    ArrayList<RestoreContractsModel> list = new ArrayList<>();
    RecyclerView rv_backup_file;
    String path;
    private int Contacts;
    VcfFileAdapter vcfFileAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_backup_file);

        back_select_btn = findViewById(R.id.back_select_btn);
       ImageView imageView8 = findViewById(R.id.imageView8);
        path_restore = findViewById(R.id.path_restore);
        rv_backup_file = findViewById(R.id.rv_backup_file);

        HelperResizer.getheightandwidth(this);

        HelperResizer.setSize(imageView8,1080,152);

        HelperResizer.setSize(back_select_btn,33,52);
        HelperResizer.setMargin(back_select_btn,55,0,0,0);



        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        Contacts = intent.getIntExtra("Contacts",0);
        path_restore.setText(path);



        clickevent();
        loadlist();







         vcfFileAdapter = new VcfFileAdapter(list, SelectBackupFileActivity.this, new VcfFileAdapter.OnclickvcfFileListnear() {

            @Override
            public void ClickvcfFile(RestoreContractsModel restoreContractsModel, int position) {

                switch (Contacts){
                    case 0:
                        restoreContractsModel.getPath();
                        openBackup(  restoreContractsModel.getPath()+"/"+restoreContractsModel.getName());
                        Toast.makeText(SelectBackupFileActivity.this, "hello ", Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        restoreContractsModel.getPath();

                        Toast.makeText(SelectBackupFileActivity.this, "hello hi", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(restoreContractsModel.getPath()+"/"+restoreContractsModel.getName());
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                        break;
                    case 3:

                        File file = new File(restoreContractsModel.getPath()+"/"+restoreContractsModel.getName());
                        Log.d("TAG", "ClickvcfFile: "+file);

                        file.delete();

                        if(file.exists()){
                            try {
                                file.getCanonicalFile().delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(file.exists()){

                                deleteFile(restoreContractsModel.getName());
                            }
                        }

                        list.remove(position);
                        vcfFileAdapter.notifyDataSetChanged();


                        break;

                }

            }
        });
        rv_backup_file.setAdapter(vcfFileAdapter);






    }

    public void loadlist(){

        File f = new File(path);
        File file[] = f.listFiles();

        if(file != null) {
            assert file != null;
            Log.d("Files", "Size: " + file.length);
            for (File value : file) {
                Log.d("Files", "FileName:" + value.getName());
                Log.d("Files", "FileName:" + value.lastModified());
                Date lastModifiedDate = new Date(value.lastModified());
                Log.d("Files", "FileName:" + lastModifiedDate);
                list.add(new RestoreContractsModel(value.getName(), lastModifiedDate.toString(), path));
            }
        }else{
            Toast.makeText(this , "Don't access your directory ", Toast.LENGTH_SHORT).show();
        }
    }

    private void openBackup(String savedVCard)
    {
        try
        {
            String vcfMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("vcf");
            Intent openVcfIntent = new Intent(Intent.ACTION_VIEW);
            openVcfIntent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+File.separator+savedVCard)), vcfMimeType);

//            try
//            {
//                if (getPackageManager() != null)
//                {
//                    List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(openVcfIntent, 0);
//                    if (resolveInfos != null)
//                    {
//                        for (ResolveInfo resolveInfo : resolveInfos)
//                        {
//                            ActivityInfo activityInfo = resolveInfo.activityInfo;
//                            if (activityInfo != null)
//                            {
//                                String packageName = activityInfo.packageName;
//                                String name = activityInfo.name;
//                                // Find the needed Activity based on Android source files: http://grepcode.com/search?query=ImportVCardActivity&start=0&entity=type&n=
//                                if (packageName != null && packageName.equals("com.android.contacts") && name != null && name.contains("ImportVCardActivity"))
//                                {
//                                    openVcfIntent.setPackage(packageName);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            catch (Exception ignored)
//            {
//            }

            startActivity(openVcfIntent);
        }
        catch (Exception exception)
        {
            // No app for openning .vcf files installed (unlikely)
        }
    }


    private void clickevent() {

        back_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }
//    public void resContact(String filename,String path) {
//        try {
//            File f = new File(path + "/" + filename);
//            FileInputStream istrm = new FileInputStream(f);
//            if ((int) f.length() <= 0) {
//                Toast.makeText(this, "file empty/does not exit",
//                        Toast.LENGTH_LONG).show();
//                return;
//            }
//            parseContacts(istrm, filename);
//        } catch (XmlPullParserException e) {
//            Toast.makeText(this, "XmlPullParserException!", Toast.LENGTH_LONG)
//                    .show();
//            this.finish();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(this, "Error:Could not find backup file!",
//                    Toast.LENGTH_LONG).show();
//            this.finish();
//        } catch (IOException e) {
//            Toast.makeText(this, "Error:Could not read from backup file!",
//                    Toast.LENGTH_LONG).show();
//            this.finish();
//        }
//        this.finish();
//    }
//    private void parseContacts(FileInputStream strm, String filename)
//            throws XmlPullParserException, IOException {
//        XmlPullParser parser = Xml.newPullParser();
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//        ContentProviderOperation.Builder op;
//        String name, str1 = "", str2 = "";
//        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//        parser.setInput(strm, null);
//        int event = parser.getEventType();
//        while (event != XmlPullParser.END_DOCUMENT) {
//            // name = "";
//            // str1 = "";
//            // str2 = "";
//            op = null;
//            switch (event) {
//                case XmlPullParser.START_TAG:
//                    name = parser.getName();
//                    if (name.equals("contactfile"))
//                        break;
//                    if (name.equals("contact"))
//                        break;
//                    if (name.equals("accname")) {
//                        str1 = parser.nextText();
//                        if (str1.equals("null")) {
//                            Log.i("", "MYTAG here");
//                            str1 = null;
//                        }
//                        break;
//                    }
//                    if (name.equals("acctype")) {
//                        str2 = parser.nextText();
//                        if (str2.equals("null"))
//                            str2 = null;
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                                .withValue(
//                                        ContactsContract.RawContacts.ACCOUNT_NAME,
//                                        str1)
//                                .withValue(
//                                        ContactsContract.RawContacts.ACCOUNT_TYPE,
//                                        str2);
//                        Log.i("", "MYTAG str1=" + str1 + " str2=" + str2);
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("name")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("phone")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("note")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("email")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("org")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//                    if (name.equals("address")) {
//                        op = ContentProviderOperation
//                                .newInsert(ContactsContract.Data.CONTENT_URI)
//                                .withValueBackReference(
//                                        ContactsContract.Data.RAW_CONTACT_ID, 0)
//                                .withValue(
//                                        ContactsContract.Data.MIMETYPE,
//                                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
//                                .withValue(ContactsContract.Data.DATA1,
//                                        parser.nextText());
//                        ops.add(op.build());
//                        break;
//                    }
//
//                case XmlPullParser.END_TAG:
//                    name = parser.getName();
//                    if (name.equals("contact")) {
//                        try {
//                            Log.i("", "MYTAG ops=" + ops.toString());
//                            this.getContentResolver().applyBatch(
//                                    ContactsContract.AUTHORITY, ops);
//
//                            ops.clear();
//                        } catch (RemoteException e) {
//                            Toast.makeText(this, "RemoteException",
//                                    Toast.LENGTH_SHORT).show();
//                            return;// this.finish();
//                        } catch (OperationApplicationException e) {
//                            Toast.makeText(this, "OperationApplicationException",
//                                    Toast.LENGTH_SHORT).show();
//                            return;// this.finish();
//                        }
//                        break;
//                    }
//            }
//            event = parser.next();
//        }
//
//        log.close();
//        Toast.makeText(this, "Contacts restored successfully!",
//                Toast.LENGTH_LONG).show();
//    }
}