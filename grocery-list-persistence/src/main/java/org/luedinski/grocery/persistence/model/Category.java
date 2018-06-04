package org.luedinski.grocery.persistence.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categories")
public class Category extends AbstractDAO {

    @ForeignCollectionField
    private ForeignCollection<Product> products;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private User user;

    Category() {
    }

    public Category(String name, User user) {
        super(name);
        this.user = user;
    }

    public ForeignCollection<Product> getProducts() {
        return products;
    }

    public User getUser() {
        return user;
    }
}
