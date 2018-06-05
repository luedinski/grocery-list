package org.luedinski.grocery.persistence.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class UserDAO extends AbstractDAO {

    @DatabaseField(canBeNull = false)
    private String password;
    @ForeignCollectionField
    private ForeignCollection<CategoryDAO> categories;
    @ForeignCollectionField
    private ForeignCollection<ProductDAO> productDAOS;
    //    @ForeignCollectionField
    //    private ForeignCollection<GroceryListDAO> groceryLists;
    //    @ForeignCollectionField
    //    private ForeignCollection<StockListDAO> stockLists;

    UserDAO() {
    }

    public UserDAO(String name, String password) {
        super(name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ForeignCollection<CategoryDAO> getCategories() {
        return categories;
    }

    public ForeignCollection<ProductDAO> getProducts() {
        return productDAOS;
    }

    //    public ForeignCollection<GroceryListDAO> getGroceryLists() {
    //        return groceryLists;
    //    }
    //
    //    public ForeignCollection<StockListDAO> getStockLists() {
    //        return stockLists;
    //    }
}
