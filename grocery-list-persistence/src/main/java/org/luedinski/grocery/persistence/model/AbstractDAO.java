package org.luedinski.grocery.persistence.model;


import java.util.Date;

import com.j256.ormlite.field.DataType;
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
    @DatabaseField(version = true, dataType = DataType.DATE_LONG)
    private Date lastModified;


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
