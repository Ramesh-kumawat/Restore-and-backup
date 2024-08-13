package com.example.restoreandbackup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restoreandbackup.Models.RestoreContractsModel;
import com.example.restoreandbackup.R;

import java.util.ArrayList;
import java.util.List;

public class VcfFileAdapter extends RecyclerView.Adapter<VcfFileAdapter.ViewHolder> {

    List<RestoreContractsModel> list = new ArrayList<>();
    Context context;
    OnclickvcfFileListnear onclickvcfFileListnear;

    public VcfFileAdapter(List<RestoreContractsModel> list, Context context, OnclickvcfFileListnear onclickvcfFileListnear) {
        this.list = list;
        this.context = context;
        this.onclickvcfFileListnear = onclickvcfFileListnear;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_data_restore,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.date.setText(list.get(position).getDate());


        holder.cl_restore_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onclickvcfFileListnear.ClickvcfFile(list.get(position),position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnclickvcfFileListnear{
        void ClickvcfFile(RestoreContractsModel restoreContractsModel,int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,date;
        ConstraintLayout cl_restore_data;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_restore);
            date = itemView.findViewById(R.id.date_restore);
            cl_restore_data = itemView.findViewById(R.id.cl_restore_data);

        }
    }
}
