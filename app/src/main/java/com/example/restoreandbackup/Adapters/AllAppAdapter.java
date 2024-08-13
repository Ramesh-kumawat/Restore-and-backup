package com.example.restoreandbackup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Models.AppDataModel;
import com.example.restoreandbackup.Models.HelperResizer;
import com.example.restoreandbackup.R;

import java.util.Date;
import java.util.List;

public class AllAppAdapter extends RecyclerView.Adapter<AllAppAdapter.ViewHolder> {

    int app_order;
    List<AppDataModel> list;
    Context context;
    OnclickAppListnear onclickAppListnear;

    public AllAppAdapter(List<AppDataModel> list, Context context, OnclickAppListnear onclickAppListnear) {
        this.list = list;
        this.context = context;
        this.onclickAppListnear = onclickAppListnear;
    }


    @NonNull
    @Override
    public AllAppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAppAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        list.get(position).getIcon();


        HelperResizer.setSize(holder.img_icon,120,120);
        HelperResizer.setMargin(holder.img_icon,50,0,0,0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("RkPreference", Context.MODE_PRIVATE);
        app_order =  sharedPreferences.getInt("app_order",0);
        Date date = list.get(position).getDate1();

        String dayOfTheWeek = (String) DateFormat.format("EEE", date);
        String monthNumber = (String) DateFormat.format("MMM", date);
        String year = (String) DateFormat.format("yy", date);


        Log.d("TAG", "onBindViewHolder: "+app_order);
        Log.d("TAG", "onBindViewHolder: "+list.get(position).getType());



            holder.img_icon.setImageDrawable(list.get(position).getIcon());
            holder.tv_name.setText(list.get(position).getName());
            holder.app_version.setText(list.get(position).getVersion());
            holder.app_data.setText(list.get(position).getSize());
            holder.app_date.setText(dayOfTheWeek+" "+monthNumber+","+year);


//        }




        holder.cl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickAppListnear.clickapplistener(list.get(position));
            }
        });

    }

     public interface OnclickAppListnear{
        void clickapplistener(AppDataModel appDataModel);
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_icon;
        TextView tv_name,app_version,app_data,app_date;
        ConstraintLayout cl_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_icon = itemView.findViewById(R.id.img_icon1);
            tv_name = itemView.findViewById(R.id.tv_name1);
            app_version = itemView.findViewById(R.id.app_version1);
            app_data = itemView.findViewById(R.id.app_data1);
            app_date = itemView.findViewById(R.id.app_date1);
            cl_data = itemView.findViewById(R.id.cl_data);



        }
    }
}
