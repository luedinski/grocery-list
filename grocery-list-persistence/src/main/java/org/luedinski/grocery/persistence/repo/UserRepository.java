package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.DAONotFoundException;
import org.luedinski.grocery.persistence.UserNameExistsException;
import org.luedinski.grocery.persistence.dao.UserDAO;

public class UserRepository extends AbstractCrudRepository<UserDAO> {

    public UserRepository(Dao<UserDAO, Integer> dao, TableFactory tableFactory) {
        super(dao, tableFactory, UserDAO.class);
    }

    /**
     * Creates user with the given name and password.
     *
     * @param name     The user name
     * @param password The password
     * @return The user id
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     */
    public String create(String name, String password) {
        checkNameExistence(name);
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
     * @param id      The user id
     * @param newName The new name
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     * @throws DAONotFoundException    thrown if no user exists with the given id.
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
     * @param id          The user id
     * @param newPassword The new pasword
     * @throws UserNameExistsException thrown if a user with the given name already exists.
     * @throws DAONotFoundException    thrown if no user exists with the given id.
     */
    public void changePassword(String id, String newPassword) {
        int userId = convertId(id);
        UserDAO userDAO = getById(userId);
        userDAO.setPassword(newPassword);
        save(userDAO);
    }

}
