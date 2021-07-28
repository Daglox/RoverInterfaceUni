package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class passwordRecover1 extends AppCompatActivity {
    EditText edtMail;
    String mail,code;
    long StartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover1);
        edtMail = findViewById(R.id.editTextTextEmailAddress2);
        //getSupportActionBar().setTitle("Cambio de clave");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void Password_Recover_2_Activity(View view) {
        mail= edtMail.getText().toString();
        Boolean Check = false;
        if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            Check = true;
        if (Check) {
            String request="key=12345" + "&mail="+ mail ;
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                final String RequestBody = request;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_SEND,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json_obj = new JSONObject(response);
                                    String registro = json_obj.getString("QUERYMAIL");
                                    if (registro.equals("OK")) {
                                        code=json_obj.getString("CODE");
                                        StartTime= System.nanoTime();
                                        Toast.makeText(passwordRecover1.this, "Se ha enviado el código a su correo", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(passwordRecover1.this,passwordRecover2.class);
                                        intent.putExtra("EMAIL",mail);
                                        intent.putExtra("CODE",code);
                                        intent.putExtra("START",StartTime);
                                        startActivity(intent);
                                    } else if (registro.equals("NONE")) {
                                        Toast.makeText(passwordRecover1.this, "El correo ingresado no está asociado a ningún usuario", Toast.LENGTH_SHORT).show();
                                    } else if (registro.equals("ER")) {
                                        Toast.makeText(passwordRecover1.this, "No se pudo realizar el envío", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Conexion Error", error.getMessage());
                                Log.e("Result", "there was an error " + error);
                            }
                        }) {
                    @Override
                    public String getPostBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return RequestBody == null ? null : RequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("unsupported codification");
                            return null;
                        }
                    }
                };

                requestQueue.add(stringRequest);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        else
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
    }

}