package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.DatabaseOperationException;
import org.luedinski.grocery.IdentifierInUseException;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.model.utils.PasswordCrypter;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordCrypter passwordCrypter;

    @Mock
    private Dao<User, String> dao;

    @InjectMocks
    private UserService subject;

    @Test
    void testCreate() throws Exception {
        when(passwordCrypter.crypt(anyString())).thenAnswer(inv -> new StringBuilder(inv.getArgument(0)).reverse().toString());
        when(dao.idExists("name")).thenReturn(false, true);
        when(dao.create(any(User.class))).thenReturn(1);
        User user = subject.create("name", "pw");

        Assertions.assertThat(user).extracting(User::getId, User::getPassword).containsExactly("name", "wp");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao).create(same(user));
    }

    @Test
    void testCreate_IdInUse() throws Exception {
        when(dao.idExists("name")).thenReturn(true);
        Assertions.assertThatExceptionOfType(IdentifierInUseException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withMessage("Element with id 'name' already exists.");
    }

    @Test
    void testCreate_notAdded() throws Exception {
        when(dao.idExists("name")).thenReturn(false);
        when(dao.create(any(User.class))).thenThrow(new SQLException("error"));
        when(passwordCrypter.crypt(anyString())).thenAnswer(inv -> new StringBuilder(inv.getArgument(0)).reverse().toString());
        Assertions.assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

}