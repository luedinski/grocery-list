package org.luedinski.grocery.persistence.context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.luedinski.grocery.persistence.DatabaseInitializer;
import org.luedinski.grocery.persistence.dao.CategoryDAO;
import org.luedinski.grocery.persistence.dao.ProductDAO;
import org.luedinski.grocery.persistence.dao.UserDAO;
import org.luedinski.grocery.persistence.repo.CategoryRepository;
import org.luedinski.grocery.persistence.repo.ProductRepository;
import org.luedinski.grocery.persistence.repo.UserRepository;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.SQLException;

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
     * Creates the bean implementing {@link Dao} for {@link UserDAO Users}.
     *
     * @return The {@link Dao}
     */
    @Bean("categoryDao")
    Dao<CategoryDAO, Integer> categoryDao() {
        return createDao(CategoryDAO.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link ProductDAO Products}.
     *
     * @return The {@link Dao}
     */
    @Bean("productDao")
    Dao<ProductDAO, Integer> productDao() {
        return createDao(ProductDAO.class);
    }

    /**
     * Creates the bean implementing {@link Dao} for {@link UserDAO Users}.
     *
     * @return The {@link Dao}
     */
    @Bean("userDao")
    Dao<UserDAO, Integer> userDao() {
        return createDao(UserDAO.class);
    }

    @Bean
    DatabaseInitializer databaseInitializer() {
        return new DatabaseInitializer(connectionSource(), UserDAO.class, CategoryDAO.class, ProductDAO.class);
    }

    private <M, I> Dao<M, I> createDao(Class<M> clazz) {
        try {
            return DaoManager.createDao(connectionSource(), clazz);
        } catch (SQLException e) {
            throw new BeanCreationException("Dao creation of " + clazz + " failed", e);
        }
    }

    /**
     * Creates the bean implementing {@link CategoryRepository}.
     *
     * @return The {@link CategoryRepository}
     */
    @Bean
    public CategoryRepository categoryService() {
        return new CategoryRepository(categoryDao(), TableUtils::createTableIfNotExists);
    }

    /**
     * Creates the bean implementing {@link UserRepository}.
     *
     * @return The {@link UserRepository}
     */
    @Bean
    public UserRepository userService() {
        return new UserRepository(userDao(), TableUtils::createTableIfNotExists);
    }

    /**
     * Creates the bean implementing {@link UserRepository}.
     *
     * @return The {@link UserRepository}
     */
    @Bean
    public ProductRepository productService() {
        return new ProductRepository(productDao(), TableUtils::createTableIfNotExists, ProductDAO.class);
    }


}
