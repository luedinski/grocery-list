package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.dao.CategoryDAO;
import org.luedinski.grocery.persistence.dao.UserDAO;

public class CategoryRepository extends AbstractCrudRepository<CategoryDAO> {

    public CategoryRepository(Dao<CategoryDAO, Integer> dao, TableFactory tableFactory) {
        super(dao, tableFactory, CategoryDAO.class);
    }

    public CategoryDAO create(String name, UserDAO userDAO) {
        CategoryDAO categoryDAO = new CategoryDAO(name, userDAO);
        create(categoryDAO);
        return categoryDAO;
    }
}
