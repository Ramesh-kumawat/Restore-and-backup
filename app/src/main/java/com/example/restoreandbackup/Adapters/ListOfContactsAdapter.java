package com.example.restoreandbackup.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Models.ListOfContarcts;
import com.example.restoreandbackup.R;

import java.util.ArrayList;

public class ListOfContactsAdapter extends RecyclerView.Adapter<ListOfContactsAdapter.ViewHolder> {

    ArrayList<ListOfContarcts> listOfContarcts;
    Context context;
    ContactClickListner contactClickListner;
    public static boolean longclick = false;



    public ListOfContactsAdapter(ArrayList<ListOfContarcts> listOfContarcts, Context context,ContactClickListner contactClickListner) {
        this.listOfContarcts = listOfContarcts;
        this.context = context;
        this.contactClickListner = contactClickListner;
    }

    @NonNull
    @Override
    public ListOfContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_data_restore, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListOfContactsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name_restore.setText(listOfContarcts.get(position).getName());
        holder.date_restore.setText(listOfContarcts.get(position).getNumber());
        holder.date_yu.setVisibility(View.GONE);

        if (listOfContarcts.get(position).getDate() != null) {
            holder.date_yu.setVisibility(View.VISIBLE);
            holder.date_yu.setText(listOfContarcts.get(position).getDate());
        }
        if (listOfContarcts.get(position).getBitmap() != null) {
            holder.tv_name.setVisibility(View.GONE);
            holder.imageView3.setVisibility(View.VISIBLE);
            holder.imageView3.setImageBitmap(listOfContarcts.get(position).getBitmap());

        } else {
            holder.imageView3.setVisibility(View.INVISIBLE);
            holder.tv_name.setVisibility(View.VISIBLE);
            holder.tv_name.setText(printInitials(listOfContarcts.get(position).getName()));

        }

        holder.cl_restore_data.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                longclick = true;
                return false;
            }
        });



        holder.cl_restore_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(longclick){

                     contactClickListner.ClickEventHandel(position,listOfContarcts.get(position));


                }
            }
        });


    }

    public interface ContactClickListner {

         void ClickEventHandel(int position,ListOfContarcts listOfContarcts);

    }

    @Override
    public int getItemCount() {
        return listOfContarcts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView3;
        TextView name_restore;
        TextView date_restore;
        TextView date_yu;
        TextView tv_name;
        ConstraintLayout cl_restore_data;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView3 = itemView.findViewById(R.id.imageView3);
            name_restore = itemView.findViewById(R.id.name_restore);
            date_restore = itemView.findViewById(R.id.date_restore);
            date_yu = itemView.findViewById(R.id.date_yu);
            tv_name = itemView.findViewById(R.id.tv_name1);
            cl_restore_data = itemView.findViewById(R.id.cl_restore_data);


        }
    }

    private String printInitials(String name) {
        String[] nameParts = name.split(" ");
        String firstName = nameParts[0];
        String firstNameChar = String.valueOf(firstName.charAt(0));
        if (nameParts.length > 1) {
            System.out.println("First character of first name: " + firstNameChar);
            String lastName = nameParts[nameParts.length - 1];
            char lastNameChar = lastName.charAt(0);
            System.out.println("First character of last name: " + lastNameChar);
            return firstNameChar + lastNameChar;
        } else {

            return firstNameChar;
//            System.out.println("First character name: " + firstNameChar);
        }
    }
}
