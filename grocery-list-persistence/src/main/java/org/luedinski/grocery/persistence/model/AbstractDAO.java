package org.luedinski.grocery.persistence.model;

import com.j256.ormlite.field.DatabaseField;

public abstract class AbstractDAO {

    AbstractDAO() {

    }

    protected AbstractDAO(String name) {
        this.name = name;
        this.id = null;
    }

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String name;


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
