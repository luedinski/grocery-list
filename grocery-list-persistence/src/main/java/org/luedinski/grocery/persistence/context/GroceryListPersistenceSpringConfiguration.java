package org.luedinski.grocery.persistence.context;

import java.sql.SQLException;
import java.util.Arrays;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.luedinski.grocery.persistence.DatabaseInitializer;
import org.luedinski.grocery.persistence.model.AbstractDAO;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.Product;
import org.luedinski.grocery.persistence.model.User;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The spring configuration.
 */
@Configuration
@PropertySource("classpath:/META-INF/grocery-list-persistence.properties")
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
    @Bean("categoryDao")
    Dao<Category, Integer> categoryDao() {
        return createDao(Category.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link Product Products}.
     *
     * @return The {@link Dao}
     */
    @Bean("productDao")
    Dao<Product, Integer> productDao() {
        return createDao(Product.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link User Users}.
     *
     * @return The {@link Dao}
     */
    @Bean("userDao")
    Dao<User, Integer> userDao() {
        return createDao(User.class);
    }

    @Bean
    DatabaseInitializer databaseInitializer() {
        return new DatabaseInitializer(connectionSource(), User.class, Category.class, Product.class);
    }

    private <M, I> Dao<M, I> createDao(Class<M> clazz) {
        try {
            return DaoManager.createDao(connectionSource(), clazz);
        } catch (SQLException e) {
            throw new BeanCreationException("Dao creation of " + clazz + " failed", e);
        }
    }

}
