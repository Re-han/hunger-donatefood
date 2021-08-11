package com.ib.hunger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ib.hunger.R;
import com.ib.hunger.models.fragment_notification_model;
import com.ib.hunger.models.fragment_request_model;

import java.util.List;

public class fragment_notification_adapter extends RecyclerView.Adapter<fragment_notification_adapter.MyViewHolder> {
    Context context;
    List<fragment_notification_model> datalist;

    public fragment_notification_adapter(Context context, List<fragment_notification_model> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public fragment_notification_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_notification_adapter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull fragment_notification_adapter.MyViewHolder holder, int position) {
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
            title = itemView.findViewById(R.id.notification_title);
            req = itemView.findViewById(R.id.notification_req);
            date = itemView.findViewById(R.id.notification_datetime);
        }
    }
}
