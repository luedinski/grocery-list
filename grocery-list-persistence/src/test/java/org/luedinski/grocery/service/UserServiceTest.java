package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.IdentifierInUseException;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.model.utils.PasswordCrypter;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordCrypter passwordCrypter;

    @Mock
    private Dao<User, String> userDso;

    @InjectMocks
    private UserService subject;

    @Test
    void testCreate() throws Exception {
        when(passwordCrypter.crypt(anyString())).thenAnswer(inv -> new StringBuilder(inv.getArgument(0)).reverse().toString());
        when(userDso.idExists("name")).thenReturn(false, true);
        when(userDso.create(any(User.class))).thenReturn(1);
        User user = subject.create("name", "pw");

        Assertions.assertThat(user).extracting(User::getId, User::getPassword).containsExactly("name", "wp");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDso).create(userCaptor.capture());
        Assertions.assertThat(userCaptor).isNotSameAs(user);

    }

    @Test
    void testCreate_IdInUse() throws Exception {
        when(userDso.idExists("name")).thenReturn(true);
        Assertions.assertThatExceptionOfType(IdentifierInUseException.class)
                .isThrownBy(() -> subject.create("name", "pw"))
                .withMessage("Element with id 'name' already exists.");


    }
}