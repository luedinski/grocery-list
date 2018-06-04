package org.luedinski.grocery.model;

import javax.naming.Name;

import com.j256.ormlite.field.DatabaseField;

public abstract class Nameable extends Identifiable {

    Nameable() {

    }

    protected Nameable(String name) {
        this.name = name;
    }

    @DatabaseField(canBeNull = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
