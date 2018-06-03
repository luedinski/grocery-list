package org.luedinski.grocery.service;

import com.j256.ormlite.dao.Dao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.DatabaseOperationException;
import org.luedinski.grocery.model.utils.SQLOperation;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(dao.queryForId("A")).thenReturn(expected);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isPresent().get().isSameAs(expected);

    }

    @Test
    void testGetById_notPresent() throws Exception {
        Object expected = new Object();
        when(dao.queryForId("A")).thenReturn(null);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isNotPresent();

    }

    @Test
    void testGetById_notPresentFromSQLException() throws Exception {
        Object expected = new Object();
        when(dao.queryForId("A")).thenThrow(new SQLException("error"));

        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.getById("A"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");


    }

    @Test
    void testOperate() {
        String result = subject.operate(() -> "foo");
        assertThat(result).isEqualTo("foo");
    }

    @Test
    void testOperate_excpetion() throws Exception {
        SQLOperation<String> operation = () -> {
            throw new SQLException("error");
        };
        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.operate(operation))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    void testCreate() throws Exception {
        Object model = new Object();
        when(dao.create(model)).thenReturn(1);
        int i = subject.create(model);
        assertThat(i).isEqualByComparingTo(1);
        verify(dao).create(model);
    }

    @Test
    void testCreate_exception() throws Exception {
        Object model = new Object();
        when(dao.create(model)).thenThrow(new SQLException("error"));
        assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.create(model))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    void testExists() throws Exception {

        when(dao.idExists("test")).thenReturn(true);
        assertThat(subject.exists("test")).isTrue();
    }

    @Test
    void testExists_notExists() throws Exception {

        when(dao.idExists("test")).thenReturn(false);
        assertThat(subject.exists("test")).isFalse();
    }

    @Test
    void testExists_exception() throws Exception {

        when(dao.idExists("test")).thenThrow(new SQLException("error"));
        assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.exists("test"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }
}