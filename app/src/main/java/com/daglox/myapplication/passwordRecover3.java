package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.HashMap;
import java.util.Map;

public class passwordRecover3 extends AppCompatActivity {

    EditText edtNewPassword, edtNewPasswordRepeated;
    String mail,NewPassword,NewPasswordRepeated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover3);

        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtNewPasswordRepeated=findViewById(R.id.edtNewPasswordConfirm);
        //getSupportActionBar().setTitle("Cambio de clave");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent recievedIntent=getIntent();
        Bundle extras=recievedIntent.getExtras();
        mail=extras.getString("EMAIL");

        if(mail.isEmpty()){
            Intent intent=new Intent(this,passwordRecover1.class);
            finish();
            startActivity(intent);
        }

    }

    public void Password_Save(View view) {
        NewPassword = edtNewPassword.getText().toString().trim();
        NewPasswordRepeated = edtNewPasswordRepeated.getText().toString().trim();
        Boolean Change = false;
        if (!NewPassword.isEmpty() && NewPassword.length() >= 6
                && !NewPasswordRepeated.isEmpty() && NewPasswordRepeated.length() >= 6)
            Change = true;
        if (Change) {
            if (NewPassword.equals(NewPasswordRepeated)) {
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    final String RequestBody = "{"+
                            "\"email\"" +":"+ "\""+mail+"\",\"password\":"+"\""+NewPassword+"\"}";
                    Log.e("Mes",RequestBody);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_UPDATE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject json_obj = new JSONObject(response);
                                        Log.e("Response",response);
                                        String registro = json_obj.getString("message");
                                        if (registro.equals("PasswordUpdated")) {
                                            Toast.makeText(passwordRecover3.this, "Se ha cambiado su clave exitosamente", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(passwordRecover3.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (registro.equals("ErrorJson")) {
                                            Toast.makeText(passwordRecover3.this, "No se pudo actualizar su clave", Toast.LENGTH_SHORT).show();
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
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Ocp-Apim-Rover", URL.URL_KEY);
                            return headers;
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


            } else
                Toast.makeText(this, "Las claves deben coincidir", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Ingrese campos v??lidos: clave de 6 d??gitos m??nimos", Toast.LENGTH_SHORT).show();

    }


}
