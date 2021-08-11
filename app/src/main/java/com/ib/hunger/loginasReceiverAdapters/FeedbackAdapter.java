package com.ib.hunger.loginasReceiverAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ib.hunger.R;
import com.ib.hunger.LoginAsReceiver_Models.Feedback_model;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    Context context;
    List<Feedback_model> datalist;

    public FeedbackAdapter(Context context, List<Feedback_model> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lad_feedback_adapter_design, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setImageDrawable(datalist.get(position).getProfile());
        holder.name.setText(datalist.get(position).getName());
        holder.visited.setText(datalist.get(position).getVisited());
        holder.review.setText(datalist.get(position).getReview());
        holder.date.setText(datalist.get(position).getDate());
        holder.ratingBar.setRating(datalist.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, visited, date, review;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.lad_profileImgFeedback);
            name = itemView.findViewById(R.id.name);
            visited = itemView.findViewById(R.id.visited);
            date = itemView.findViewById(R.id.feedback_date);
            review = itemView.findViewById(R.id.lad_feedback_written);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}
