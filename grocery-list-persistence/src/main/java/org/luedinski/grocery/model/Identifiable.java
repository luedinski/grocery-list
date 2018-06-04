package org.luedinski.grocery.model;

import com.j256.ormlite.field.DatabaseField;

public abstract class Identifiable {

    Identifiable() {

    }

    @DatabaseField(generatedId = true)
    private int id;

    public int getId() {
        return id;
    }
}
