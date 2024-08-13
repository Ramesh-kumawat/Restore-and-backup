package com.example.restoreandbackup.Fragments;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Activities.ApplicationActivity;
import com.example.restoreandbackup.Adapters.AllAppAdapter;
import com.example.restoreandbackup.Models.AppDataModel;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AplicationFragment extends Fragment {


    private static final int UNINSTALL_REQUEST_CODE = 1;

    RecyclerView rv_application;
    long installed;
    List<AppDataModel> list = new ArrayList<>();
    String size = "455";
    AllAppAdapter allAppAdapter;
    ProgressDialog progressDialog;

    int order, app_order;
    Boolean order_detail;
    private int type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_aplication, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_application = view.findViewById(R.id.rv_application);

//        getallapps();

        ((ApplicationActivity) requireActivity()).setFragmentRefreshListener(new ApplicationActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
//                progressDialog.show();


                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("RkPreference", Context.MODE_PRIVATE);
                order = sharedPreferences.getInt("order", 0);
                order_detail = sharedPreferences.getBoolean("order_detail", false);
                app_order = sharedPreferences.getInt("app_order", 0);

                list.clear();
                GetAllInstalledApkInfo();
//                if(order_detail){
//
//
//                }else{
//                    Collections.sort(list, Collections.reverseOrder());
//
//                }
                if (order == 0) {
                    if (order_detail) {
                        Collections.sort(list, new Comparator<AppDataModel>() {
                            @Override
                            public int compare(AppDataModel a, AppDataModel b) {
                                return a.getName().compareTo(b.getName());
                            }
                        });

                    } else {
                        Collections.reverse(list);
                    }
                } else if (order == 1) {

                    Collections.sort(list, new Comparator<AppDataModel>() {
                        @Override
                        public int compare(AppDataModel a, AppDataModel b) {

                            Date date1 = a.getDate1();
                            Date date2 = b.getDate1();

                            if (date1 != null && date2 != null) {
                                boolean b1;
                                boolean b2;
                                if (order_detail) {
                                    b1 = date2.after(date1);
                                    b2 = date2.before(date1);
                                } else {
                                    b1 = date1.after(date2);
                                    b2 = date1.before(date2);
                                }
                                if (b1 != b2) {
                                    if (b1) {
                                        return -1;
                                    }
                                    if (!b1) {
                                        return 1;
                                    }
                                }
                            }
                            return 0;
                        }

                    });


                } else if (order == 2) {
                    if (order_detail) {
                        Collections.sort(list, new Comparator<AppDataModel>() {
                            @Override
                            public int compare(AppDataModel a, AppDataModel b) {

                                Float a1 = 0.0f;
                                Float b1 = 0.0f;

                                if (a.getSize().contains("k")) {
                                    a1 = Float.parseFloat((a.getSize().replaceAll("kB", "").trim()));
                                    a1 = a1 / 1000;
                                    Log.d("TAG", "onRefresh: " + a1);
                                }
                                if (b.getSize().contains("k")) {
                                    b1 = Float.parseFloat((b.getSize().replaceAll("kB", "").trim()));
                                    b1 = b1 / 1000;
                                    Log.d("TAG", "onRefresh: " + b1);
                                }
                                if (a.getSize().contains("M")) {
                                    a1 = Float.parseFloat((a.getSize().replaceAll("MB", "").trim()));
                                    Log.d("TAG", "onRefresh: " + a1);
                                }
                                if (b.getSize().contains("M")) {
                                    b1 = Float.parseFloat(b.getSize().replaceAll("MB", "").trim());

                                    Log.d("TAG", "onRefresh: " + b1);
                                }
                                Log.d("TAG", "onRefresh: " + a1);
                                Log.d("TAG", "onRefresh: " + b1);


                                return Float.compare(a1, b1);

                            }
                        });

                    } else {


                    }
                }

                allAppAdapter.notifyDataSetChanged();

            }

        });

        Thread thread = new Thread(runnable);
        thread.start();

//        Collections.sort(list, new Comparator<>()
//        {
//            @Override
//            public int compare(XYZBean lhs, XYZBean rhs) {
//
//                return Integer.valueOf(lhs.getDistance()).compareTo(rhs.getDistance());
//            }
//        });


        allAppAdapter = new AllAppAdapter(list, getContext(), new AllAppAdapter.OnclickAppListnear() {
            @Override
            public void clickapplistener(AppDataModel appDataModel) {

                View bottomSheet = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(),R.style.BottomSheetDialog);
                bottomSheetDialog.setContentView(bottomSheet);
                bottomSheetDialog.show();
                bottomSheetDialog.setCancelable(true);


                TextView bts_name = bottomSheet.findViewById(R.id.bts_name);
                TextView bts_version = bottomSheet.findViewById(R.id.bts_version);
                TextView bts_package = bottomSheet.findViewById(R.id.bts_package);
                TextView bts_default = bottomSheet.findViewById(R.id.bts_default);
                TextView bts_date = bottomSheet.findViewById(R.id.bts_date);
                ConstraintLayout cl_bottomsheet = bottomSheet.findViewById(R.id.cl_bottomsheet);

                ImageView bts_backup_btn = bottomSheet.findViewById(R.id.bts_backup_btn);
                ImageView bts_btn_launch = bottomSheet.findViewById(R.id.bts_btn_launch);
                ImageView btn_bts_uninstall = bottomSheet.findViewById(R.id.btn_bts_uninstall);
                ImageView bts_btn_play_store = bottomSheet.findViewById(R.id.bts_btn_play_store);
                ImageView bts_btn_app_info = bottomSheet.findViewById(R.id.bts_btn_app_info);
                ImageView bts_img = bottomSheet.findViewById(R.id.bts_img);

                HelperResizer.setSize(bts_img,200,200);
                HelperResizer.setMargin(bts_img,50,0,0,0);

                HelperResizer.setSize(cl_bottomsheet,1080,509,true);

                HelperResizer.setSize(bts_backup_btn,242,92);

                HelperResizer.setSize(bts_btn_launch,150,150);
                HelperResizer.setMargin(bts_btn_launch,80,0,0,40);

                HelperResizer.setSize(bts_btn_play_store,150,150);
                HelperResizer.setMargin(bts_btn_play_store,80,0,0,0);

                HelperResizer.setSize(bts_btn_app_info,150,150);
                HelperResizer.setMargin(bts_btn_app_info,80,0,0,0);

                HelperResizer.setSize(btn_bts_uninstall,150,150);
                HelperResizer.setMargin(btn_bts_uninstall,80,0,0,0);






                bts_name.setText(appDataModel.getName());
                bts_version.setText(appDataModel.getVersion());
                bts_package.setText(appDataModel.getPackegeName());

                if (appDataModel.getType() == 1) {
                    bts_default.setText("Users");
                    btn_bts_uninstall.setVisibility(View.VISIBLE);

                } else {
                    bts_default.setText("System");
                    btn_bts_uninstall.setVisibility(View.INVISIBLE);

                }

                Date d = appDataModel.getDate1();
                String dayOfTheWeek = (String) DateFormat.format("EEE", d);
                String monthNumber = (String) DateFormat.format("MMM", d);
                String year = (String) DateFormat.format("yy", d);

                bts_date.setText(dayOfTheWeek + " " + monthNumber + ", " + year);
                bts_img.setImageDrawable(appDataModel.getIcon());


                bts_btn_play_store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + appDataModel.getPackegeName())));
                        Log.d("TAG", "onClick: " + appDataModel.getPackegeName());
                    }
                });

                bts_btn_launch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launchIntent = getContext().getPackageManager().getLaunchIntentForPackage(appDataModel.getPackegeName());
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        }
                    }
                });
                btn_bts_uninstall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
//                        intent.setData(Uri.parse(appDataModel.getPackegeName()));
//                        startActivity(intent);
                        String app_pkg_name = appDataModel.getPackegeName();
                        intent.setData(Uri.parse("package:" + app_pkg_name));
                        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                        startActivityForResult(intent, UNINSTALL_REQUEST_CODE);


                    }
                });

                bts_btn_app_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        i.addCategory(Intent.CATEGORY_DEFAULT);
                        i.setData(Uri.parse("package:" + appDataModel.getPackegeName()));
                        startActivity(i);
                    }
                });

                bts_backup_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.show();
                                        bottomSheetDialog.dismiss();

                                    }
                                });

                                try {
                                    File f2 = new File(Environment.getExternalStorageDirectory() + "/Download/All Restore Data/Application");
                                    f2.mkdirs();
                                    f2 = new File(f2.getPath() + "/" + appDataModel.getName().trim() + ".apk");

                                    f2.createNewFile();


                                    InputStream in = new FileInputStream(appDataModel.getFile());

                                    OutputStream out = new FileOutputStream(f2);

                                    byte[] buf = new byte[1024];
                                    int len;
                                    while ((len = in.read(buf)) > 0) {
                                        out.write(buf, 0, len);
                                    }
                                    in.close();
                                    out.close();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    });


                                } catch (
                                        FileNotFoundException ex) {
                                    System.out.println(ex.getMessage() + " in the specified directory.");
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                        };
                        thread.start();

                    }
                });

            }
        });
        rv_application.setAdapter(allAppAdapter);

    }

    Runnable runnable = new Runnable() {
        public void run() {


            GetAllInstalledApkInfo();


        }
    };


//    public static Date stringToDate(String strDate) {
//        if (strDate == null)
//            return null;
//
//        // change the date format whatever you have used in your model class.
//        Date date = null;
//        System.out.println(date);
//
//        return date;
//    }

    public void GetAllInstalledApkInfo() {


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressDialog = new ProgressDialog(getContext());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
        });
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        List<ResolveInfo> resolveInfoList = getContext().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            getAppIconByPackageName(activityInfo.applicationInfo.packageName);
            GetAppName(activityInfo.applicationInfo.packageName);
            GetVersion(activityInfo.applicationInfo.packageName);
            GetDate(activityInfo.applicationInfo.packageName);
            PackageManager pm = getContext().getPackageManager();
            File file = new File(resolveInfo.activityInfo.applicationInfo.publicSourceDir);

//
            try {
                size = formateFileSize(new File(getContext().getPackageManager().getApplicationInfo(activityInfo.applicationInfo.packageName, 0).publicSourceDir).length());
            } catch (PackageManager.NameNotFoundException e) {
                size = "0";
            }

            if (isSystemPackage(resolveInfo)) {
                type = 2;
                Log.d("TAG", "GetAllInstalledApkInfo: 1" + GetAppName(activityInfo.applicationInfo.packageName));
            } else {
                type = 1;
                Log.d("TAG", "GetAllInstalledApkInfo: 2" + GetAppName(activityInfo.applicationInfo.packageName));
            }


            if (app_order == 1) {
                if (type == 1) {
                    list.add(new AppDataModel(getAppIconByPackageName(activityInfo.applicationInfo.packageName), GetAppName(activityInfo.applicationInfo.packageName), activityInfo.applicationInfo.packageName, GetDate(activityInfo.applicationInfo.packageName), GetVersion(activityInfo.applicationInfo.packageName), size, type, file));
                }
            } else if (app_order == 2) {
                if (type == 2) {

                    list.add(new AppDataModel(getAppIconByPackageName(activityInfo.applicationInfo.packageName), GetAppName(activityInfo.applicationInfo.packageName), activityInfo.applicationInfo.packageName, GetDate(activityInfo.applicationInfo.packageName), GetVersion(activityInfo.applicationInfo.packageName), size, type, file));
                }
            } else {

                list.add(new AppDataModel(getAppIconByPackageName(activityInfo.applicationInfo.packageName), GetAppName(activityInfo.applicationInfo.packageName), activityInfo.applicationInfo.packageName, GetDate(activityInfo.applicationInfo.packageName), GetVersion(activityInfo.applicationInfo.packageName), size, type, file));

            }


        }


        progressDialog.dismiss();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();

                Collections.sort(list, new Comparator<AppDataModel>() {
                    @Override
                    public int compare(AppDataModel a, AppDataModel b) {
                        return a.getName().compareTo(b.getName());
                    }

                });
                allAppAdapter.notifyDataSetChanged();
            }
        });
    }


    private String formateFileSize(long size) {
        return Formatter.formatFileSize(getContext(), size);
    }

    private Date GetDate(String packageName) {


        try {
            installed = new File(getContext().getPackageManager().getPackageInfo(packageName, 0).applicationInfo.sourceDir).lastModified();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Date date = new Date(installed);
        date.toString();

//            SimpleDateFormat format1 = new SimpleDateFormat("dd MMM,yyyy");
        return date;


    }

    private String GetVersion(String packageName) {

        try {
            return getContext().getPackageManager().getPackageInfo(packageName, 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return packageName;
    }

    public boolean isSystemPackage(ResolveInfo resolveInfo) {

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName) {

        Drawable drawable;

        try {
            drawable = getContext().getPackageManager().getApplicationIcon(ApkTempPackageName);

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher);
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName) {

        String Name = "";

        ApplicationInfo applicationInfo;
        PackageManager packageManager = getContext().getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if (applicationInfo != null) {

                Name = (String) packageManager.getApplicationLabel(applicationInfo);
            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNINSTALL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getContext(), "Uninstall Successfully", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onActivityResult: user accepted the (un)install");

            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getContext(), "Uninstall Successfully", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onActivityResult: user canceled the (un)install");
            } else if (resultCode == RESULT_FIRST_USER) {
                Toast.makeText(getContext(), "Uninstall Failed ", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onActivityResult: failed to (un)install");
            }
        }
    }
}
