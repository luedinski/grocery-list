package org.luedinski.grocery.service;

/**
 * Thrown if creation of a model fails due to an existing id.
 */
public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException(String name) {
        super("User with name '" + name + "' already exists.");
    }
}
