package com.ib.hunger.LoginAsReceiverFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Helper.PrefManager;
import com.ib.hunger.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoggedInAsDonorHomeFragment extends Fragment {

    CircleImageView imageView;
    String eimage,username;
    TextView lad_home_email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logged_in_as_receiver_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.lad_homeImg);
        lad_home_email=view.findViewById(R.id.lad_home_email);

        eimage= PrefManager.getDStr(getActivity(), Contants.dimage);
        username= PrefManager.getDStr(getActivity(), Contants.demail);

        if((username != null && !username.isEmpty() && !username.equals("null"))) {
            lad_home_email.setText(username);

        }

        SharedPreferences sharedPreferences_home_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
        String imageUri = sharedPreferences_home_get.getString("imageString_lad", "");
        Glide.with(getContext()).load(StringToBitMap(imageUri)).into(imageView);

        SharedPreferences sharedPreferences_lad_My_acc_get_take_pic_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
        String imageUri_takePic = sharedPreferences_lad_My_acc_get_take_pic_get.getString("imageString_lad", "");
        Glide.with(getContext()).load(StringToBitMap(imageUri_takePic)).into(imageView);
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