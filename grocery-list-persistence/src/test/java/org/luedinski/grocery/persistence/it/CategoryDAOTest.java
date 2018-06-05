package org.luedinski.grocery.persistence.it;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.model.AbstractDAO;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryDAOTest extends AbstractIntegrationTest {

    @Autowired
    private Dao<UserDAO, Integer> userDao;
    @Autowired
    private Dao<CategoryDAO, Integer> categoryDao;

    @Test
    public void testAddCategory() throws Exception {
        UserDAO userDAO = new UserDAO("name", "1234");
        userDao.create(userDAO);

        CategoryDAO categoryDAO = new CategoryDAO("test", userDAO);
        int i = categoryDao.create(categoryDAO);
        Assertions.assertThat(i).isEqualByComparingTo(1);
        Assertions.assertThat(categoryDAO).extracting(AbstractDAO::getName, CategoryDAO::getUser).containsExactly("test", userDAO);
    }
}
