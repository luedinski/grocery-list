package org.luedinski.grocery;

public class EntityCreationException extends RuntimeException {
    public EntityCreationException(String id, Exception cause) {
        super("Entity with id '" + id + "' could not be created", cause);
    }

    public EntityCreationException(String id) {
        super("Entity with id '" + id + "' could not be created");
    }

}
