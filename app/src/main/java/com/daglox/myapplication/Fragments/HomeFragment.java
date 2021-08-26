package com.daglox.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.Presenter.HomeFragmentPresenter;
import com.daglox.myapplication.Presenter.IHomeFragmentPresenter;
import com.daglox.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements IHomeFragment{

    private FloatingActionButton fabRefresh;
    private TextView tvTemperatureInfo;
    private TextView tvHumidityInfo;
    private TextView tvDatetimeInfo;
    private IHomeFragmentPresenter iHomeFragmentPresenter;
    private EnvironmentItem environmentItem;
    private Float temperature;
    private Float humidity;
    private Boolean temperatureSave;
    private Boolean humiditySave;
    private Context context;
    private IHomeFragment iHomeFragment;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        iHomeFragmentPresenter = new HomeFragmentPresenter(this, getContext());
        tvTemperatureInfo = v.findViewById(R.id.tvTemperatureInfo);
        tvHumidityInfo = v.findViewById(R.id.tvHumidityInfo);
        tvDatetimeInfo = v.findViewById(R.id.tvDatetimeInfo);
        fabRefresh = v.findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iHomeFragmentPresenter.getEnvironmentInfo();
            }
        });
        return v;
    }

    @Override
    public void showEnvironmentInfo(EnvironmentItem environmentItem) {
        temperature = (Float) environmentItem.getTemperature();
        humidity = (Float) environmentItem.getHumidity();
        tvTemperatureInfo.setText(String.valueOf(temperature) + " °C");
        tvHumidityInfo.setText(String.valueOf(humidity) + " %");
        tvDatetimeInfo.setText(environmentItem.getDatetime());
        onSaveInstanceState(bundle);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(getContext(),"onSaveInstanceState",Toast.LENGTH_LONG).show();
        outState.putFloat("key_temperature", 20.0f);
        outState.putFloat("key_humidity", 10.0f);
        onViewStateRestored(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Toast.makeText(getContext(),"onViewStateRestored",Toast.LENGTH_LONG).show();

        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            Toast.makeText(getContext(),"get data saved",Toast.LENGTH_LONG).show();
            temperatureSave = savedInstanceState.getBoolean("key_temperature");
            humiditySave = savedInstanceState.getBoolean("key_humidity");
            tvTemperatureInfo.setText(String.valueOf(temperatureSave));
            tvHumidityInfo.setText(String.valueOf(humiditySave));
        }
    }
}