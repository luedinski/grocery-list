package org.luedinski.grocery.model.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PasswordCrypterTest {

    @Test
    public void testCrypt() {
        String crypted = new PasswordCrypter(4).crypt("foo");
        Assertions.assertThat(crypted).isNotBlank().isNotEqualTo("foo");
    }

    @Test
    public void testValidatePassword() {
        PasswordCrypter passwordCrypter = new PasswordCrypter(4);
        String crypted = passwordCrypter.crypt("foo");
        Assertions.assertThat(passwordCrypter.validatePassword("foo", crypted)).isTrue();
    }

    @Test
    public void testValidatePassword_differentPassword() {
        PasswordCrypter passwordCrypter = new PasswordCrypter(4);
        String crypted = passwordCrypter.crypt("foo");
        Assertions.assertThat(passwordCrypter.validatePassword("bar", crypted)).isFalse();
    }

    @Test
    public void testCrypt_doubleTest() {
        PasswordCrypter passwordCrypter = new PasswordCrypter(4);
        String crypted1 = passwordCrypter.crypt("foo");
        String crypted2 = passwordCrypter.crypt("foo");
        Assertions.assertThat(crypted1).isEqualTo(crypted2);
    }
}