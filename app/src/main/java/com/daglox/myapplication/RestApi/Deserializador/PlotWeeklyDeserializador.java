package com.daglox.myapplication.RestApi.Deserializador;

import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.RestApi.JsonKeys;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlotWeeklyDeserializador implements JsonDeserializer<EnvironmentResponse> {

    @Override
    public EnvironmentResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        EnvironmentResponse environmentResponse = gson.fromJson(json, EnvironmentResponse.class);
        JsonArray environmentResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.RESPONSE_ARRAY_ENVIRONMENT);
        environmentResponse.setEnvironmentItems(deserializeEnvironmentJson(environmentResponseData));
        return environmentResponse;
    }

    private ArrayList<EnvironmentItem> deserializeEnvironmentJson(JsonArray environmentResponseData) {
        ArrayList<EnvironmentItem> environmentItems = new ArrayList<>();
        for (int i = 0; i<environmentResponseData.size(); i++) {

            JsonObject environmentResponseDataObejct = environmentResponseData.get(i).getAsJsonObject();
            Float temperature = environmentResponseDataObejct.get(JsonKeys.INFO_TEMPERATURE).getAsFloat();
            Float humidity = environmentResponseDataObejct.get(JsonKeys.INFO_HUMIDITY).getAsFloat();
            String datetime = environmentResponseDataObejct.get(JsonKeys.DATE_STAMP).getAsString();

            EnvironmentItem currentEnvironmentItem = new EnvironmentItem();
            currentEnvironmentItem.setTemperature(temperature);
            currentEnvironmentItem.setHumidity(humidity);
            currentEnvironmentItem.setDatetime(datetime);

            environmentItems.add(currentEnvironmentItem);

        }

        return environmentItems;
    }
}
