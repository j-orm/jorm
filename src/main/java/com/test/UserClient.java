package com.test;

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

public final class UserClient {
    private final QueryExecutor executor;

    public UserClient(QueryExecutor executor) {
        this.executor = executor;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        Integer _id = rs.getInt("id");
        String _name = rs.getString("name");
        String _email = rs.getString("email");
        return new User(_id, _name, _email);
    }

    public List<User> findMany() {
        String sql = "SELECT * FROM User";
        return executor.executeQuery(sql, this::mapRow);
    }

    public List<User> findMany(Consumer<WhereBuilder> consumer) {
        WhereBuilder builder = new WhereBuilder();
        consumer.accept(builder);
        String sql = "SELECT * FROM User WHERE " + String.join(" AND ", builder.conditions);
        return executor.executeQuery(sql, this::mapRow, builder.parameters.toArray());
    }

    private User withIncludes(User result, IncludeBuilder includeBuilder) {
        Integer _id = result.id();
        String _name = result.name();
        String _email = result.email();
        return new User(_id, _name, _email);
    }

    public List<User> findMany(Consumer<WhereBuilder> whereConsumer,
            Consumer<IncludeBuilder> includeConsumer) {
        IncludeBuilder includeBuilder = new IncludeBuilder();
        includeConsumer.accept(includeBuilder);
        List<User> results = findMany(whereConsumer);
        if (!results.isEmpty() && !includeBuilder.includes.isEmpty()) {
            return results.stream().map(r -> withIncludes(r, includeBuilder)).collect(Collectors.toList());
        }
        return results;
    }

    public User create(Consumer<DataBuilder> consumer) {
        DataBuilder builder = new DataBuilder();
        consumer.accept(builder);
        String cols = String.join(", ", builder.columns);
        String placeholders = builder.columns.stream().map(c -> "?").collect(Collectors.joining(", "));
        String sql = "INSERT INTO User (" + cols + ") VALUES (" + placeholders + ")";
        executor.executeUpdate(sql, builder.values.toArray());
        return null;
    }

    public User update(Integer id, Consumer<DataBuilder> consumer) {
        DataBuilder builder = new DataBuilder();
        consumer.accept(builder);
        String setClause = builder.columns.stream().map(c -> c + " = ?").collect(Collectors.joining(", "));
        String sql = "UPDATE User SET " + setClause + " WHERE id = ?";
        List<Object> params = new ArrayList<>(builder.values);
        params.add(id);
        executor.executeUpdate(sql, params.toArray());
        return findById(id);
    }

    public User findById(Integer id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        List<User> results = executor.executeQuery(sql, this::mapRow, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public User findById(Integer id, Consumer<IncludeBuilder> includeConsumer) {
        IncludeBuilder includeBuilder = new IncludeBuilder();
        includeConsumer.accept(includeBuilder);
        User result = findById(id);
        if (result != null && !includeBuilder.includes.isEmpty()) {
            return withIncludes(result, includeBuilder);
        }
        return result;
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM User WHERE id = ?";
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

        public StringFilter name() {
            return new StringFilter(this, "name");
        }

        public StringFilter email() {
            return new StringFilter(this, "email");
        }
    }

    public static final class DataBuilder {
        public final List<String> columns = new ArrayList<>();

        public final List<Object> values = new ArrayList<>();

        public DataBuilder name(String value) {
            this.columns.add("name");
            this.values.add(value);
            return this;
        }

        public DataBuilder email(String value) {
            this.columns.add("email");
            this.values.add(value);
            return this;
        }
    }

    public static final class IncludeBuilder {
        public final List<String> includes = new ArrayList<>();
    }
}
