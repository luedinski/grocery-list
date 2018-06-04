package org.luedinski.grocery.persistence.it;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.model.AbstractDAO;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryTest extends AbstractIntegrationTest {

    @Autowired
    private Dao<User, Integer> userDao;
    @Autowired
    private Dao<Category, Integer> categoryDao;

    @Test
    public void testAddCategory() throws Exception {
        User user = new User("name", "1234");
        userDao.create(user);

        Category category = new Category("test", user);
        int i = categoryDao.create(category);
        Assertions.assertThat(i).isEqualByComparingTo(1);
        Assertions.assertThat(category).extracting(AbstractDAO::getName, Category::getUser).containsExactly("test", user);
    }
}
