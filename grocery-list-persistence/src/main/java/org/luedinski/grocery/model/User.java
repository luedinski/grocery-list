package org.luedinski.grocery.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User extends Nameable {

    @DatabaseField(canBeNull = false)
    private String password;
    @ForeignCollectionField
    private ForeignCollection<Category> categories;
    @ForeignCollectionField
    private ForeignCollection<Product> products;
    //    @ForeignCollectionField
    //    private ForeignCollection<GroceryList> groceryLists;
    //    @ForeignCollectionField
    //    private ForeignCollection<StockList> stockLists;

    User() {
    }

    public User(String name, String password) {
        super(name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<Category> getCategories() {
        return categories;
    }

    public ForeignCollection<Product> getProducts() {
        return products;
    }

    //    public ForeignCollection<GroceryList> getGroceryLists() {
    //        return groceryLists;
    //    }
    //
    //    public ForeignCollection<StockList> getStockLists() {
    //        return stockLists;
    //    }
}
