package com.daglox.myapplication.RestApi.Adapter;

import android.os.Environment;

import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.RestApi.ConstantsRestApi;
import com.daglox.myapplication.RestApi.Deserializador.EnvironmentDeserializador;
import com.daglox.myapplication.RestApi.Deserializador.PlotCurrentDeserializador;
import com.daglox.myapplication.RestApi.Deserializador.PlotWeeklyDeserializador;
import com.daglox.myapplication.RestApi.EndPointsAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiAdapter {
    public EndPointsAPI stablishConnectionRestApiAzure(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EndPointsAPI.class);
    }

    public Gson buildGsonDeserializeEnvironmentInfo() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EnvironmentResponse.class, new EnvironmentDeserializador());
        return gsonBuilder.create();
    }

    public Gson buildGsonDeserializePlotCurrent() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EnvironmentResponse.class, new PlotCurrentDeserializador());
        return gsonBuilder.create();
    }

    public Gson buildGsonDeserializePlotWeekly() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EnvironmentResponse.class, new PlotWeeklyDeserializador());
        return gsonBuilder.create();
    }
}
