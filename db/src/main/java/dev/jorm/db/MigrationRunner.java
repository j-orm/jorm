package dev.jorm.db;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MigrationRunner {

    private final ConnectionManager connectionManager;

    public MigrationRunner(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void runMigrations(File migrationsDir) {
        if (!migrationsDir.exists() || !migrationsDir.isDirectory()) {
            System.out.println("No migrations directory found.");
            return;
        }

        try (Connection conn = connectionManager.getConnection()) {
            ensureMigrationTableExists(conn);

            List<String> appliedMigrations = getAppliedMigrations(conn);

            File[] files = migrationsDir.listFiles((dir, name) -> name.endsWith(".sql"));
            if (files == null || files.length == 0) {
                System.out.println("No migrations to apply.");
                return;
            }

            // Sort by name (timestamp)
            Arrays.sort(files, (f1, f2) -> f1.getName().compareTo(f2.getName()));

            int count = 0;
            for (File file : files) {
                String migrationName = file.getName();
                if (!appliedMigrations.contains(migrationName)) {
                    applyMigration(conn, file);
                    count++;
                }
            }

            if (count > 0) {
                System.out.println(count + " migration(s) applied successfully.");
            } else {
                System.out.println("Database is already up to date.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error executing migrations", e);
        }
    }

    private void ensureMigrationTableExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS _jorm_migrations (" +
                     "id SERIAL PRIMARY KEY, " +
                     "name VARCHAR(255) UNIQUE NOT NULL, " +
                     "applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public List<String> getAppliedMigrationsPublic() throws SQLException {
        try (Connection conn = connectionManager.getConnection()) {
            ensureMigrationTableExists(conn);
            return getAppliedMigrations(conn);
        }
    }

    private List<String> getAppliedMigrations(Connection conn) throws SQLException {
        List<String> migrations = new ArrayList<>();
        String sql = "SELECT name FROM _jorm_migrations ORDER BY id ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                migrations.add(rs.getString("name"));
            }
        }
        return migrations;
    }

    private void applyMigration(Connection conn, File file) throws Exception {
        System.out.println("Applying migration: " + file.getName());
        String sql = Files.readString(file.toPath());
        
        try {
            conn.setAutoCommit(false);
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
            
            String insertSql = "INSERT INTO _jorm_migrations (name) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, file.getName());
                pstmt.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException("Failed to apply migration " + file.getName() + ". Rollback executed.", e);
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
