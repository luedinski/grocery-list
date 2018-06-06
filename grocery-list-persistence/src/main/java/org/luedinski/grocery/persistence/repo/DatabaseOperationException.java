package org.luedinski.grocery.persistence.repo;

public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(Throwable cause) {
        super(cause);
    }
}
