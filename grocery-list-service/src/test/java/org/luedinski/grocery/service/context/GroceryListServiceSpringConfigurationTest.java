package org.luedinski.grocery.service.context;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.service.utils.PasswordCrypter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

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

    @Test
    public void testMessageSource() {
        MessageSourceSupport messageSource = subject.messageSource();
        Assertions.assertThat(messageSource)
                .isInstanceOfSatisfying(ReloadableResourceBundleMessageSource.class, ms -> {
                    Assertions.assertThat(ms.getBasenameSet()).containsExactly("META-INF/i18n/translations");
                    Assertions.assertThat(ms).extracting("cacheMillis", "defaultEncoding").containsExactly(-1000L, "UTF-8");
                });
    }
}