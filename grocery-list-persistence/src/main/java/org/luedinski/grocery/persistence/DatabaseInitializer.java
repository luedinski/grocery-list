package org.luedinski.grocery.persistence;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseInitializer {

    private final List<Class<?>> dataClasses;
    private final ConnectionSource connectionSource;

    public DatabaseInitializer(ConnectionSource connectionSource, Class<?> ... dataClasses) {
        this.dataClasses = Arrays.asList(dataClasses);
        this.connectionSource = connectionSource;
    }

    @PostConstruct
    public void init() {
        dataClasses.forEach(clazz -> {
            try {
                TableUtils.createTableIfNotExists(connectionSource, clazz);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }

        });
    }
}
