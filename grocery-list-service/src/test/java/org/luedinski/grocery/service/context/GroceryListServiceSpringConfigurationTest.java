package org.luedinski.grocery.service.context;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.service.utils.PasswordCrypter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(MockitoJUnitRunner.class)
public class GroceryListServiceSpringConfigurationTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private GroceryListServiceSpringConfiguration subject;

    @Test
    public void testPasswordCrypter() {
        when(environment.getProperty("password.salt.iterations", Integer.class)).thenReturn(4);
        PasswordCrypter passwordCrypter = subject.passwordCrypter();
        Assertions.assertThat(passwordCrypter).isNotNull();
    }
}