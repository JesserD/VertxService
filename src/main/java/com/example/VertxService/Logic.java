package com.example.VertxService;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.VertxService.Data.ForecastInfoDB;
import com.example.VertxService.Model.ForecastElement;
import com.example.VertxService.Model.ForecastInfo;
import com.example.VertxService.Model.ForecastParser;
import com.google.gson.Gson;
import io.vertx.core.json.JsonObject;

public class Logic {

    static String convertToJsonArray(JsonObject data) {
        ArrayList<ForecastElement> forecastElements = ForecastParser.getListOfForecastElements(data);
        Gson gson = new Gson();
        String returnValue = new String();

        for (int i = 0; i < forecastElements.size(); i++) {
            if (i == forecastElements.size() - 1)
                returnValue += gson.toJson(forecastElements.get(i));
            else
                returnValue += gson.toJson(forecastElements.get(i)) + ",";
        }

        return "[" + returnValue + "]";
    }

    static void saveToDB(String userId, String data) {
        try {
            ForecastInfoDB.insertData(new ForecastInfo(userId, data));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String getFromDB(String userId) {
        return ForecastInfoDB.getData(userId);
    }

}