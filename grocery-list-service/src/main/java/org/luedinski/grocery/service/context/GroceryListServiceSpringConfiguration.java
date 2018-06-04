package org.luedinski.grocery.service.context;

import java.nio.charset.StandardCharsets;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.context.GroceryListPersistenceSpringConfiguration;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.Product;
import org.luedinski.grocery.persistence.model.User;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.ProductService;
import org.luedinski.grocery.service.UserService;
import org.luedinski.grocery.service.utils.PasswordCrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/META-INF/grocery-list-service.properties")
@Import({GroceryListPersistenceSpringConfiguration.class})
public class GroceryListServiceSpringConfiguration {
    @Autowired
    private Environment environment;

    /**
     * Constructor needed to disable API consumer to instantiate this type, but necessary for Spring Java-based (container) configuration.
     */
    GroceryListServiceSpringConfiguration() {
    }

    /**
     * Creates the bean implementing {@link CategoryService}.
     *
     * @return The {@link CategoryService}
     */
    @Bean
    public CategoryService categoryService(@Autowired Dao<Category, Integer> categoryDao) {
        return new CategoryService(categoryDao);
    }

    /**
     * Creates the bean implementing {@link UserService}.
     *
     * @return The {@link UserService}
     */
    @Bean
    public UserService userService(@Autowired Dao<User, Integer> userDao) {
        return new UserService(userDao, passwordCrypter());
    }

    /**
     * Creates the bean implementing {@link UserService}.
     *
     * @return The {@link UserService}
     */
    @Bean
    public ProductService productService(@Autowired Dao<Product, Integer> productDao) {
        return new ProductService(productDao, Product.class);
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
