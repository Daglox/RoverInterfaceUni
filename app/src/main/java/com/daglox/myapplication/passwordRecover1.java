package com.daglox.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
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
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class passwordRecover1 extends AppCompatActivity {
    EditText edtMail;
    String mail,code;
    long StartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover1);
        edtMail = findViewById(R.id.edtBackEmail);
        //getSupportActionBar().setTitle("Cambio de clave");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void Password_Recover_2_Activity(View view) {
        mail= edtMail.getText().toString();
        Boolean Check = false;
        if (!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            Check = true;
        if (Check) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                final String RequestBody = "{"+
                        "\"email\"" +":"+ "\""+mail+"\""+"}";
                Log.e("send",RequestBody);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_VERIFY,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json_obj = new JSONObject(response);
                                    Log.i("Result", response);
                                    String registro = json_obj.getString("message");
                                    if (registro.equals("ValidEmail")) {
                                        SendMail();
                                    } else if (registro.equals("InvalidEmail")) {
                                        Toast.makeText(passwordRecover1.this, "El correo ingresado no está asociado a ningún usuario", Toast.LENGTH_SHORT).show();
                                    } else if (registro.equals("ErrorJson")) {
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

        }
        else
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
    }

    public void SendMail() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        try {
            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Credentials.CREDENTIAL_MAIL, Credentials.CREDENTIAL_PASSWORD);
                        }
                    });

            if (session != null) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(Credentials.CREDENTIAL_MAIL));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(mail));
                GenerateCode();
                message.setSubject("Recuperación de contraseña RoverApp");
                String mes="Buenas,\n\nIngrese el siguiente código en la aplicación " +
                        "para cambiar su contraseña: "+code+"\n\n El código expirará después" +
                        " de 3 minutos y tendrá que solicitar uno nuevo.";
                message.setText(mes);
                new SendEmail().execute(message);
                //Transport.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void GenerateCode() {
        String store="";
        for (int i = 0; i < 4; i++) {

            Random rand = new Random();
            int value = rand.nextInt(9 - 0);
            store=store+Integer.toString(value);
        }
        code=store;

    }

    private class SendEmail extends AsyncTask<Message, String, String> {
        //Initilize progress dialog
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Create and show progress dialog
            progressDialog = ProgressDialog.show(passwordRecover1.this,"Please wait",
                    "Sending Mail...",true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                //When success
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e){
                //When error
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Dismiss progress dialog
            progressDialog.dismiss();
            if (s.equals("Success")){
                //When Success
                StartTime= System.nanoTime();
                //Initialize
                AlertDialog.Builder builder = new AlertDialog.Builder(passwordRecover1.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail sent succesfully.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //Clear all edit text
                        Intent intent=new Intent(passwordRecover1.this,passwordRecover2.class);
                        intent.putExtra("EMAIL",mail);
                        intent.putExtra("CODE",code);
                        intent.putExtra("START",StartTime);
                        startActivity(intent);
                        finish();

                    }
                });
                //Show alert dialog
                builder.show();
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Something went wrong?", Toast.LENGTH_LONG).show();
            }
        }
    }


}