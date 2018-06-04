package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.model.User;
import org.luedinski.grocery.service.utils.PasswordCrypter;

public class UserService extends AbstractDAOService<User> {

    private final PasswordCrypter passwordCrypter;

    public UserService(Dao<User, Integer> dao, PasswordCrypter passwordCrypter) {
        super(dao, User.class);
        this.passwordCrypter = passwordCrypter;
    }

    public User create(String name, String plainTextPassword) {
        if (existsByName(name)) {
            throw new UserNameExistsException(name);
        }
        String password = passwordCrypter.crypt(plainTextPassword);
        User user = new User(name, password);
        create(user);
        return user;
    }

    private boolean existsByName(String name) {
        return operate(() -> !dao.queryForEq("name", name).isEmpty());
    }

}
