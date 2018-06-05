package org.luedinski.grocery.persistence.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "products")
public class ProductDAO extends AbstractDAO {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "category_id")
    private CategoryDAO categoryDAO;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private UserDAO userDAO;

    ProductDAO() {

    }

    public ProductDAO(String name, CategoryDAO categoryDAO, UserDAO userDAO) {
        super(name);
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
    }

    public CategoryDAO getCategory() {
        return categoryDAO;
    }

    public void setCategory(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
}
