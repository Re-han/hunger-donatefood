package com.ib.hunger.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Helper.PrefManager;
import com.ib.hunger.R;
import com.ib.hunger.activities.MainActivity;
import com.ib.hunger.activities.UpdateProfile;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    CircleImageView imageView;
    TextView updatePro,home_allReviews,home_email;
    ImageButton home_forward;
    String eimage,username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.homeImg);
        updatePro = view.findViewById(R.id.home_updateProfile);
        home_allReviews=view.findViewById(R.id.home_allReviews);
        home_forward=view.findViewById(R.id.home_forward);
        home_email=view.findViewById(R.id.home_email);

        eimage= PrefManager.getStr(getActivity(), Contants.image);
        username= PrefManager.getStr(getActivity(), Contants.email);

        if((username != null && !username.isEmpty() && !username.equals("null"))) {
            home_email.setText(username);

        }


        home_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RequestFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_holder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if((eimage != null && !eimage.isEmpty() && !eimage.equals("null"))){
            Picasso.get().load(Contants.image_url + eimage).into(imageView);

        }else {
            imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        updatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext()).startActivity(new Intent(getContext(), UpdateProfile.class));
            }
        });
//        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("Image", Context.MODE_PRIVATE);
//        String imageUri = sharedPreferences.getString("imageString", "");
//        Glide.with(getContext()).load(StringToBitMap(imageUri)).into(imageView);
//
//        SharedPreferences sharedPreferences_lad_My_acc_get_take_pic_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image", Context.MODE_PRIVATE);
//        String imageUri_takePic = sharedPreferences_lad_My_acc_get_take_pic_get.getString("imageString", "");
//        Glide.with(getContext()).load(StringToBitMap(imageUri_takePic)).into(imageView);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}