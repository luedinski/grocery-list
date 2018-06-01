package org.luedinski.grocery.service;

import java.sql.SQLException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.luedinski.grocery.model.utils.SQLOperation;

public class AbstractModelService<M, I> {

    protected final Dao<M, I> dao;
    private final Class<M> daoClazz;

    AbstractModelService(Dao<M, I> dao, Class<M> daoClazz) {
        this.dao = dao;
        this.daoClazz = daoClazz;
    }

    @PostConstruct
    public void initTable() {
        ConnectionSource connectionSource = dao.getConnectionSource();
        try {
            TableUtils.createTableIfNotExists(connectionSource, daoClazz);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Optional<M> getById(I id) {
        return Optional.ofNullable(operate(() -> dao.queryForId(id)));
    }

    <R> R operate(SQLOperation<R> operation) {
        try {
            return operation.apply();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    int create(M value) {
        return operate(() -> dao.create(value));
    }
}
