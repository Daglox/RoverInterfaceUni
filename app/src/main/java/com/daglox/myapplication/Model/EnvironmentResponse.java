package com.daglox.myapplication.Model;

import com.daglox.myapplication.POJO.EnvironmentItem;

import java.util.ArrayList;

public class EnvironmentResponse {
    private ArrayList<EnvironmentItem> environmentItems;

    public ArrayList<EnvironmentItem> getEnvironmentItems() {
        return environmentItems;
    }

    public void setEnvironmentItems(ArrayList<EnvironmentItem> environmentItems) {
        this.environmentItems = environmentItems;
    }
}
