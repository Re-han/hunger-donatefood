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

public class MobileLogin extends AppCompatActivity {
    private TextView backtoLogin;
    private Button submitbtn;
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_login);
        backtoLogin = findViewById(R.id.goBack);
        submitbtn = findViewById(R.id.submitButton);
        textInputLayout = findViewById(R.id.mobilenumber);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        backtoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileLogin.this, LoginActivity.class));
                finish();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.requireNonNull(textInputLayout.getEditText()).getText().length() == 0 || textInputLayout.getEditText().getText().length() < 10) {
                    textInputLayout.setError("Enter a valid mobile number");
                } else {
                    textInputLayout.setError("");
                    startActivity(new Intent(MobileLogin.this, VerficationMobileNo.class));
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(MobileLogin.this, LoginActivity.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MobileLogin.this, LoginActivity.class));
        finish();
    }
}