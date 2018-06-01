package org.luedinski.grocery;

/**
 * Thrown if creation of a model fails due to an existing id.
 */
public class IdentifierInUseException extends RuntimeException {

    public IdentifierInUseException(String id) {
        super("Element with id " + id + " already exists.");
    }
}
