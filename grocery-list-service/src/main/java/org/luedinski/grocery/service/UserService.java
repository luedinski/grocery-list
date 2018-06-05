package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.UserNameExistsException;
import org.luedinski.grocery.ModelNotFoundException;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.luedinski.grocery.service.utils.PasswordCrypter;

public class UserService extends AbstractDAOService<UserDAO> {

    private final PasswordCrypter passwordCrypter;

    public UserService(Dao<UserDAO, Integer> dao, TableFactory tableFactory, PasswordCrypter passwordCrypter) {
        super(dao, tableFactory, UserDAO.class);
        this.passwordCrypter = passwordCrypter;
    }

    /**
     * Creates user with the given name and password.
     *
     * @param name The user name
     * @param plainTextPassword The password
     * @return The user id
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     */
    public String create(String name, String plainTextPassword) {
        checkNameExistence(name);
        String password = passwordCrypter.crypt(plainTextPassword);
        UserDAO userDAO = new UserDAO(name, password);
        create(userDAO);
        return String.valueOf(userDAO.getId());
    }

    private void checkNameExistence(String name) {
        if (existsByName(name)) {
            throw new UserNameExistsException(name);
        }
    }

    private boolean existsByName(String name) {
        return operate(() -> !dao.queryForEq("name", name).isEmpty());
    }

    /**
     * Changes the name of the user with the given id.
     *
     * @param id The user id
     * @param newName The new name
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     * @throws ModelNotFoundException thrown if no user exists with the given id.
     */
    public void changeName(String id, String newName) {
        checkNameExistence(newName);
        int userId = convertId(id);
        UserDAO userDAO = getById(userId);
        userDAO.setName(newName);
        save(userDAO);
    }

    /**
     * Changes the password of the user with the given id.
     *
     * @param id The user id
     * @param newPassword The new pasword
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     * @throws ModelNotFoundException thrown if no user exists with the given id.
     */
    public void changePassword(String id, String newPassword) {
        int userId = convertId(id);
        UserDAO userDAO = getById(userId);
        String cryptedPassword = passwordCrypter.crypt(newPassword);
        userDAO.setPassword(cryptedPassword);
        save(userDAO);
    }

}
