package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.ProductDAO;
import org.luedinski.grocery.persistence.model.UserDAO;

public class ProductService extends AbstractDAOService<ProductDAO> {

    public ProductService(Dao<ProductDAO, Integer> dao, TableFactory tableFactory, Class<ProductDAO> daoClazz) {
        super(dao, tableFactory, daoClazz);
    }

    /**
     * Creates a product with the given name, {@link CategoryDAO} and {@link UserDAO}.
     *
     * @param name The product name
     * @param categoryDAO The categoryDAO
     * @param userDAO The userDAO
     * @return The created product
     */
    public ProductDAO create(String name, CategoryDAO categoryDAO, UserDAO userDAO) {
        ProductDAO productDAO = new ProductDAO(name, categoryDAO, userDAO);
        operate(() -> dao.create(productDAO));
        return productDAO;
    }
}
