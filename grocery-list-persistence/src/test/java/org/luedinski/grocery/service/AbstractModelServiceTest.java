package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.DatabaseOperationException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(MockitoExtension.class)
class AbstractModelServiceTest {

    @Mock
    private Dao<Object, Object> dao;

    private AbstractModelService<Object, Object> subject;

    @BeforeEach
    void setUp() throws Exception {
        subject = new AbstractModelService<Object, Object>(dao, Object.class) {
        };
    }

    @Test
    void testGetById() throws Exception {
        Object expected = new Object();
        Mockito.when(dao.queryForId("A")).thenReturn(expected);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isPresent().get().isSameAs(expected);

    }

    @Test
    void testGetById_notPresent() throws Exception {
        Object expected = new Object();
        Mockito.when(dao.queryForId("A")).thenReturn(null);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isNotPresent();

    }

    @Test
    void testGetById_notPresentFromSQLException() throws Exception {
        Object expected = new Object();
        Mockito.when(dao.queryForId("A")).thenThrow(new SQLException("error"));
        
        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.getById("A"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");


    }

    @Test
    void testOperate() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void testExists() {
    }
}