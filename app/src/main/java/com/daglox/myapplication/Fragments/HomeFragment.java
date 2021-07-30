package com.daglox.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daglox.myapplication.R;

public class HomeFragment extends Fragment {

    private EditText edtNumber;
    private Button btnRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        edtNumber = v.findViewById(R.id.edtNumber);
        btnRefresh = v.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().detach(HomeFragment.this)
                        .attach(HomeFragment.this).commit();
                Toast.makeText(getContext(),"Refresh de la actividad", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}