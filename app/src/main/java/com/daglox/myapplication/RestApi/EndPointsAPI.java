package com.daglox.myapplication.RestApi;

import com.daglox.myapplication.Model.EnvironmentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface EndPointsAPI {
    @Headers("Apim-Rover-Key: " + ConstantsRestApi.ACCESS_TOKEN)
    @GET(ConstantsRestApi.URL_API_INFO_ENVIRONMENT)
    Call<EnvironmentResponse> getRecentEnvironment();

    @Headers("Apim-Rover-Key: " + ConstantsRestApi.ACCESS_TOKEN)
    @GET(ConstantsRestApi.URL_API_PLOT_WEEKLY_ENVIRONMENT)
    Call<EnvironmentResponse> plotWeeklyEnvironment();

    @Headers("Apim-Rover-Key: " + ConstantsRestApi.ACCESS_TOKEN)
    @GET(ConstantsRestApi.URL_API_PLOT_CURRENT_ENVIRONMENT)
    Call<EnvironmentResponse> plotCurrentEnvironment();

}