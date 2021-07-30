package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class passwordRecover2 extends AppCompatActivity {
    EditText edt1, edt2, edt3, edt4;
    Button btnEnter;
    String mail, code,Code1,Code2,Code3,Code4, EnteredCode;
    long StartTime, EndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover2);
        edt1 = findViewById(R.id.edtCode1);
        edt2 = findViewById(R.id.edtCode2);
        edt3 = findViewById(R.id.edtCode3);
        edt4 = findViewById(R.id.edtCode4);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt1.getText().toString().length()==1){
                    edt2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt2.getText().toString().length()==1){
                    edt3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt3.getText().toString().length()==1){
                    edt4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        Intent recievedIntent=getIntent();
        Bundle extras=recievedIntent.getExtras();
        mail=extras.getString("EMAIL");
        code=extras.getString("CODE");
        StartTime= extras.getLong("START");

        if(mail.isEmpty() || code.isEmpty()){
            Intent intent=new Intent(this,passwordRecover1.class);
            finish();
            startActivity(intent);
        }
    }

    public void Password_Recover_3_Activity(View view){
        Code1=edt1.getText().toString();
        Code2=edt2.getText().toString();
        Code3=edt3.getText().toString();
        Code4=edt4.getText().toString();
        EnteredCode=Code1+Code2+Code3+Code4;
        Boolean Change = false;
        if (!EnteredCode.isEmpty() && EnteredCode.length() == 4 )
            Change = true;
        if (Change) {
            if (EnteredCode.equals(code)) {
                EndTime = System.nanoTime();
                long difference = (EndTime - StartTime) / 1000000000;
                Log.e("Elapsed", String.valueOf(difference));
                if (difference < 180) {
                    Toast.makeText(this, "Código ingresado válido", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,passwordRecover3.class);
                    intent.putExtra("EMAIL",mail);
                    startActivity(intent);
                } else
                    Toast.makeText(this, "El código ya no es válido, solicite uno nuevo", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, "El código ingresado no es válido", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Ingrese campos válidos: Código de 4 dígitios", Toast.LENGTH_SHORT).show();

    }
    }
