package com.example.restoreandbackup.Activities;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.restoreandbackup.Fragments.AplicationFragment;
import com.example.restoreandbackup.Fragments.ArchieveFragment;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class ApplicationActivity extends AppCompatActivity {

    ImageView first,secord;
    ImageView imageView2;
    ViewPagerAdapter pagerAdapter;
    ViewPager view_pager;
    ImageView name_asc_des,size_order,date_order,up_order,bottom_order,back_btn_application,imageView;
    ImageView us_sy_both,user_order,system_order;
    int order;
    boolean order_detail;
    int app_order;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

      first = findViewById(R.id.first);
      secord = findViewById(R.id.secord);
      view_pager = findViewById(R.id.view_pager);
      imageView2 = findViewById(R.id.imageView2);
        imageView = findViewById(R.id.imageView);

        back_btn_application = findViewById(R.id.back_btn_application);

       sharedPreferences = getSharedPreferences("RkPreference",MODE_PRIVATE);
       editor = sharedPreferences.edit();


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ApplicationActivity.this,R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.filter_bottom_sheet);
        bottomSheetDialog.setCancelable(true);


        name_asc_des =  bottomSheetDialog.findViewById(R.id.name_asc_des);
        date_order =  bottomSheetDialog.findViewById(R.id.date_order);
        size_order =  bottomSheetDialog.findViewById(R.id.size_order);
        up_order =  bottomSheetDialog.findViewById(R.id.up_order);
        bottom_order =  bottomSheetDialog.findViewById(R.id.bottom_order);
        us_sy_both =  bottomSheetDialog.findViewById(R.id.us_sy_both);
        user_order =  bottomSheetDialog.findViewById(R.id.user_order);
        system_order =  bottomSheetDialog.findViewById(R.id.system_order);
        Button button = bottomSheetDialog.findViewById(R.id.button);

        ConstraintLayout cl_filter_box = bottomSheetDialog.findViewById(R.id.cl_filter_box);


        HelperResizer.setSize(name_asc_des,120,178);
        HelperResizer.setMargin(name_asc_des,0,30,0,0);

        HelperResizer.setSize(date_order,120,178);
        HelperResizer.setMargin(date_order,55,0,0,0);

        HelperResizer.setSize(size_order,120,178);
        HelperResizer.setMargin(size_order,55,0,0,0);

        HelperResizer.setSize(bottom_order,165,101);

        HelperResizer.setSize(up_order,165,101);
        HelperResizer.setMargin(up_order,0,100,0,0);


        HelperResizer.setSize(us_sy_both,191,178);
        HelperResizer.setMargin(us_sy_both,0,35,0,0);

        HelperResizer.setSize(user_order,120,178);
        HelperResizer.setMargin(user_order,55,0,0,0);

        HelperResizer.setSize(system_order,120,178);
        HelperResizer.setMargin(system_order,55,0,0,0);

        HelperResizer.setSize(cl_filter_box,1080,1082,true);







      pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
      view_pager.setAdapter(pagerAdapter);


      view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              switch (position){
                  case 0:
                  default:
                     first.setSelected(true);
                     secord.setSelected(false);
                     break;
                  case 1:
                      first.setSelected(false);
                      secord.setSelected(true);
                      break;

              }
          }

          @Override
          public void onPageSelected(int position) {

          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
      imageView2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              bottomSheetDialog.show();

              name_asc_des.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      date_order.setSelected(false);
                      size_order.setSelected(false);
                      name_asc_des.setSelected(true);

                      order = 0;

                  }
              });
              date_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      date_order.setSelected(true);
                      size_order.setSelected(false);
                      name_asc_des.setSelected(false);


                      order = 1;
                  }
              });

              size_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      date_order.setSelected(false);
                      size_order.setSelected(true);
                      name_asc_des.setSelected(false);

                      order = 2;
                  }
              });

              up_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      up_order.setSelected(true);
                      bottom_order.setSelected(false);

                      order_detail = false;

                  }
              });

              bottom_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      up_order.setSelected(false);
                      bottom_order.setSelected(true);
                      order_detail = true;
                  }
              });

              us_sy_both.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      us_sy_both.setSelected(true);
                      system_order.setSelected(false);
                      user_order.setSelected(false);

                      app_order = 0;

                  }
              });

              user_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      us_sy_both.setSelected(false);
                      system_order.setSelected(false);
                      user_order.setSelected(true);

                      app_order = 1;

                  }
              });

              system_order.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      us_sy_both.setSelected(false);
                      system_order.setSelected(true);
                      user_order.setSelected(false);

                      app_order = 2;
                  }
              });


              button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {

                      editor.putInt("order",order);
                      editor.putBoolean("order_detail",order_detail);
                      editor.putInt("app_order",app_order);
                      editor.apply();

                      bottomSheetDialog.dismiss();
                      getFragmentRefreshListener().onRefresh();

                  }
              });



          }
      });


        size();
        clickevent();

    }

    private void size() {

        HelperResizer.getheightandwidth(this);
        HelperResizer.setSize(imageView,1080,152,true);

        HelperResizer.setSize(back_btn_application,33,52);
        HelperResizer.setMargin(back_btn_application,55,0,0,0);

        HelperResizer.setSize(imageView2,86,86);
        HelperResizer.setMargin(imageView2,0,0,45,0);

        HelperResizer.setSize(first,490,115);
        HelperResizer.setMargin(first,0,50,0,0);

        HelperResizer.setSize(secord,490,115);
        HelperResizer.setMargin(secord,0,50,0,0);

    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }

    private void clickevent() {



      first.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              view_pager.setCurrentItem(0);


          }
      });

      secord.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              view_pager.setCurrentItem(1);

          }
      });

        back_btn_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
    public void getallapps(View view) {
        List<ApplicationInfo> infos = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        String[] apps = new String[infos.size()];
        int i = 0;
        for (ApplicationInfo info : infos) {
            apps[i] = info.packageName;
            i++;
        }

}
    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private int NUM_ITEMS = 2;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                default:
                    return new AplicationFragment();
                case 1:
                    return new ArchieveFragment();



            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        first.setSelected(true);


      int i =   sharedPreferences.getInt("order",0);
       boolean detail =  sharedPreferences.getBoolean("order_detail",true);
        int order = sharedPreferences.getInt("app_order",0);

        if(i == 0){
            date_order.setSelected(false);
            size_order.setSelected(false);
            name_asc_des.setSelected(true);


        }else if(i == 1){
            date_order.setSelected(true);
            size_order.setSelected(false);
            name_asc_des.setSelected(false);


        }else{
            date_order.setSelected(false);
            size_order.setSelected(true);
            name_asc_des.setSelected(false);

        }

        if(detail){
            up_order.setSelected(true);
            bottom_order.setSelected(false);

        }else {
            up_order.setSelected(false);
            bottom_order.setSelected(true);
        }

        if (order == 0){

            us_sy_both.setSelected(true);
            system_order.setSelected(false);
            user_order.setSelected(false);


        }else if(order == 1){

            us_sy_both.setSelected(false);
            system_order.setSelected(true);
            user_order.setSelected(false);



        }else{
            us_sy_both.setSelected(false);
            system_order.setSelected(false);
            user_order.setSelected(true);

        }


    }
}
