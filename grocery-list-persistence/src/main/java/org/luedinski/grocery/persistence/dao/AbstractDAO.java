package org.luedinski.grocery.persistence.dao;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public abstract class AbstractDAO {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(version = true, dataType = DataType.DATE_LONG)
    private Date lastModified;

    AbstractDAO() {

    }

    protected AbstractDAO(String name) {
        this.name = name;
        this.id = null;
        this.lastModified = null;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }
}
