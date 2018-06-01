package org.luedinski.grocery.context;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.model.utils.PasswordCrypter;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * The spring configuration.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:/META-INF/connection.properties"),
        @PropertySource("classpath:/META-INF/persistence.properties")
})
public class GroceryListPersistenceSpringConfiguration {

    @Value("${password.salt.iterations}")
    private int passwordSaltIterations;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.user}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /**
     * Constructor needed to disable API consumer to instantiate this type, but necessary for Spring Java-based (container) configuration.
     */
    GroceryListPersistenceSpringConfiguration() {
    }

    /**
     * Creates the bean implementing {@link ConnectionSource} for db access.
     *
     * @return The {@link ConnectionSource}
     */
    @Bean(destroyMethod = "close")
    ConnectionSource connectionSource() {
        try {
            return new JdbcPooledConnectionSource(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (SQLException e) {
            throw new BeanCreationException("Database can not be acquired.", e);
        }
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link User Users}.
     *
     * @return The {@link Dao}
     */
    @Bean
    Dao<User, String> userDao() {
        return createDao(User.class);
    }

    private <M, I> Dao<M, I> createDao(Class<M> clazz) {
        try {
            return DaoManager.createDao(connectionSource(), clazz);
        } catch (SQLException e) {
            throw new BeanCreationException("Dao creation of " + clazz + " failed", e);
        }
    }

    /**
     * Creates the bean implementing {@link UserService}.
     *
     * @return The {@link UserService}
     */
    @Bean
    public UserService userService() {
        return new UserService(userDao(), passwordCrypter());
    }

    /**
     * Creates the bean implementing {@link PasswordCrypter}.
     *
     * @return The {@link PasswordCrypter}
     */
    @Bean
    public PasswordCrypter passwordCrypter() {
        return new PasswordCrypter(passwordSaltIterations);
    }

}
