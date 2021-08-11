package com.ib.hunger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ib.hunger.R;

import java.util.Objects;

public class VerficationMobileNo extends AppCompatActivity {
    private TextView backtoLogin;
    Button verify;
    TextInputLayout otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_mobile_no);
        backtoLogin = findViewById(R.id.goBack);
        otp = findViewById(R.id.otp);
        verify = findViewById(R.id.verifyotp);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        backtoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerficationMobileNo.this, MobileLogin.class));
                finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.getEditText().getText().length()==0){
                    otp.setError("Please enter otp");
                }
                else{
                    otp.setError("");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(VerficationMobileNo.this, MobileLogin.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(VerficationMobileNo.this, MobileLogin.class));
        finish();
    }
}