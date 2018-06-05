package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private Dao<CategoryDAO, Integer> dao;

    @InjectMocks
    private CategoryService subject;

    @Test
    public void testCreate() throws Exception {
        UserDAO userDAO = new UserDAO("userDAO", "pw");
        Date currentDate = mock(Date.class);
        when(currentDate.getTime()).thenReturn(321L);
        when(dao.create(any(CategoryDAO.class))).thenAnswer(inv-> {
            CategoryDAO categoryDAO = inv.getArgumentAt(0, CategoryDAO.class);
            ReflectionTestUtils.setField(categoryDAO, "id", 123);
            ReflectionTestUtils.setField(categoryDAO, "lastModified", currentDate);
            return 1;
        });
        Category category = subject.create("test", userDAO);

        Assertions.assertThat(category).extracting(Category::getId, Category::getName, Category::getVersion).containsExactly("123", "test", "321");
    }
}