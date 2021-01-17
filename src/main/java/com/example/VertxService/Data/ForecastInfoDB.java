/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.VertxService.Data;

import java.sql.*;
import java.util.logging.*;

import com.example.VertxService.Model.ForecastInfo;

public class ForecastInfoDB extends ForecastInfo {
    
    private ForecastInfoDB(String userId, String forecastData) {
        super(userId, forecastData);
    }
    
    public static String getData(String userId) {
        String query = "SELECT data FROM forecast WHERE userId = ?";
        String fetchedInfo = null;
        try (PreparedStatement preStmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            preStmt.setString(1, userId);
            ResultSet rs = preStmt.executeQuery();
            if (rs != null) {
               rs.next();
                fetchedInfo = rs.getString("data");
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForecastInfoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fetchedInfo;
    }

    public static boolean insertData(ForecastInfo forecastInfo) throws SQLException {
        String query = "INSERT INTO forecast VALUES (?, ?)";
        try (PreparedStatement preStmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            preStmt.setString(1, forecastInfo.getUserId());
            preStmt.setString(2, forecastInfo.getForecastData());
            preStmt.executeUpdate();
        }
        return true;
    }
    
}
