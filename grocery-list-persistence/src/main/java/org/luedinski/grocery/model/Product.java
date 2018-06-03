package org.luedinski.grocery.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "products")
public class Product extends AbstractDAO {

    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "category_id")
    private Category category;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_id")
    private User user;

    Product() {
    }

    public Product(String name, Category category, User user) {
        this.name = name;
        this.category = category;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
