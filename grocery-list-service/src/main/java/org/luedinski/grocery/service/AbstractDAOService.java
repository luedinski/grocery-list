package org.luedinski.grocery.service;

import java.sql.SQLException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

abstract class AbstractDAOService<M> {

    protected final Dao<M, Integer> dao;
    private final Class<M> daoClazz;

    AbstractDAOService(Dao<M, Integer> dao, Class<M> daoClazz) {
        this.dao = dao;
        this.daoClazz = daoClazz;
    }

    @PostConstruct
    public void initTable() {
        ConnectionSource connectionSource = dao.getConnectionSource();
        try {
            TableUtils.createTableIfNotExists(connectionSource, daoClazz);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    public Optional<M> getById(Integer id) {
        return Optional.ofNullable(operate(() -> dao.queryForId(id)));
    }

    <R> R operate(SQLOperation<R> operation) {
        try {
            return operation.apply();
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    int create(M value) {
        return operate(() -> dao.create(value));
    }

    boolean exists(Integer id) {
        return operate(() -> dao.idExists(id));
    }

    public void update(M model) {
        operate(() -> dao.update(model));
    }

}
