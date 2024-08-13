package com.example.restoreandbackup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.example.restoreandbackup.Models.ListofCalender;
import com.example.restoreandbackup.R;

import java.util.ArrayList;
import java.util.Date;

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    ArrayList<ListofCalender> listofCalenders;
    Context context;
    Date date;
    ClickEventOfCalender clickEventOfCalender;
    public static boolean selectLong;


    public EventAdapter(ArrayList<ListofCalender> listofCalenders, Context context, ClickEventOfCalender clickEventOfCalender) {
        this.listofCalenders = listofCalenders;
        this.context = context;
        this.clickEventOfCalender = clickEventOfCalender;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_calender_data,parent,false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        holder.date_calender.setText(listofCalenders.get(position).getCallDayTime().toString());



        if(listofCalenders.get(position).getName().equals("")){
            holder.name_calender.setText(listofCalenders.get(position).getPhNumber());
            if(listofCalenders.get(position).getPhNumber() != null) {
                String idd = listofCalenders.get(position).getPhNumber().substring(0, 1);
            holder.tv_name3.setText(idd);
        }}
            else{
            holder.name_calender.setText(listofCalenders.get(position).getName());
            String idd = listofCalenders.get(position).getName().substring(0,1);
            holder.tv_name3.setText(idd);
        }


        String date2 = listofCalenders.get(position).getCallDayTime();

        long _date = Long.parseLong(date2);
        Date date = new Date(_date);

        Log.d("TAG", "onBindViewHolder: "+date);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
        String day          = (String) DateFormat.format("dd",   date);
        String monthString  = (String) DateFormat.format("MMMM",  date);
        String monthNumber  = (String) DateFormat.format("MM",   date);
        String year         = (String) DateFormat.format("yyyy", date);


        Date date1 = new Date();
        String dayOfTheWeek1 = (String) DateFormat.format("EEEE", date1);
        String day1          = (String) DateFormat.format("dd",   date1);
        String monthString1  = (String) DateFormat.format("MMM",  date1);
        String monthNumber1  = (String) DateFormat.format("MM",   date1);
        String year1        = (String) DateFormat.format("yyyy", date1);





        if(monthNumber.equals(monthNumber1) && year.equals(year1)){

            int days = Math.abs(Integer.parseInt(day1) - Integer.parseInt(day));



            Log.d("TAG", "onBindViewHolder: "+days);
            holder.date_calender.setText(days + " days");

        }else if(!monthNumber.equals(monthNumber1) && year.equals(year1)){

            holder.date_calender.setText(day+" "+monthString);

        }else{

            holder.date_calender.setText(day+" "+monthString+" "+year);

        }




          int seconds = Integer.parseInt(listofCalenders.get(position).getCallDuration());
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            int second = seconds % 60;

            if(minutes == 0 && second == 0 && hours == 0){

                holder.day_calender.setText(second+ " sec " );

            }else if(hours == 0){

                holder.day_calender.setText(minutes+" min "+second+ " sec " );


            }else{

                holder.day_calender.setText(hours+" hours "+ minutes+" min "+second+ " sec " );

            }



        String i = listofCalenders.get(position).getDir1();

        Log.d("TAG", "onBindViewHolder: "+i);



if(i == null){
    holder.img_icon4.setImageResource(R.drawable.ic_baseline_call_made_24);

}else {

    if (i.contains("OUTGOING")) {
        holder.img_icon4.setImageResource(R.drawable.ic_baseline_call_made_24);
    } else if (i.contains("INCOMING")) {
        holder.img_icon4.setImageResource(R.drawable.ic_baseline_call_received_24);
    } else if (i.contains("MISSED")) {
        holder.img_icon4.setImageResource(R.drawable.ic_baseline_call_missed_24);
    }
}
//        String idd = listofCalenders.get(position).getPhNumber().substring(0,1);;
//        holder.tv_name3.setText(idd);

holder.cv_calender.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        selectLong = true;
        return false;
    }
});
        holder.cv_calender.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (selectLong) {
            clickEventOfCalender.SelectMessage(position,listofCalenders.get(position));
        }
    }
});

    }

   public interface ClickEventOfCalender{
        void SelectMessage(int position,ListofCalender listofCalender);
    }

    @Override
    public int getItemCount() {
        return listofCalenders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_calender,date_calender,day_calender,tv_name3;
        ImageView img_icon4;
        ConstraintLayout cv_calender;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_icon4 = itemView.findViewById(R.id.img_icon4);
            tv_name3 = itemView.findViewById(R.id.tv_name3);
            name_calender = itemView.findViewById(R.id.name_calender);
            date_calender = itemView.findViewById(R.id.date_calender);
            day_calender = itemView.findViewById(R.id.day_calender);
            cv_calender = itemView.findViewById(R.id.cv_calender);
        }
    }
}
