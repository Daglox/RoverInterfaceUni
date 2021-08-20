package com.daglox.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.daglox.myapplication.Fragments.DashboardFragment;
import com.daglox.myapplication.Fragments.HomeFragment;
import com.daglox.myapplication.Fragments.MapsFragment;
import com.daglox.myapplication.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    MapsFragment mapsFragment = new MapsFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(homeFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    loadFragment(homeFragment);
                    return true;
                case R.id.dashboardFragment:
                    loadFragment(dashboardFragment);
                    return true;
                case R.id.mapsFragment:
                    loadFragment(mapsFragment);
                    return true;
                case R.id.settingsFragment:
                    loadFragment(settingsFragment);
            }
            return false;
        }
    };


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(navigation.getSelectedItemId() == R.id.homeFragment) {
            super.onBackPressed();
            finish();
        }
        else{
            navigation.setSelectedItemId(R.id.homeFragment);
        }
    }
}