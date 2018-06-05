package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private Dao<CategoryDAO, Integer> dao;

    @InjectMocks
    private CategoryService subject;

    @Test
    public void testCreate() throws Exception {
        UserDAO userDAO = new UserDAO("userDAO", "pw");
        CategoryDAO categoryDAO = subject.create("test", userDAO);

        Assertions.assertThat(categoryDAO).extracting(CategoryDAO::getName, CategoryDAO::getUser).containsExactly("test", userDAO);
        verify(dao).create(categoryDAO);
    }
}