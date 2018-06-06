package org.luedinski.grocery.persistence.dao;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categories")
public class CategoryDAO extends AbstractDAO {

    @ForeignCollectionField
    private ForeignCollection<ProductDAO> productDAOS;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private UserDAO userDAO;

    CategoryDAO() {
    }

    public CategoryDAO(String name, UserDAO userDAO) {
        super(name);
        this.userDAO = userDAO;
    }

    public ForeignCollection<ProductDAO> getProducts() {
        return productDAOS;
    }

    public UserDAO getUser() {
        return userDAO;
    }
}
