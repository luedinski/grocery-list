package org.luedinski.grocery.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.model.Category;
import org.luedinski.grocery.persistence.model.Product;
import org.luedinski.grocery.persistence.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private Dao<Product, Integer> dao;

    @InjectMocks
    private ProductService subject;

    @Test
    public void testCreate() throws Exception {
        Category category = mock(Category.class);
        User user = mock(User.class);
        when(dao.create(any(Product.class))).thenAnswer(inv -> {
            ReflectionTestUtils.setField(inv.getArgumentAt(0, Product.class), "id", 42);
            ReflectionTestUtils.setField(inv.getArgumentAt(0, Product.class), "category", category);
            return 1;
        });

        Product apples = subject.create("Apples", category, user);

        Assertions.assertThat(apples).extracting(Product::getName, Product::getId, Product::getCategory).containsExactly("Apples", 42, category);
    }

    @Test
    public void testCreate_Exception() throws Exception {
        Category category = mock(Category.class);
        User user = mock(User.class);
        when(dao.create(any(Product.class))).thenThrow(new SQLException("test error"));

        Assertions.assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.create("Apples", category, user))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("test error");

    }
}