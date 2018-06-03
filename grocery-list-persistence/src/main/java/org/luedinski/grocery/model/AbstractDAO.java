package org.luedinski.grocery.model;

import com.j256.ormlite.field.DatabaseField;

abstract class AbstractDAO {

    @DatabaseField(generatedId = true)
    private int id;

    AbstractDAO() {

    }

    public int getId() {
        return id;
    }
}
