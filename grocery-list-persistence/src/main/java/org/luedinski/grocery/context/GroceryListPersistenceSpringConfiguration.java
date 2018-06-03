package org.luedinski.grocery.context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.model.Product;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.model.utils.PasswordCrypter;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.ProductService;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * The spring configuration.
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:/META-INF/connection.properties"),
        @PropertySource("classpath:/META-INF/persistence.properties")
})
public class GroceryListPersistenceSpringConfiguration {

    @Autowired
    private Environment environment;

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
        String jdbcUrl = environment.getProperty("jdbc.url");
        String jdbcUser = environment.getProperty("jdbc.user");
        String jdbcPassword = environment.getProperty("jdbc.password");
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
    Dao<Category, Integer> categoryDao() {
        return createDao(Category.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link Product Products}.
     *
     * @return The {@link Dao}
     */
    @Bean
    Dao<Product, Integer> productDao() {
        return createDao(Product.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link User Users}.
     *
     * @return The {@link Dao}
     */
    @Bean
    Dao<User, Integer> userDao() {
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
     * Creates the bean implementing {@link CategoryService}.
     *
     * @return The {@link CategoryService}
     */
    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryDao());
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
     * Creates the bean implementing {@link UserService}.
     *
     * @return The {@link UserService}
     */
    @Bean
    public ProductService productService() {
        return new ProductService(productDao(), Product.class);
    }

    /**
     * Creates the bean implementing {@link PasswordCrypter}.
     *
     * @return The {@link PasswordCrypter}
     */
    @Bean
    public PasswordCrypter passwordCrypter() {
        return new PasswordCrypter(environment.getProperty("password.salt.iterations", Integer.class));
    }

    /**
     * Creates the bean implementing {@link MessageSourceSupport}.
     *
     * @return The {@link MessageSourceSupport}
     */
    @Bean
    public MessageSourceSupport messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setBasename("META-INF/i18n/translations");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

}
