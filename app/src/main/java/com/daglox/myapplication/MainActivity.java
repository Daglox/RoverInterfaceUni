package com.daglox.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //loadFragment(homeFragment);
        fm.beginTransaction().add(R.id.frame_container,settingsFragment, "4").hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.frame_container, mapsFragment, "3").hide(mapsFragment).commit();
        fm.beginTransaction().add(R.id.frame_container,dashboardFragment, "2").hide(dashboardFragment).commit();
        fm.beginTransaction().add(R.id.frame_container,homeFragment, "1").commit();
        
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    //loadFragment(homeFragment);
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    return true;
                case R.id.dashboardFragment:
                    //loadFragment(dashboardFragment);
                    fm.beginTransaction().hide(active).show(dashboardFragment).commit();
                    active = dashboardFragment;
                    return true;
                case R.id.mapsFragment:
                    //loadFragment(mapsFragment);
                    fm.beginTransaction().hide(active).show(mapsFragment).commit();
                    active = mapsFragment;
                    return true;
                case R.id.settingsFragment:
                    //loadFragment(settingsFragment);
                    fm.beginTransaction().hide(active).show(settingsFragment).commit();
                    active = settingsFragment;
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment).commit();
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

    public void quit(View view){
        Toast.makeText(MainActivity.this, "HOLA", Toast.LENGTH_SHORT).show();
        SharedPreferences pref2 = getSharedPreferences("Usuario.xml",MODE_PRIVATE);
        SharedPreferences.Editor editor2=pref2.edit();
        editor2.putString("USUARIO",null);
        editor2.commit();
        Intent intent2 = new Intent(this, LoginActivity.class);
        startActivity(intent2);
    }
}