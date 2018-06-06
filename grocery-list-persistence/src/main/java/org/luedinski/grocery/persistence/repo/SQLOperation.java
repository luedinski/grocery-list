package org.luedinski.grocery.persistence.repo;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLOperation<R> {

    R apply() throws SQLException;
}