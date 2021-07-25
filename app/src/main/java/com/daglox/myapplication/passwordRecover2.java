package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class passwordRecover2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover2);
    }

    public void Password_Recover_3_Activity(View view){
        Intent intent=new Intent(this,passwordRecover3.class);
        startActivity(intent);
    }
}