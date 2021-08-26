package com.daglox.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        iHomeFragmentPresenter = new HomeFragmentPresenter(this, getContext());
        tvTemperatureInfo = v.findViewById(R.id.tvTemperatureInfo);
        tvHumidityInfo = v.findViewById(R.id.tvHumidityInfo);
        tvDatetimeInfo = v.findViewById(R.id.tvDatetimeInfo);
        fabRefresh = v.findViewById(R.id.fabRefresh);
        return v;
    }*/

    @Override
    public void showEnvironmentInfo(EnvironmentItem environmentItem) {
        tvTemperatureInfo.setText(String.valueOf(environmentItem.getTemperature()) + " °C");
        tvHumidityInfo.setText(String.valueOf(environmentItem.getHumidity()) + " %");
        tvDatetimeInfo.setText(environmentItem.getDatetime());
    }

}