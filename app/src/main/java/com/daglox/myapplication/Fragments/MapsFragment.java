package com.daglox.myapplication.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment {

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
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;


            // Add a marker in Sydney and move the camera
            LatLng p1 = new LatLng(-13.417969906176912, -76.13333447971097);
            LatLng p2 = new LatLng(-13.417709912849299, -76.13230482495895);
            LatLng p3 = new LatLng(-13.417778406103029, -76.13506375861964);
            LatLng p4 = new LatLng(-13.417636999999988, -76.1352629);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(p1).title("Caffe Sport"));
            mMap.addMarker(new MarkerOptions().position(p2).title("Plaza de Armas Chincha"));
            mMap.addMarker(new MarkerOptions().position(p3).title("Caja Municipal Ica"));
            mMap.addMarker(new MarkerOptions().position(p4).title("Gelateria Calderon"));
            switch (position){
                case 0:
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p1,19));
                    PolylineOptions rectOption = new PolylineOptions()
                            .add(p1)
                            .add(p2);
                    Polyline polyline = mMap.addPolyline(rectOption);
                    break;
                case 1:
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2,19));
                    break;
                case 2:
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p3,19));
                    break;
                case 3:
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p4,19));
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

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
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    String secc= obj.getString("longitud");
                                    Log.e("al cine"+String.valueOf(i),secc);
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
                    headers.put("Apim-Rover-Key", URL.URL_KEY_LOG);
                    return headers;
                }

            };

            requestQueue.add(stringRequest);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
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