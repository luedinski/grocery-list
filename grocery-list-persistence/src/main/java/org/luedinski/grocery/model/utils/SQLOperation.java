package org.luedinski.grocery.model.utils;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLOperation<R> {

    public R apply() throws SQLException;
}