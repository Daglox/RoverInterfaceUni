package com.daglox.myapplication.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daglox.myapplication.LoginActivity;
import com.daglox.myapplication.MainActivity;
import com.daglox.myapplication.Presenter.DashboardFragmentPresenter;
import com.daglox.myapplication.R;
import com.daglox.myapplication.URL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment {
    private FloatingActionButton fabRefresh;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        private GoogleMap mMap;
        private int position;
        private Context context;
        private MapsClass arr[];
        private int num;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            //Preguntarme si ya me he logueado anteriormente
            //SharedPreferences pref=getContext().getSharedPreferences("data.xml",Context.MODE_PRIVATE);
            //String user = pref.getString("data",null);

            //if (user!=null){
            //    Log.e("data",user);
            //}

            try{
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_MAP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject json_obj = new JSONObject(response);
                                    Log.e("Response",response);
                                    String registro = json_obj.getString("geolocations");
                                    Log.e("AVER",registro);
                                    JSONArray jsonArray=new JSONArray(registro);
                                    MapsClass arr[]= new MapsClass[jsonArray.length()];
                                    num=jsonArray.length();
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        String v_long= obj.getString("longitud");
                                        String v_lat= obj.getString("latitud");
                                        Log.e("al cine"+String.valueOf(i),v_long);
                                        Log.e("al x"+String.valueOf(i),v_lat);
                                        //SharedPreferences variables almacenadas en el disco duro

                                        //SharedPreferences pref=getContext().getSharedPreferences("data.xml",Context.MODE_PRIVATE);
                                        //SharedPreferences.Editor editor=pref.edit();

                                        //String val_cod=v_long;

                                        //editor.putString("data",val_cod);
                                        //editor.commit(); //Graba las variables en Usuario.xml


                                        float f_long=Float.parseFloat(v_long);
                                        float f_lat=Float.parseFloat(v_lat);


                                        // Add a marker in Sydney and move the camera
                                        LatLng p1 = new LatLng(f_long, f_lat);
                                        /*
                                        LatLng p2 = new LatLng(-13.417709912849299, -76.13230482495895);
                                        LatLng p3 = new LatLng(-13.417778406103029, -76.13506375861964);
                                        LatLng p4 = new LatLng(-13.417636999999988, -76.1352629);

                                         */
                                        arr[i]=new MapsClass(p1,f_long,f_lat);

                                    }
                                    for(int i = 0; i < num; i++){
                                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                        Log.e("op",String.valueOf(arr[i].getPunto()));
                                        if (i==0 || i== num-1){
                                            mMap.addMarker(new MarkerOptions().position(arr[i].getPunto()).title("Prueba").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                                        }
                                        else{
                                            mMap.addMarker(new MarkerOptions().position(arr[i].getPunto()).title("Prueba").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
                                            PolylineOptions rectOption = new PolylineOptions()
                                                    .add(arr[i].getPunto())
                                                    .add(arr[i+1].getPunto());
                                            Polyline polyline = mMap.addPolyline(rectOption);
                                        }



                                    }


                                    if (registro.contains("OK")) {
                                        Toast.makeText(getContext(), "Welcome333", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (registro.contains("InvalidPassword")) {
                                        Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                    }

                                    else if (registro.contains("IvalidUser")) {
                                        Toast.makeText(getContext(), "Invalid User", Toast.LENGTH_SHORT).show();
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
                        headers.put("Ocp-Apim-Rover", URL.URL_KEY_LOG);
                        return headers;
                    }

                };

                requestQueue.add(stringRequest);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }


        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        fabRefresh = v.findViewById(R.id.fabRefreshMap);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Actualizado", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().beginTransaction().detach(MapsFragment.this).attach(MapsFragment.this).commit();

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}