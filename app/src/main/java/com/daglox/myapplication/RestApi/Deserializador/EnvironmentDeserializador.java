package com.daglox.myapplication.RestApi.Deserializador;

import com.daglox.myapplication.Model.EnvironmentResponse;
import com.daglox.myapplication.POJO.EnvironmentItem;
import com.daglox.myapplication.RestApi.JsonKeys;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class EnvironmentDeserializador implements JsonDeserializer<EnvironmentResponse>{

    @Override
    public EnvironmentResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        EnvironmentResponse environmentResponse = gson.fromJson(json, EnvironmentResponse.class);
        JsonObject jsonObject = json.getAsJsonObject().getAsJsonObject();
        String temperature = jsonObject.get(JsonKeys.INFO_TEMPERATURE).getAsString();
        String humidity = jsonObject.get(JsonKeys.INFO_HUMIDITY).getAsString();

        EnvironmentItem recentEnvironmentItem = new EnvironmentItem(temperature, humidity);
        environmentResponse.setEnvironmentItem(recentEnvironmentItem);

        return environmentResponse;
    }
}
