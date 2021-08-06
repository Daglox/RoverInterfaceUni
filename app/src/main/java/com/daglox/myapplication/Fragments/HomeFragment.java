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

public class HomeFragment extends Fragment implements IHomeFragment{

    private Button btnRefresh;
    private TextView tvTemperatureInfo;
    private TextView tvHumidityInfo;
    private IHomeFragmentPresenter iHomeFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        iHomeFragmentPresenter = new HomeFragmentPresenter(this, getContext());
        btnRefresh = v.findViewById(R.id.btnRefresh);
        return v;
    }

    @Override
    public void showEnvironmentInfo(EnvironmentItem environmentItem) {
        tvTemperatureInfo.setText(environmentItem.getTemperature());
        tvHumidityInfo.setText(environmentItem.getHumidity());
    }

}