package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.UserNameExistsException;
import org.luedinski.grocery.persistence.dao.UserDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {


    @Mock
    private Dao<UserDAO, String> dao;

    @InjectMocks
    private UserRepository subject;

    @Test
    public void testCreate() throws Exception {
        when(dao.queryForEq("name", "name")).thenReturn(Collections.emptyList(), Collections.singletonList(new UserDAO("name", "pw1")));
        Date currentDate = mock(Date.class);
        when(currentDate.getTime()).thenReturn(321L);
        when(dao.create(any(UserDAO.class))).thenAnswer(inv -> {
            UserDAO userDAO = inv.getArgumentAt(0, UserDAO.class);
            ReflectionTestUtils.setField(userDAO, "id", 123);
            return 1;
        });
        String id = subject.create("name", "pw");

        assertThat(id).isEqualTo("123");
    }

    @Test
    public void testCreate_IdInUse() throws Exception {
        when(dao.queryForEq("name", "name")).thenReturn(Collections.singletonList(new UserDAO("name", "pw1")));
        Assertions.assertThatExceptionOfType(UserNameExistsException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withMessage("UserDAO with name 'name' already exists.");
    }

    @Test
    public void testCreate_notAdded() throws Exception {
        when(dao.queryForEq("name", "name")).thenReturn(Collections.emptyList());
        when(dao.create(any(UserDAO.class))).thenThrow(new SQLException("error"));
        Assertions.assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    public void testChangeName_invalidId() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> subject.changeName("bla", "new name"))
                .withMessage("Id 'bla' is not a number")
                .withCauseExactlyInstanceOf(NumberFormatException.class);
    }

    @Test
    public void testChangeName_userNotFound() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> subject.changeName("bla", "new name"))
                .withMessage("Id 'bla' is not a number")
                .withCauseExactlyInstanceOf(NumberFormatException.class);
    }

    @Test
    public void testChangePassword_invalidId() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> subject.changePassword("bla", "new pw"))
                .withMessage("Id 'bla' is not a number")
                .withCauseExactlyInstanceOf(NumberFormatException.class);
    }
}