package com.ib.hunger.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ib.hunger.R;
import com.ib.hunger.adapters.imageAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.Objects;

public class ExpandedPeopleAddr extends AppCompatActivity {
    TextView title, addr, locality, about, readmore, readmore2;
    Button contactUs, ratings;
    boolean isTextViewClicked = false;
    private DotsIndicator dotsIndicator;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_people_addr);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager = findViewById(R.id.viewPager);
        imageAdapter adapter = new imageAdapter(this);
        viewPager.setAdapter(adapter);
        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);
        ratings = findViewById(R.id.expandedButton_rateUs);
        readmore = findViewById(R.id.readmore);
        readmore2 = findViewById(R.id.readmore2);
        title = findViewById(R.id.expandedTitle);
        contactUs = findViewById(R.id.expandedButton_contactUs);
        addr = findViewById(R.id.expandedAddress);
        locality = findViewById(R.id.expandedLocality);
        about = findViewById(R.id.expandedAbout);
        Bundle b = getIntent().getExtras();
        title.setText(String.valueOf(b.get("title")));
        addr.setText(String.valueOf(b.get("addr")));
        locality.setText(String.valueOf(b.get("locality")));
        about.setText(String.valueOf(b.get("about")));
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandedPeopleAddr.this, ExpandedContactUs.class));
            }
        });

        readmore.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    addr.setMaxLines(2);
                    readmore.setText("Readmore");
                    isTextViewClicked = false;
                } else {
                    addr.setMaxLines(Integer.MAX_VALUE);
                    readmore.setText("Readless");
                    isTextViewClicked = true;
                }
            }
        });
        readmore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextViewClicked) {
                    about.setMaxLines(2);
                    readmore2.setText("Readmore");
                    isTextViewClicked = false;
                } else {
                    about.setMaxLines(Integer.MAX_VALUE);
                    readmore2.setText("Readless");
                    isTextViewClicked = true;
                }
            }
        });
        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandedPeopleAddr.this, Ratings.class));
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