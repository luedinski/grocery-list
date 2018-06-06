package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.dao.CategoryDAO;
import org.luedinski.grocery.persistence.dao.ProductDAO;
import org.luedinski.grocery.persistence.dao.UserDAO;

public class ProductRepository extends AbstractCrudRepository<ProductDAO> {

    public ProductRepository(Dao<ProductDAO, Integer> dao, TableFactory tableFactory, Class<ProductDAO> daoClazz) {
        super(dao, tableFactory, daoClazz);
    }

    /**
     * Creates a product with the given name, {@link CategoryDAO} and {@link UserDAO}.
     *
     * @param name        The product name
     * @param categoryDAO The categoryDAO
     * @param userDAO     The userDAO
     * @return The created product
     */
    public ProductDAO create(String name, CategoryDAO categoryDAO, UserDAO userDAO) {
        ProductDAO productDAO = new ProductDAO(name, categoryDAO, userDAO);
        operate(() -> dao.create(productDAO));
        return productDAO;
    }
}
