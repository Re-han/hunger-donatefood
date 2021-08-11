package com.ib.hunger.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ib.hunger.R;
import com.ib.hunger.activities.ChangePassword;
import com.ib.hunger.activities.ContactUs;
import com.ib.hunger.activities.LoginActivity;
import com.ib.hunger.activities.PrivacyPolicy;
import com.ib.hunger.activities.TermsAndConditions;

import java.util.List;

public class myAccount_adapter extends RecyclerView.Adapter<myAccount_adapter.MyViewHolder> {
    Context context;
    List<String> dataList;
    int[] images = {R.drawable.ic_baseline_lock,  R.drawable.ic_baseline_account_circle_24,
            R.drawable.ic_baseline_notes_24, R.drawable.ic_baseline_policy_24,
            R.drawable.ic_baseline_share_24, R.drawable.ic_baseline_power_settings};

    public myAccount_adapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public myAccount_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_account_adapter_design, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myAccount_adapter.MyViewHolder holder, int position) {
        holder.options.setText(dataList.get(position));
        holder.profileImg.setImageDrawable(ContextCompat.getDrawable(context, images[position % 6]));
        holder.optionsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == 0) {
                    context.startActivity(new Intent(context, ChangePassword.class));
                } else if (holder.getAdapterPosition() == 1) {
                    context.startActivity(new Intent(context, ContactUs.class));
                } else if (holder.getAdapterPosition() == 2) {
                    context.startActivity(new Intent(context, TermsAndConditions.class));
                } else if (holder.getAdapterPosition() == 3) {
                    context.startActivity(new Intent(context, PrivacyPolicy.class));
                } else if (holder.getAdapterPosition() == 4) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, "Send to:");
                    context.startActivity(Intent.createChooser(i, null));
                } else if (holder.getAdapterPosition() == 5) {
                    Intent intent=new Intent(context,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                 //   context.startActivity(new Intent(context, LoginActivity.class));
                }

//               Toast.makeText(context,String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImg;
        TextView options;
        ConstraintLayout optionsLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            optionsLayout = itemView.findViewById(R.id.optionsLayout);
            profileImg = itemView.findViewById(R.id.myAcc_imageView);
            options = itemView.findViewById(R.id.myAcc_textView);
        }
    }
}
