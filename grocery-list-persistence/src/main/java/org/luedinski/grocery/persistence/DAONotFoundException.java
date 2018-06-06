package org.luedinski.grocery.persistence;

public class DAONotFoundException extends RuntimeException {

    public DAONotFoundException(int id, String modelType) {
        super(modelType + " with id'" + id + "' not found.");
    }
}
