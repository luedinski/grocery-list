package org.luedinski.grocery;

public class ModelNotFoundException extends RuntimeException {

    public ModelNotFoundException(int id, String modelType) {
        super(modelType + " with id'" + id + "' not found.");
    }
}
