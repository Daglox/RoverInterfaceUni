package com.daglox.myapplication.Presenter;

import android.content.Context;

import com.daglox.myapplication.Fragments.IHomeFragment;
import com.daglox.myapplication.POJO.EnvironmentItem;

public class HomeFragmentPresenter implements IHomeFragmentPresenter {

    private IHomeFragment iHomeFragment;
    private Context context;
    private EnvironmentItem environmentItem;
    public HomeFragmentPresenter(IHomeFragment iHomeFragment, Context context){
        this.iHomeFragment = iHomeFragment;
        this.context = context;
        getEnvironmentInfo();
    }

    @Override
    public void getEnvironmentInfo() {

    }
}
