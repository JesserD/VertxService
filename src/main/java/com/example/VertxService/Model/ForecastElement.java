package com.example.VertxService.Model;

public class ForecastElement {
    private String validTime;
    private double temperature;

    public ForecastElement(String validTime, double temperature) {
        this.validTime = validTime;
        this.temperature = temperature;
    }

    public String getValidTime() {
        return validTime;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return validTime + "\n" + "Temperature: " + temperature +" °C" ;
    }
}
