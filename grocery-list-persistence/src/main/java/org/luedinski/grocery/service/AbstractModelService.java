package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.luedinski.grocery.DatabaseOperationException;
import org.luedinski.grocery.model.utils.SQLOperation;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Optional;

abstract class AbstractModelService<M, I> {

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
            throw new IllegalStateException(e);
        }

    }

    public Optional<M> getById(I id) {
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

    boolean exists(I id) {
        return operate(() -> dao.idExists(id));
    }

}
