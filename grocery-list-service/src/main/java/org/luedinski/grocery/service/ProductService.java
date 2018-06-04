package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.Product;
import org.luedinski.grocery.persistence.model.User;

public class ProductService extends AbstractDAOService<Product> {

    public ProductService(Dao<Product, Integer> dao, Class<Product> daoClazz) {
        super(dao, daoClazz);
    }

    /**
     * Creates a product with the given name, {@link Category} and {@link User}.
     * @param name The product name
     * @param category The category
     * @param user The user
     * @return The created product
     */
    public Product create(String name, Category category, User user) {
        Product product = new Product(name, category, user);
        operate(() -> dao.create(product));
        return product;
    }
}
