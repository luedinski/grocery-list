package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private Dao<Category, Integer> dao;

    @InjectMocks
    private CategoryService subject;

    @Test
    public void testCreate() throws Exception {
        User user = new User("user", "pw");
        Category category = subject.create("test", user);

        Assertions.assertThat(category).extracting(Category::getName, Category::getUser).containsExactly("test", user);
        verify(dao).create(category);
    }
}