package dev.jorm.db.adapters;

public interface DatabaseAdapter {
    String getDriverClassName();
    String getJdbcUrlPrefix();
    String buildPaginationClause(int limit, int offset);
}
