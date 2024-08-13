package com.example.restoreandbackup.Adapters;

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

import com.example.restoreandbackup.Models.ListofEvents;
import com.example.restoreandbackup.R;

import java.util.ArrayList;
import java.util.Date;


public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.ViewHolder> {

    ArrayList<ListofEvents> listofCalenders;
    Context context;
    ClickEventForCalender clickEventForCalender;
    public static boolean clickcalender;



    public CalendarEventAdapter(ArrayList<ListofEvents> listofCalenders, Context context, ClickEventForCalender clickEventForCalender) {
        this.listofCalenders = listofCalenders;
        this.context = context;
        this.clickEventForCalender = clickEventForCalender;
    }

    @NonNull
    @Override
    public CalendarEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout_view, parent, false);
        return new CalendarEventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarEventAdapter.ViewHolder holder, int position) {

        Date date = listofCalenders.get(position).getDtstart();

        String dayOfTheWeek = (String) DateFormat.format("EEE", date);
        String day = (String) DateFormat.format("dd", date);
        String monthNumber = (String) DateFormat.format("MMM", date);
        String year = (String) DateFormat.format("yyyy", date);
        String time = (String) DateFormat.format("h:mm a", date);


        if (listofCalenders.get(position).getTitle().equals("")) {

            holder.tv_title_event.setText("No Title");
        } else {
            holder.tv_title_event.setText(listofCalenders.get(position).getTitle());

        }

        holder.tv_time.setText(dayOfTheWeek + ", " + day + " " + " " + monthNumber + ", " + year);

        if (listofCalenders.get(position).getAllday().equals("1")) {

            holder.all_days.setText(" All days ");
        } else {
            if (listofCalenders.get(position).getDtend() != null) {
                String date1 = listofCalenders.get(position).getDtend();
                Date callDayTime = new Date(Long.parseLong(date1));
                String time1 = (String) DateFormat.format("h:mm a", callDayTime);
                holder.all_days.setText(time + "-" + time1);
            }
        }

        holder.cv_calender_list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickcalender = true;
                return false;
            }
        });
        holder.cv_calender_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clickcalender){
                clickEventForCalender.SelectCalender(position, listofCalenders.get(position));
            }}
        });

    }

    public interface ClickEventForCalender{
        void SelectCalender(int position,ListofEvents listofEvents);
    }

    @Override
    public int getItemCount() {
        return listofCalenders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_icon2;
        TextView tv_title_event, tv_time, all_days;
        ConstraintLayout cv_calender_list;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_calender_list = itemView.findViewById(R.id.cv_calender_list);
            img_icon2 = itemView.findViewById(R.id.img_icon2);
            tv_title_event = itemView.findViewById(R.id.tv_title_event);
            tv_time = itemView.findViewById(R.id.tv_time);
            all_days = itemView.findViewById(R.id.all_days);

        }
    }
}
