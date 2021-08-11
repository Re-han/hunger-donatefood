package com.ib.hunger.loginAsReceiverActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ib.hunger.R;

import java.util.Objects;

public class LoginAsDonorPrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_receiver_privacy_policy);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}