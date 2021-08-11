package com.ib.hunger.loginAsReceiverActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ib.hunger.R;
import com.ib.hunger.LoginAsReceiverFragments.LoggedInAsDonorFeedback;
import com.ib.hunger.LoginAsReceiverFragments.LoggedInAsDonorHomeFragment;
import com.ib.hunger.LoginAsReceiverFragments.LoggedInAsDonorMyAccount;
import com.ib.hunger.LoginAsReceiverFragments.LoggedInAsDonorRequests;

public class MainActivity_LoggedInAsDonor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__logged_in_as_receiver);
        BottomNavigationView bottomNavigationView = findViewById(R.id.lad_frag_replacer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.lad_frag_holder, new LoggedInAsDonorHomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.lad_home) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.lad_frag_holder, new LoggedInAsDonorHomeFragment()).commit();
                }
                if (item.getItemId() == R.id.lad_feedback) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.lad_frag_holder, new LoggedInAsDonorFeedback()).commit();
                }
                if (item.getItemId() == R.id.lad_request) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.lad_frag_holder, new LoggedInAsDonorRequests()).commit();
                }
                if (item.getItemId() == R.id.lad_my_account) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.lad_frag_holder, new LoggedInAsDonorMyAccount()).commit();
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
finishAffinity();    }
}