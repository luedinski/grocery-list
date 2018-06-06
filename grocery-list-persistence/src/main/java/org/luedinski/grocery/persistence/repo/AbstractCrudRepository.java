package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import org.luedinski.grocery.persistence.DAONotFoundException;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

abstract class AbstractCrudRepository<M> {

    protected final Dao<M, Integer> dao;
    private final Class<M> daoClazz;
    private final TableFactory tableFactory;

    AbstractCrudRepository(Dao<M, Integer> dao, TableFactory tableFactory, Class<M> daoClazz) {
        this.dao = dao;
        this.tableFactory = tableFactory;
        this.daoClazz = daoClazz;
    }

    @PostConstruct
    public void initTable() {
        ConnectionSource connectionSource = dao.getConnectionSource();
        try {
            tableFactory.initTable(connectionSource, daoClazz);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    M getById(Integer id) {
        M dao = operate(() -> this.dao.queryForId(id));
        if (dao == null) {
            throw new DAONotFoundException(id, daoClazz.getSimpleName());
        }
        return dao;
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

    void save(M model) {
        operate(() -> dao.update(model));
    }

    void refresh(M model) {
        operate(() -> dao.refresh(model));
    }

    int convertId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id '" + id + "' is not a number", e);
        }
    }

}
