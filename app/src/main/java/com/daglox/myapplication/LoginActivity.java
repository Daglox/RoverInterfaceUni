package com.daglox.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText edtUser, edtPassword;
    Button btnEnter;
    //ProgressBar pgbLoad;

    //private String url="https://samuelcalixto98.000webhostapp.com/Laboratorio2/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //URL.URL_LOGIN;
        setContentView(R.layout.activity_login);
        /*Eliminando el Action bar del activity main */
        //getSupportActionBar().hide();
        /*Conectando los objetos JAVA con los XML*/
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnEnter = findViewById(R.id.btnForgotPassword);
        //txv_registrar = findViewById(R.id.txv_registrar_activity_main);
        //pgbLoad = findViewById(R.id.pgb_cargando_activiy_main);
        /*La unica orientacion valida es portrait (vista vertical, parado)*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
        //Preguntarme si ya me he logueado anteriormente
        SharedPreferences pref=getSharedPreferences("Usuario.xml",MODE_PRIVATE);
        String user = pref.getString("USUARIO",null);

        if (user!=null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }

         */
    }


    public void sign_in(View view){
        //pgbLoad.setVisibility(View.VISIBLE); /*Muestra el progressbar*/

        try{
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String usuario=edtUser.getText().toString();
            String pass = edtPassword.getText().toString();

            if (usuario.contains("@upc.edu.pe")){
                int nf=usuario.indexOf("@upc.edu.pe");
                usuario=usuario.substring(0,nf);
            }
            else{
                usuario=usuario;

            }
            final String requestBody= "{"+
                    "\"username\"" +":"+ "\""+usuario+"\",\"password\":"+"\""+pass+"\"}";
            Log.e("Mes",requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json_obj = new JSONObject(response);
                                Log.e("Response",response);
                                String registro = json_obj.getString("result");
                                Log.e("AVER",registro);
                                if (registro.contains("ValidUser")) {
                                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (registro.contains("InvalidPassword")) {
                                    Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }

                                else if (registro.contains("InvalidUser")) {
                                    Toast.makeText(LoginActivity.this, "Invalid User", Toast.LENGTH_SHORT).show();
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
                    headers.put("Apim-Rover-Key", URL.URL_KEY_LOG);
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("unsupported codification");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }//FIN de funcion ingresar_sistema

    public void Password_Recover_1_Activity(View View){
        Intent intent=new Intent(this,passwordRecover1.class);
        startActivity(intent);
    }
}