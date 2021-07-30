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

import java.io.UnsupportedEncodingException;


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
        edtUser = findViewById(R.id.Email_Input);
        edtPassword = findViewById(R.id.Password_Input);
        btnEnter = findViewById(R.id.button);
        //txv_registrar = findViewById(R.id.txv_registrar_activity_main);
        //pgbLoad = findViewById(R.id.pgb_cargando_activiy_main);
        /*La unica orientacion valida es portrait (vista vertical, parado)*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
            final String requestBody = "usuario="+usuario+"&clave="+pass;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LOGIN, new Response.Listener<String>() {
                @Override

                public void onResponse(String response) {
                    Log.e("Respuesta de Webhost", response);
                    //pgbLoad.setVisibility(View.GONE);
                    Log.e("AAAAAA",URL.URL_LOGIN);
                    if (response.contains("\"USUARIO\"")){

                        //SharedPreferences variables almacenadas en el disco duro
                        Toast.makeText(LoginActivity.this,"Bienvenido", Toast.LENGTH_SHORT).show();

                        SharedPreferences pref=getSharedPreferences("Usuario.xml",MODE_PRIVATE);
                        SharedPreferences.Editor editor=pref.edit();

                        String val_cod=edtUser.getText().toString();


                        editor.putString("USUARIO",val_cod);
                        editor.commit(); //Graba las variables en Usuario.xml

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "La clave o el usuario son errados",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("hola","izi");
                    Log.e("Error",error.getMessage());
                    Log.e("Resultado","fallo conexión "+error);
                }
            }){
                @Override
                public String getBodyContentType(){
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError
                {
                    try {
                        return requestBody==null? null:requestBody.getBytes("utf-8");

                    }catch(UnsupportedEncodingException uee)
                    {
                        VolleyLog.wtf("Codificacion no soportada al tratar de conectarse a %s",requestBody);
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