package org.luedinski.grocery.persistence;

/**
 * Thrown if creation of a dao fails due to an existing id.
 */
public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException(String name) {
        super("UserDAO with name '" + name + "' already exists.");
    }
}
