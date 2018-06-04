package org.luedinski.grocery.service;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLOperation<R> {

    public R apply() throws SQLException;
}