package com.ib.hunger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ib.hunger.R;
import com.ib.hunger.activities.ExpandedPeopleAddr;
import com.ib.hunger.models.peopleAddr_model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class peopleAddr_adapter extends RecyclerView.Adapter<peopleAddr_adapter.MyViewHolder> implements Filterable {
    Context context;
    List<peopleAddr_model> data;
    List<peopleAddr_model> datalist;


    public peopleAddr_adapter(Context context, List<peopleAddr_model> data) {
        this.context = context;
        this.data = data;
        datalist = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public peopleAddr_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.peoplesaddr_adapter_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull peopleAddr_adapter.MyViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.addr.setText(data.get(position).getAddress());
        holder.locality.setText(data.get(position).getLocality());
        holder.about.setText(data.get(position).getAbout());
        holder.peopleAddr_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ExpandedPeopleAddr.class);
                i.putExtra("title", data.get(position).getTitle());
                i.putExtra("addr", data.get(position).getAddress());
                i.putExtra("locality", data.get(position).getLocality());
                i.putExtra("about", data.get(position).getAbout());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return dataListFilter;
    }

    private final Filter dataListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
         List<peopleAddr_model> filteredList = new ArrayList<>();
         if(constraint == null || constraint.length() == 0){
             filteredList.addAll(datalist);
         }else{
             String filterPattern = constraint.toString().toLowerCase().trim();
             for(peopleAddr_model item: datalist){
                 if(item.getTitle().toLowerCase().contains(filterPattern)){
                     filteredList.add(item);
                 }
             }
         }
         FilterResults filterResults= new FilterResults();
         filterResults.values= filteredList;
         return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((Collection<? extends peopleAddr_model>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, addr, locality, about;
        ConstraintLayout peopleAddr_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_peopleAddr);
            addr = itemView.findViewById(R.id.addr_peopleAddr);
            locality = itemView.findViewById(R.id.locality_peopleAddr);
            about = itemView.findViewById(R.id.about_peopleAddr);
            peopleAddr_layout = itemView.findViewById(R.id.peoplesAddr_layout);
        }
    }

}
