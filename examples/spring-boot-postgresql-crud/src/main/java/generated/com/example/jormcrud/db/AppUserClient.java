package com.example.jormcrud.db;

import dev.jorm.db.QueryExecutor;

import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class AppUserClient {
    private final QueryExecutor executor;

    public AppUserClient(QueryExecutor executor) {
        this.executor = executor;
    }

    private AppUser mapRow(ResultSet rs) throws SQLException {
        Integer _id = rs.getInt("id");
        String _email = rs.getString("email");
        String _name = rs.getString("name");
        return new AppUser(_id, _email, _name);
    }

    public List<AppUser> findMany() {
        String sql = "SELECT * FROM AppUser";
        return executor.executeQuery(sql, this::mapRow);
    }

    public List<AppUser> findMany(Consumer<WhereBuilder> consumer) {
        WhereBuilder builder = new WhereBuilder();
        consumer.accept(builder);
        String sql = "SELECT * FROM AppUser WHERE " + String.join(" AND ", builder.conditions);
        return executor.executeQuery(sql, this::mapRow, builder.parameters.toArray());
    }

    public AppUser findById(Integer id) {
        String sql = "SELECT * FROM AppUser WHERE id = ?";
        List<AppUser> results = executor.executeQuery(sql, this::mapRow, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public AppUser create(Consumer<DataBuilder> consumer) {
        DataBuilder builder = new DataBuilder();
        consumer.accept(builder);
        String cols = String.join(", ", builder.columns);
        String placeholders = builder.columns.stream().map(c -> "?").collect(Collectors.joining(", "));
        String sql = "INSERT INTO AppUser (" + cols + ") VALUES (" + placeholders + ")";
        executor.executeUpdate(sql, builder.values.toArray());
        return null;
    }

    public AppUser update(Integer id, Consumer<DataBuilder> consumer) {
        DataBuilder builder = new DataBuilder();
        consumer.accept(builder);
        String setClause = builder.columns.stream().map(c -> c + " = ?").collect(Collectors.joining(", "));
        String sql = "UPDATE AppUser SET " + setClause + " WHERE id = ?";
        List<Object> params = new ArrayList<>(builder.values);
        params.add(id);
        executor.executeUpdate(sql, params.toArray());
        return findById(id);
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM AppUser WHERE id = ?";
        executor.executeUpdate(sql, id);
    }

    public static final class StringFilter {
        private final WhereBuilder parent;
        private final String column;

        public StringFilter(WhereBuilder parent, String column) {
            this.parent = parent;
            this.column = column;
        }

        public WhereBuilder equals(String value) {
            parent.conditions.add(column + " = ?");
            parent.parameters.add(value);
            return parent;
        }

        public WhereBuilder contains(String value) {
            parent.conditions.add(column + " LIKE ?");
            parent.parameters.add("%" + value + "%");
            return parent;
        }

        public WhereBuilder startsWith(String value) {
            parent.conditions.add(column + " LIKE ?");
            parent.parameters.add(value + "%");
            return parent;
        }
    }

    public static final class IntFilter {
        private final WhereBuilder parent;
        private final String column;

        public IntFilter(WhereBuilder parent, String column) {
            this.parent = parent;
            this.column = column;
        }

        public WhereBuilder equals(Integer value) {
            parent.conditions.add(column + " = ?");
            parent.parameters.add(value);
            return parent;
        }

        public WhereBuilder gt(Integer value) {
            parent.conditions.add(column + " > ?");
            parent.parameters.add(value);
            return parent;
        }

        public WhereBuilder lt(Integer value) {
            parent.conditions.add(column + " < ?");
            parent.parameters.add(value);
            return parent;
        }
    }

    public static final class WhereBuilder {
        public final List<String> conditions = new ArrayList<>();
        public final List<Object> parameters = new ArrayList<>();

        public IntFilter id() {
            return new IntFilter(this, "id");
        }

        public StringFilter email() {
            return new StringFilter(this, "email");
        }

        public StringFilter name() {
            return new StringFilter(this, "name");
        }
    }

    public static final class DataBuilder {
        public final List<String> columns = new ArrayList<>();
        public final List<Object> values = new ArrayList<>();

        public DataBuilder email(String value) {
            this.columns.add("email");
            this.values.add(value);
            return this;
        }

        public DataBuilder name(String value) {
            this.columns.add("name");
            this.values.add(value);
            return this;
        }
    }
}
