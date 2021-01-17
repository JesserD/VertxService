package com.example.VertxService.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String userName = "WebForumBackend";
    private static final String password = "";
    private static final String server = "jdbc:mysql://host.docker.internal:3306/VertxDB";
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = CreateNewConnection();
        }

        return conn;
    }

    private static Connection CreateNewConnection() throws SQLException {
        try {
            conn = DriverManager.getConnection(server, userName, password);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return conn;
    }
    
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
