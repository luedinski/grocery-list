package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

@FunctionalInterface
public interface TableFactory {

    void initTable(ConnectionSource connectionSource, Class<?> daoClass) throws SQLException;
}
