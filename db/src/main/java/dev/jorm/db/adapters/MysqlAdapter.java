package dev.jorm.db.adapters;

public final class MysqlAdapter implements DatabaseAdapter {
    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getJdbcUrlPrefix() {
        return "jdbc:mysql://";
    }

    @Override
    public String buildPaginationClause(int limit, int offset) {
        return " LIMIT " + limit + " OFFSET " + offset;
    }
}
