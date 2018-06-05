package org.luedinski.grocery.service;

import java.sql.SQLException;

import com.j256.ormlite.support.ConnectionSource;

@FunctionalInterface
public interface TableFactory {

    void initTable(ConnectionSource connectionSource, Class<?> daoClass) throws SQLException;
}
