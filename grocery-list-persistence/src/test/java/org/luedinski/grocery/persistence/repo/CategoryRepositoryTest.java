package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.dao.CategoryDAO;
import org.luedinski.grocery.persistence.dao.UserDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryRepositoryTest {

    @Mock
    private Dao<CategoryDAO, Integer> dao;

    @InjectMocks
    private CategoryRepository subject;

    @Test
    public void testCreate() throws Exception {
        UserDAO userDAO = new UserDAO("user", "pw");
        Date currentDate = mock(Date.class);
        when(currentDate.getTime()).thenReturn(321L);
        when(dao.create(any(CategoryDAO.class))).thenAnswer(inv -> {
            CategoryDAO categoryDAO = inv.getArgumentAt(0, CategoryDAO.class);
            ReflectionTestUtils.setField(categoryDAO, "id", 123);
            ReflectionTestUtils.setField(categoryDAO, "lastModified", currentDate);
            return 1;
        });
        CategoryDAO category = subject.create("test", userDAO);

        Assertions.assertThat(category)
                .extracting(CategoryDAO::getId, CategoryDAO::getName, CategoryDAO::getLastModified)
                .containsExactly(123, "test", currentDate);
    }
}