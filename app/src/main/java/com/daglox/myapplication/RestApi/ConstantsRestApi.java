package com.daglox.myapplication.RestApi;

public final class ConstantsRestApi {
    public static final String ROOT_URL = "https://apim-rover-dev-pe.azure-api.net/FUNCTION-ROVER-FA-ISABEL-DEV-PE/";
    public static final String ACCESS_TOKEN = "471d2acb790f4f55bab1c87ec87768bd";

    public static final String KEY_API_INFO_ENVIRONMENT = "measurements/fundo-santa-isabel/meteorologia/last";
    public static final String KEY_API_PLOT_WEEKLY_ENVIRONMENT = "measurements/fundo-santa-isabel/meteorologia/7";
    public static final String KEY_API_PLOT_CURRENT_ENVIRONMENT = "measurements/fundo-santa-isabel/meteorologia/4";
    public static final String URL_API_INFO_ENVIRONMENT = ROOT_URL + KEY_API_INFO_ENVIRONMENT;
    public static final String URL_API_PLOT_WEEKLY_ENVIRONMENT = ROOT_URL + KEY_API_PLOT_WEEKLY_ENVIRONMENT;
    public static final String URL_API_PLOT_CURRENT_ENVIRONMENT = ROOT_URL + KEY_API_PLOT_CURRENT_ENVIRONMENT;
}
