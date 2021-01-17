package com.example.VertxService.Model;

public class ForecastInfo {
    private String userId;
    private String forecastData;

    public ForecastInfo(String userId, String forecastData) {
        this.userId = userId;
        this.forecastData = forecastData;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForecastData() {
        return this.forecastData;
    }

    public void setForecastData(String forecastData) {
        this.forecastData = forecastData;
    }

    @Override
    public String toString() {
        return "{" + " userId='" + getUserId() + "'" + ", forecastData='" + getForecastData() + "'" + "}";
    }

}