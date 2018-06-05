package org.luedinski.grocery.model;

public class Category extends BaseModel {

    Category() {
        // for serialization
    }

    public Category(String id, String name, String version) {
        super(id, name, version);
    }
}
