package com.ib.hunger.loginasReceiverAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ib.hunger.R;
import com.ib.hunger.LoginAsReceiver_Models.lad_request_model;

import java.util.List;

public class Lad_RequestAdapter extends RecyclerView.Adapter<Lad_RequestAdapter.MyViewHolder> {
    Context context;
    List<lad_request_model> datalist;

    public Lad_RequestAdapter(Context context, List<lad_request_model> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lad_request_adpterdesign, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.req.setText(datalist.get(position).getReq());
        holder.title.setText(datalist.get(position).getTitle());
        holder.date.setText(datalist.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, req, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.req_title);
            req = itemView.findViewById(R.id.home_req);
            date = itemView.findViewById(R.id.home_req_datetime);
        }
    }
}
