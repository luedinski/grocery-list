package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.UserDAO;

public class CategoryService extends AbstractDAOService<CategoryDAO> {

    public CategoryService(Dao<CategoryDAO, Integer> dao, TableFactory tableFactory) {
        super(dao, tableFactory, CategoryDAO.class);
    }

    public Category create(String name, UserDAO userDAO) {
        CategoryDAO categoryDAO = new CategoryDAO(name, userDAO);
        create(categoryDAO);
        return new Category(String.valueOf(categoryDAO.getId()), categoryDAO.getName(), String.valueOf(categoryDAO.getLastModified().getTime()));
    }
}
