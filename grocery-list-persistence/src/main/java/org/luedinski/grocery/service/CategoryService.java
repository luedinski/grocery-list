package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.model.User;

public class CategoryService extends AbstractModelService<Category, Integer> {

    CategoryService(Dao dao) {
        super(dao, Category.class);
    }

    public Category create(String name, User user) {
        Category category = new Category(name, user);
        create(category);
        return category;
    }
}
