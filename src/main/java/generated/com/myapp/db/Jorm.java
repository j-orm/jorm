package com.myapp.db;

import dev.jorm.db.ConnectionManager;
import dev.jorm.db.QueryExecutor;

public final class Jorm {
    private final QueryExecutor executor;

    public Jorm(ConnectionManager connectionManager) {
        this.executor = new QueryExecutor(connectionManager);
    }

    public UserClient user() {
        return new UserClient(executor);
    }
}
