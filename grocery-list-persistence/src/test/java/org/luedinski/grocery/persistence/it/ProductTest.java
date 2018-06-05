package org.luedinski.grocery.persistence.it;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.Product;
import org.luedinski.grocery.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductTest extends AbstractIntegrationTest {
    @Autowired
    private Dao<User, Integer> userDao;
    @Autowired
    private Dao<Category, Integer> categoryDao;
    @Autowired
    private Dao<Product, Integer> productDao;

    @Test
    public void testProducts() throws Exception {
        User user = new User("name", "pw");
        userDao.create(user);
        Category category = new Category("Misc", user);
        categoryDao.create(category);
        Product apples = new Product("Apples", category, user);
        productDao.create(apples);

        Assertions.assertThat(apples.getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(apples.getName()).isEqualToIgnoringCase("Apples");

        userDao.refresh(user);
        List<Product> userProducts = new ArrayList<>(user.getProducts());
        Assertions.assertThat(userProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        categoryDao.refresh(category);
        List<Product> categoryProducts = new ArrayList<>(category.getProducts());
        Assertions.assertThat(categoryProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });


    }

    @Test
    public void testRenameProduct() throws Exception {
        User user = new User("name", "pw");
        userDao.create(user);
        Category miscCategory = new Category("Misc", user);
        categoryDao.create(miscCategory);
        Category fruitsCategory = new Category("Fruits", user);
        categoryDao.create(fruitsCategory);
        Product apples = new Product("Apples", miscCategory, user);
        productDao.create(apples);
        Date creationDate = apples.getLastModified();
        Assertions.assertThat(creationDate).isNotNull();

        Assertions.assertThat(apples.getName()).isEqualToIgnoringCase("Apples");

        userDao.refresh(user);
        List<Product> userProducts = new ArrayList<>(user.getProducts());
        Assertions.assertThat(userProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        categoryDao.refresh(miscCategory);
        List<Product> miscCategoryProducts = new ArrayList<>(miscCategory.getProducts());
        Assertions.assertThat(miscCategoryProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        apples.setName("Red Apples");
        apples.setCategory(fruitsCategory);
        productDao.update(apples);

        Assertions.assertThat(apples.getLastModified()).isAfter(creationDate);

        userDao.refresh(user);
        userProducts = new ArrayList<>(user.getProducts());
        Assertions.assertThat(userProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Red Apples");
        });

        categoryDao.refresh(miscCategory);
        miscCategoryProducts = new ArrayList<>(miscCategory.getProducts());
        Assertions.assertThat(miscCategoryProducts).isEmpty();

        categoryDao.refresh(fruitsCategory);
        List<Product> fruitsCategoryProducts = new ArrayList<>(fruitsCategory.getProducts());
        Assertions.assertThat(fruitsCategoryProducts).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Red Apples");
        });
    }
}
