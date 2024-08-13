package com.example.restoreandbackup.Fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Adapters.ArchiveListAdapter;
import com.example.restoreandbackup.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class ArchieveFragment extends Fragment {

   RecyclerView rv_apk_file;
    ArchiveListAdapter listOfDataAdapter;
    ArrayList<ListOfApp> list1 = new ArrayList<>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_archieve, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_apk_file = view.findViewById(R.id.rv_apk_file);


        loadlist();

        listOfDataAdapter = new ArchiveListAdapter(getContext(), list1, new ArchiveListAdapter.OnClickOpenListener() {
            @Override
            public void DataClick(ListOfApp list,int position) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                bottomSheetDialog.setContentView(R.layout.archievelistview);
                bottomSheetDialog.show();
                bottomSheetDialog.setCancelable(true);

                ImageView icon = bottomSheetDialog.findViewById(R.id.img_icon1);
                TextView name = bottomSheetDialog.findViewById(R.id.tv_name1);
                TextView version =  bottomSheetDialog.findViewById(R.id.app_version1);
                TextView data = bottomSheetDialog.findViewById(R.id.app_data1);
                TextView date = bottomSheetDialog.findViewById(R.id.app_date1);
                Button upload_bottomsheet_btn = bottomSheetDialog.findViewById(R.id.upload_bottomsheet_btn);
                Button share_bottomsheet_btn = bottomSheetDialog.findViewById(R.id.share_bottomsheet_btn);
                Button delete_bottomsheet_btn = bottomSheetDialog.findViewById(R.id.delete_bottomsheet_btn);

                icon.setImageDrawable(list.getImage());
                name.setText(list.getName());
                version.setText(list.getVersion());
                data.setText(list.getSize());


                String dayOfTheWeek = (String) DateFormat.format("EEE", list.getDate());
                String monthNumber = (String) DateFormat.format("MMM", list.getDate());
                String year = (String) DateFormat.format("yy", list.getDate());

                date.setText(dayOfTheWeek+ " "+monthNumber+","+year);

                upload_bottomsheet_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d("TAG", "onClick: "+list.getPath()+"/"+list.getName()+".apk");
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(list.getPath()+"/"+list.getName()+".apk");
                        sharingIntent.setType("*/*");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));


                    }
                });

                share_bottomsheet_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//
//                        Intent intent = new Intent(Intent.ACTION_SEND);
//                        intent.setType("*/*");
//                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(list.getPath()+list.getName()+".apk")));
//                        startActivity(Intent.createChooser(intent, "Share app via"));

                        Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider",new File(list.getPath()+"/"+list.getName()+".apk"));
                        File file = new File(uri.getPath());
                        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                        intent.setDataAndType(uri, "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "Share app via"));
                    }
                });

                delete_bottomsheet_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        File file = new File(list.getPath()+"/"+list.getName()+".apk");
                        file.delete();

                        if(file.exists()){
                            try {
                                file.getCanonicalFile().delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(file.exists()){

                                getContext().deleteFile(list.getName());
                            }
                        }

                        bottomSheetDialog.dismiss();
                        list1.remove(position);
                        listOfDataAdapter.notifyDataSetChanged();

                    }


                });


            }

        });
        rv_apk_file.setAdapter(listOfDataAdapter);
//
//        String vcfMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("apk");
//        Intent openVcfIntent = new Intent(Intent.ACTION_VIEW);
//        openVcfIntent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+File.separator+savedVCard)), vcfMimeType);

    }

    @Override
    public void onResume() {
        super.onResume();
        list1.clear();
        loadlist();

        listOfDataAdapter.notifyDataSetChanged();

    }

    public class ListOfApp{

        String path;
        Drawable image;
        String Name;
        String Version;
        String Size;
        Date date1;

        public ListOfApp(String path, Drawable image, String name, String version, String size, Date date) {
            this.path = path;
            this.image = image;
            Name = name;
            Version = version;
            Size = size;
            date1 = date;
        }

        public String getVersion() {
            return Version;
        }

        public void setVersion(String version) {
            Version = version;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String size) {
            Size = size;
        }

        public Date getDate() {
            return date1;
        }

        public void setDate(Date date) {
            date1 = date;
        }

        public Drawable getImage() {
            return image;
        }

        public void setImage(Drawable image) {
            this.image = image;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public ListOfApp(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public void loadlist(){

        File f = new File(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Application");
        File file[] = f.listFiles();
        if(!f.exists()){
            f.mkdirs();
        }


        if(file != null) {

            Log.d("Files", "Size: " + file.length);
            for (File value : file) {
                String versionName = "null"; //String apk versionName
                String APKFilePath = f.getPath()+"/"+value.getName(); //For example
                PackageManager pm = getContext().getPackageManager();
                PackageInfo    packageInfo = pm.getPackageArchiveInfo(APKFilePath, 0);

                packageInfo.applicationInfo.sourceDir = APKFilePath;
                packageInfo.applicationInfo.publicSourceDir = APKFilePath;

//                 APKicon =  getAppIconByPackageName(packageInfo.applicationInfo.packageName);

                Drawable APKicon = packageInfo.applicationInfo.loadIcon(pm);
                String   AppName = (String)packageInfo.applicationInfo.loadLabel(pm);
                versionName = packageInfo.versionName; //String version name

                String size;
                try {
                    size = formateFileSize( new File(getContext().getPackageManager().getApplicationInfo(packageInfo.applicationInfo.packageName, 0).publicSourceDir).length());
                } catch (PackageManager.NameNotFoundException e) {
                    size = "0";
                }


                Log.d("Files", "FileName:" + value.getName());
                Log.d("Files", "FileName:" + value.lastModified());
                Date lastModifiedDate = new Date(value.lastModified());
                Log.d("Files", "FileName:" + lastModifiedDate);
                list1.add(new ListOfApp(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Application",APKicon,AppName,versionName,size ,lastModifiedDate));
            }
        }else{

        }
    }
    private String formateFileSize(long size) {
        return Formatter.formatFileSize(getContext(), size);
    }


}