package com.daglox.myapplication.POJO;

public class EnvironmentItem {


    private Float temperature;
    private Float humidity;
    private String datetime;

    public EnvironmentItem(){

    }

    public EnvironmentItem(Float temperature, Float humidity, String datetime){
        this.temperature = temperature;
        this.humidity = humidity;
        this.datetime = datetime;
    }


    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public String getDatetime() { return datetime; }

    public void setDatetime(String datetime) { this.datetime = datetime; }
}
