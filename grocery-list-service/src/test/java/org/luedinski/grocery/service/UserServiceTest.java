package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.model.User;
import org.luedinski.grocery.service.utils.PasswordCrypter;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private PasswordCrypter passwordCrypter;

    @Mock
    private Dao<User, String> dao;

    @InjectMocks
    private UserService subject;

    @Test
    public void testCreate() throws Exception {
        when(passwordCrypter.crypt(anyString())).thenAnswer(inv -> new StringBuilder(inv.getArgumentAt(0, String.class)).reverse().toString());
        when(dao.queryForEq("name", "name")).thenReturn(Collections.emptyList(), Collections.singletonList(new User("name", "pw1")));
        when(dao.create(any(User.class))).thenReturn(1);
        User user = subject.create("name", "pw");

        Assertions.assertThat(user).extracting(User::getName, User::getPassword).containsExactly("name", "wp");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(dao).create(same(user));
    }

    @Test
    public  void testCreate_IdInUse() throws Exception {
        when(dao.queryForEq("name", "name")).thenReturn(Collections.singletonList(new User("name", "pw1")));
        Assertions.assertThatExceptionOfType(UserNameExistsException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withMessage("User with name 'name' already exists.");
    }

    @Test
    public  void testCreate_notAdded() throws Exception {
        when(dao.queryForEq("name", "name")).thenReturn(Collections.emptyList());
        when(dao.create(any(User.class))).thenThrow(new SQLException("error"));
        when(passwordCrypter.crypt(anyString())).thenAnswer(inv -> new StringBuilder(inv.getArgumentAt(0, String.class)).reverse().toString());
        Assertions.assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

}