package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.User;

public class CategoryService extends AbstractDAOService<Category> {

    public CategoryService(Dao<Category, Integer> dao) {
        super(dao, Category.class);
    }

    public Category create(String name, User user) {
        Category category = new Category(name, user);
        create(category);
        return category;
    }
}
