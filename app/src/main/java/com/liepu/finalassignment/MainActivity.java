package com.liepu.finalassignment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.liepu.finalassignment.QR_Scan.FragmentAssignment;
import com.liepu.finalassignment.first.FragmentFirst;
import com.liepu.finalassignment.home.FragmentHome;
import com.liepu.finalassignment.second.FragmentSecond;
import com.liepu.finalassignment.third.FragmentThird;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_simple);
        navigationView.setOnItemSelectedListener(getNavigation());
        startHomeFragment();
    }

    private NavigationBarView.OnItemSelectedListener getNavigation() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) {
                    return false;
                }

                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        startHomeFragment();
                        return true;
                    }
                    case R.id.nav_first: {
                        startFirstFragment();
                        return true;
                    }
                    case R.id.nav_second: {
                        startSecondFragment();
                        return true;
                    }
                    case R.id.nav_third: {
                        startThirdFragment();
                        return true;
                    }
                    case R.id.nav_assignment: {
                        startAssignmentFragment();
                        return true;
                    }
                }
                return false;
            }
        };
    }

    private void startHomeFragment() {
        startFragment(new FragmentHome());
    }

    private void startFirstFragment() {
        startFragment(new FragmentFirst());
    }

    private void startSecondFragment() {
        startFragment(new FragmentSecond());
    }
    private void startThirdFragment() {
        startFragment(new FragmentThird());
    }

    private void startAssignmentFragment() {startFragment(new FragmentAssignment());}

    private void startFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}