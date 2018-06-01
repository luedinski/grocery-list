package org.luedinski.grocery.model.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordCrypter {

    private final String salt;

    public PasswordCrypter(int saltIterations) {
        salt = BCrypt.gensalt(saltIterations);
    }

    public String crypt(String password) {
        return BCrypt.hashpw(password, salt);
    }

    public boolean validatePassword(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
