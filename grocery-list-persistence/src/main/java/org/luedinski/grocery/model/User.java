package org.luedinski.grocery.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(id = true)
    private String id;
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

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
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
