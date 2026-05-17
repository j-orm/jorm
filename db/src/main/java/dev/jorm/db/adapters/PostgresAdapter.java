package dev.jorm.db.adapters;

public final class PostgresAdapter implements DatabaseAdapter {
    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getJdbcUrlPrefix() {
        return "jdbc:postgresql://";
    }

    @Override
    public String buildPaginationClause(int limit, int offset) {
        return " LIMIT " + limit + " OFFSET " + offset;
    }
}
