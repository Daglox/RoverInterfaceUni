package com.daglox.myapplication.Fragments;


import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;

import java.util.ArrayList;

public interface IDashboardFragment {
    public void PlotEnvironmentInfo(ArrayList<EnvironmentItem> environmentItems,String selection);
}
