package com.daglox.myapplication.RestApi;

import com.daglox.myapplication.Model.EnvironmentResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EndPointsAPI {
    @GET(ConstantsRestApi.URL_API_INFO_ENVIRONMENT)
    Call<EnvironmentResponse> getRecentEnvironment(@Header("Apim-Rover-Key") String auth);
}
