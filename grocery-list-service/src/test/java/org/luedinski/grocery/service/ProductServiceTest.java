package org.luedinski.grocery.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.model.CategoryDAO;
import org.luedinski.grocery.persistence.model.ProductDAO;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private Dao<ProductDAO, Integer> dao;

    @InjectMocks
    private ProductService subject;

    @Test
    public void testCreate() throws Exception {
        CategoryDAO categoryDAO = mock(CategoryDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(dao.create(any(ProductDAO.class))).thenAnswer(inv -> {
            ReflectionTestUtils.setField(inv.getArgumentAt(0, ProductDAO.class), "id", 42);
            ReflectionTestUtils.setField(inv.getArgumentAt(0, ProductDAO.class), "categoryDAO", categoryDAO);
            return 1;
        });

        ProductDAO apples = subject.create("Apples", categoryDAO, userDAO);

        Assertions.assertThat(apples).extracting(ProductDAO::getName, ProductDAO::getId, ProductDAO::getCategory).containsExactly("Apples", 42, categoryDAO);
    }

    @Test
    public void testCreate_Exception() throws Exception {
        CategoryDAO categoryDAO = mock(CategoryDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(dao.create(any(ProductDAO.class))).thenThrow(new SQLException("test error"));

        Assertions.assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.create("Apples", categoryDAO, userDAO))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("test error");

    }
}