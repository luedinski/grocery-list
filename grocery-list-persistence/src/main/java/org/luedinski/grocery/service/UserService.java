package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.IdentifierInUseException;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.model.utils.PasswordCrypter;

public class UserService extends AbstractModelService<User, String> {

    private final PasswordCrypter passwordCrypter;

    public UserService(Dao<User, String> dao, PasswordCrypter passwordCrypter) {
        super(dao, User.class);
        this.passwordCrypter = passwordCrypter;
    }

    public User create(String name, String plainTextPassword) {
        if (operate(() -> dao.idExists(name))) {
            throw new IdentifierInUseException(name);
        }
        String password = passwordCrypter.crypt(plainTextPassword);
        create(new User(name, password));
        return getById(name).orElseThrow(() -> new RuntimeException("Model not created " + name));
    }

}
