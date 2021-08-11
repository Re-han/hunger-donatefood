package com.ib.hunger.loginAsReceiverActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.ib.hunger.R;

import java.util.Objects;

public class LoginAsDonorCreateRequest extends AppCompatActivity {
    TextInputLayout title, req;
    Button sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_receiver__create_request);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.createReq_title);
        req = findViewById(R.id.createReq_req);
        sub = findViewById(R.id.createReq_submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getEditText().getText().length() == 0 && (req.getEditText().getText().length() == 0)) {
                    req.setError("Fields can't be Empty");
                    title.setError("Fields can't be Empty");
                } else {
                    req.setError("");
                    title.setError("");
                    String titleText = title.getEditText().getText().toString();
                    String reqText = req.getEditText().getText().toString();
                    Intent i = new Intent(LoginAsDonorCreateRequest.this, LoginAsDonorManageRequest.class);
                    i.putExtra("title", titleText);
                    i.putExtra("req", reqText);
                    startActivity(i);
                    finish();
                }
            }
        });
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