package com.example.restoreandbackup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Fragments.ArchieveFragment;
import com.example.restoreandbackup.R;

import java.util.Date;
import java.util.List;

    public class ArchiveListAdapter extends RecyclerView.Adapter<ArchiveListAdapter.ViewHolder> {

        Context context;
        List<ArchieveFragment.ListOfApp> list1;
        OnClickOpenListener onClickOpenListener;

        public ArchiveListAdapter(Context context, List<ArchieveFragment.ListOfApp> list1, OnClickOpenListener onClickOpenListener) {
            this.context = context;
            this.list1 = list1;
            this.onClickOpenListener = onClickOpenListener;
        }

        @NonNull
        @Override
        public ArchiveListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_view,parent,false);
            return new ArchiveListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArchiveListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Date date = list1.get(position).getDate();

            String dayOfTheWeek = (String) DateFormat.format("EEE", date);
            String monthNumber = (String) DateFormat.format("MMM", date);
            String year = (String) DateFormat.format("yy", date);




            holder.img_icon.setImageDrawable(list1.get(position).getImage());
            holder.tv_name.setText(list1.get(position).getName());
            holder.app_version.setText(list1.get(position).getVersion());
            holder.app_data.setText(list1.get(position).getSize());
            holder.app_date.setText(dayOfTheWeek+" "+monthNumber+","+year);

            holder.cl_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickOpenListener.DataClick(list1.get(position),position);
                }
            });

        }

        public interface OnClickOpenListener{

            void DataClick(ArchieveFragment.ListOfApp list,int position);


        }

        @Override
        public int getItemCount() {
            return list1.size();
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



