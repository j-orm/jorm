package dev.jorm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {

    private final ConnectionManager connectionManager;

    public QueryExecutor(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    public <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();
        
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar query: " + sql, e);
        }
        
        return results;
    }

    public int executeUpdate(String sql, Object... params) {
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update: " + sql, e);
        }
    }
}
