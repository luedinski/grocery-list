package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.luedinski.grocery.service.utils.PasswordCrypter;

public class UserService extends AbstractDAOService<UserDAO> {

    private final PasswordCrypter passwordCrypter;

    public UserService(Dao<UserDAO, Integer> dao, TableFactory tableFactory, PasswordCrypter passwordCrypter) {
        super(dao, tableFactory, UserDAO.class);
        this.passwordCrypter = passwordCrypter;
    }

    public UserDAO create(String name, String plainTextPassword) {
        if (existsByName(name)) {
            throw new UserNameExistsException(name);
        }
        String password = passwordCrypter.crypt(plainTextPassword);
        UserDAO userDAO = new UserDAO(name, password);
        create(userDAO);
        return userDAO;
    }

    private boolean existsByName(String name) {
        return operate(() -> !dao.queryForEq("name", name).isEmpty());
    }

}
