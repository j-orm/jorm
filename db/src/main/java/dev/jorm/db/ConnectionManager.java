package dev.jorm.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private final String url;
    private final String username;
    private final String password;
    private final DataSource dataSource;

    public ConnectionManager(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.dataSource = null;
    }
    
    public ConnectionManager(String jdbcUrl) {
        this.url = jdbcUrl;
        this.username = null;
        this.password = null;
        this.dataSource = null;
    }

    public ConnectionManager(DataSource dataSource) {
        this.url = null;
        this.username = null;
        this.password = null;
        this.dataSource = dataSource;
    }

    public static ConnectionManager fromEnv() {
        String dbUrl = System.getenv("DATABASE_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new RuntimeException("DATABASE_URL environment variable is not set.");
        }
        
        // Very basic conversion from postgresql:// to jdbc:postgresql:// if needed
        if (dbUrl.startsWith("postgresql://") || dbUrl.startsWith("mysql://")) {
            dbUrl = "jdbc:" + dbUrl;
        }
        
        return new ConnectionManager(dbUrl);
    }

    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        if (username != null && password != null) {
            return DriverManager.getConnection(url, username, password);
        }
        return DriverManager.getConnection(url);
    }
}
