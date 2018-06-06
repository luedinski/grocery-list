package org.luedinski.grocery.persistence.it;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.dao.CategoryDAO;
import org.luedinski.grocery.persistence.dao.ProductDAO;
import org.luedinski.grocery.persistence.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAOTest extends AbstractIntegrationTest {
    @Autowired
    private Dao<UserDAO, Integer> userDao;
    @Autowired
    private Dao<CategoryDAO, Integer> categoryDao;
    @Autowired
    private Dao<ProductDAO, Integer> productDao;

    @Test
    public void testProducts() throws Exception {
        UserDAO userDAO = new UserDAO("name", "pw");
        userDao.create(userDAO);
        CategoryDAO categoryDAO = new CategoryDAO("Misc", userDAO);
        categoryDao.create(categoryDAO);
        ProductDAO apples = new ProductDAO("Apples", categoryDAO, userDAO);
        productDao.create(apples);

        Assertions.assertThat(apples.getId()).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(apples.getName()).isEqualToIgnoringCase("Apples");

        userDao.refresh(userDAO);
        List<ProductDAO> userProductDAOS = new ArrayList<>(userDAO.getProducts());
        Assertions.assertThat(userProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        categoryDao.refresh(categoryDAO);
        List<ProductDAO> categoryProductDAOS = new ArrayList<>(categoryDAO.getProducts());
        Assertions.assertThat(categoryProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

    }

    @Test
    public void testRenameProduct() throws Exception {
        UserDAO userDAO = new UserDAO("name", "pw");
        userDao.create(userDAO);
        CategoryDAO miscCategoryDAO = new CategoryDAO("Misc", userDAO);
        categoryDao.create(miscCategoryDAO);
        CategoryDAO fruitsCategoryDAO = new CategoryDAO("Fruits", userDAO);
        categoryDao.create(fruitsCategoryDAO);
        ProductDAO apples = new ProductDAO("Apples", miscCategoryDAO, userDAO);
        productDao.create(apples);
        Date creationDate = apples.getLastModified();
        Assertions.assertThat(creationDate).isNotNull();

        Assertions.assertThat(apples.getName()).isEqualToIgnoringCase("Apples");

        userDao.refresh(userDAO);
        List<ProductDAO> userProductDAOS = new ArrayList<>(userDAO.getProducts());
        Assertions.assertThat(userProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        categoryDao.refresh(miscCategoryDAO);
        List<ProductDAO> miscCategoryProductDAOS = new ArrayList<>(miscCategoryDAO.getProducts());
        Assertions.assertThat(miscCategoryProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Apples");
        });

        apples.setName("Red Apples");
        apples.setCategory(fruitsCategoryDAO);
        productDao.update(apples);

        Assertions.assertThat(apples.getLastModified()).isAfter(creationDate);
        Assertions.assertThat(apples.getCategory()).isSameAs(fruitsCategoryDAO);

        userDao.refresh(userDAO);
        userProductDAOS = new ArrayList<>(userDAO.getProducts());
        Assertions.assertThat(userProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Red Apples");
        });

        categoryDao.refresh(miscCategoryDAO);
        miscCategoryProductDAOS = new ArrayList<>(miscCategoryDAO.getProducts());
        Assertions.assertThat(miscCategoryProductDAOS).isEmpty();

        categoryDao.refresh(fruitsCategoryDAO);
        List<ProductDAO> fruitsCategoryProductDAOS = new ArrayList<>(fruitsCategoryDAO.getProducts());
        Assertions.assertThat(fruitsCategoryProductDAOS).hasSize(1).element(0).satisfies(p -> {
            Assertions.assertThat(p.getId()).isEqualByComparingTo(apples.getId());
            Assertions.assertThat(p.getName()).isEqualTo("Red Apples");
        });
    }
}
