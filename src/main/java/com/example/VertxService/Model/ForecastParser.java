package com.example.VertxService.Model;

import io.vertx.core.json.*;

import java.util.ArrayList;

public class ForecastParser {

    public static ForecastElement getForecastElement(JsonObject jsonObj) {
        String validTime = jsonObj.getString("validTime");
        double temperature = -1000;
        JsonArray parameters = jsonObj.getJsonArray("parameters");
        for (int i = 0; i < parameters.size(); i++) {
            JsonObject objOfParameters = parameters.getJsonObject(i);
            if (objOfParameters.getString("name").equals("t"))
                temperature = objOfParameters.getJsonArray("values").getDouble(0);
        }
        return new ForecastElement(validTime, temperature);
    }

    public static ArrayList<ForecastElement> getListOfForecastElements(JsonObject jsonObj) {
        ArrayList<ForecastElement> ForecastElements = new ArrayList<>();
        JsonArray timeSeries = jsonObj.getJsonArray("timeSeries");
        for (int i = 0; i < timeSeries.size(); i++) {
            JsonObject objOfTimeSeries = timeSeries.getJsonObject(i);
            ForecastElements.add(getForecastElement(objOfTimeSeries));
        }
        return ForecastElements;
    }

}
