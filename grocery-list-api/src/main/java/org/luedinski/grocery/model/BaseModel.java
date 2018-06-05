package org.luedinski.grocery.model;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

abstract class BaseModel implements Serializable {

    private final String id;
    private final String name;
    private final String version;

    BaseModel() {
        // for serialization
        id = null;
        name = null;
        version = null;
    }

    BaseModel(String id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("version", version).toString();
    }
}
