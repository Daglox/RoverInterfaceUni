package com.daglox.myapplication.Fragments;

import com.google.android.gms.maps.model.LatLng;

public class MapsClass {
    private LatLng punto;
    private float vlong;
    private float vlat;

    public MapsClass(LatLng pu, float vlo, float vla){
        punto=pu;
        vlong=vlo;
        vlat=vla;
    }

    public LatLng getPunto() {
        return punto;
    }

    public void setPunto(LatLng punto) {
        this.punto = punto;
    }

    public float getVlong() {
        return vlong;
    }

    public void setVlong(float vlong) {
        this.vlong = vlong;
    }

    public float getVlat() {
        return vlat;
    }

    public void setVlat(float vlat) {
        this.vlat = vlat;
    }
}
