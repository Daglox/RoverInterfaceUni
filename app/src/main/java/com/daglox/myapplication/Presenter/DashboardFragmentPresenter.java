package com.daglox.myapplication.Presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.daglox.myapplication.Fragments.DashboardFragment;
import com.daglox.myapplication.Fragments.IHomeFragment;
import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.RestApi.Adapter.RestApiAdapter;
import com.daglox.myapplication.RestApi.EndPointsAPI;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragmentPresenter implements IDashboardFragmentPresenter{

    String array;
    private DashboardFragment dashboardFragment;
    private Context context;
    private ArrayList<EnvironmentItem> environmentItems;

    public DashboardFragmentPresenter(DashboardFragment dashboardFragment, Context context){
        this.dashboardFragment = dashboardFragment;
        this.context = context;
        array="Temperature";
    }

    @Override
    public void selectedValue(String selection,int check) {
        if(selection.equals("Environment Temperature")){
            array="Temperature";
        }
        else if(selection.equals("Environment Humidity")){
            array="Humidity";
        }
        if (check==1)
            getWeeklyArray();
        else if(check==0)
            getCurrentArray();
    }

    @Override
    public void getCurrentArray() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonEnvironmentInfo = restApiAdapter.buildGsonDeserializePlotCurrent();
        EndPointsAPI endPointsAPI = restApiAdapter.stablishConnectionRestApiAzure(gsonEnvironmentInfo);
        Call<EnvironmentResponse> environmentResponseCall = endPointsAPI.plotCurrentEnvironment();
        environmentResponseCall.enqueue(new Callback<EnvironmentResponse>() {
            @Override
            public void onResponse(Call<EnvironmentResponse> call, Response<EnvironmentResponse> response) {
                EnvironmentResponse environmentResponse = response.body();
                environmentItems = environmentResponse.getEnvironmentItems();
                dashboardFragment.PlotEnvironmentInfo(environmentItems,array);

            }

            @Override
            public void onFailure(Call<EnvironmentResponse> call, Throwable t) {
                Toast.makeText(context, "Falló la conexión con el servidor", Toast.LENGTH_LONG).show();
                Log.e("Connection failed", t.toString());
            }
        });
    }

    @Override
    public void getWeeklyArray() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonEnvironmentInfo = restApiAdapter.buildGsonDeserializePlotWeekly();
        EndPointsAPI endPointsAPI = restApiAdapter.stablishConnectionRestApiAzure(gsonEnvironmentInfo);
        Call<EnvironmentResponse> environmentResponseCall = endPointsAPI.plotWeeklyEnvironment();
        environmentResponseCall.enqueue(new Callback<EnvironmentResponse>() {
            @Override
            public void onResponse(Call<EnvironmentResponse> call, Response<EnvironmentResponse> response) {
                EnvironmentResponse environmentResponse = response.body();
                environmentItems = environmentResponse.getEnvironmentItems();
                dashboardFragment.PlotEnvironmentInfo(environmentItems,array);

            }

            @Override
            public void onFailure(Call<EnvironmentResponse> call, Throwable t) {
                Toast.makeText(context, "Falló la conexión con el servidor", Toast.LENGTH_LONG).show();
                Log.e("Connection failed", t.toString());
            }
        });
    }
}
