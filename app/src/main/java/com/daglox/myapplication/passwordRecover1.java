package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class passwordRecover1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover1);
    }

    public void Password_Recover_2_Activity(View view){
        Intent intent=new Intent(this,passwordRecover2.class);
        startActivity(intent);
    }
}