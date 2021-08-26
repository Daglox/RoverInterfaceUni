package com.daglox.myapplication.Presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.daglox.myapplication.Fragments.IHomeFragment;
import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.RestApi.Adapter.RestApiAdapter;
import com.daglox.myapplication.RestApi.ConstantsRestApi;
import com.daglox.myapplication.RestApi.EndPointsAPI;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter implements IHomeFragmentPresenter {

    private IHomeFragment iHomeFragment;
    private Context context;
    private ArrayList<EnvironmentItem> environmentItems;
    public HomeFragmentPresenter(IHomeFragment iHomeFragment, Context context){
        this.iHomeFragment = iHomeFragment;
        this.context = context;
    }

    @Override
    public void getEnvironmentInfo() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonEnvironmentInfo = restApiAdapter.buildGsonDeserializeEnvironmentInfo();
        EndPointsAPI endPointsAPI = restApiAdapter.stablishConnectionRestApiAzure(gsonEnvironmentInfo);
        Call<EnvironmentResponse> environmentResponseCall = endPointsAPI.getRecentEnvironment();
        environmentResponseCall.enqueue(new Callback<EnvironmentResponse>() {
            @Override
            public void onResponse(Call<EnvironmentResponse> call, Response<EnvironmentResponse> response) {
                EnvironmentResponse environmentResponse = response.body();
                environmentItems = environmentResponse.getEnvironmentItems();
                iHomeFragment.showEnvironmentInfo(environmentItems.get(0));
            }

            @Override
            public void onFailure(Call<EnvironmentResponse> call, Throwable t) {
                Toast.makeText(context, "Falló la conexión con el servidor", Toast.LENGTH_LONG).show();
                Log.e("Connection failed", t.toString());
            }
        });
    }
}
