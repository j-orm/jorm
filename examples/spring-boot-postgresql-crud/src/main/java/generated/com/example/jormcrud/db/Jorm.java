package com.example.jormcrud.db;

import dev.jorm.db.ConnectionManager;
import dev.jorm.db.QueryExecutor;
import org.springframework.stereotype.Repository;

@Repository
public final class Jorm {
    private final QueryExecutor executor;

    public Jorm(ConnectionManager connectionManager) {
        this.executor = new QueryExecutor(connectionManager);
    }

    public AppUserClient appUser() {
        return new AppUserClient(executor);
    }
}
