package org.luedinski.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Optional;

import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.DatabaseOperationException;
import org.luedinski.grocery.model.utils.SQLOperation;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractModelServiceTest {

    @Mock
    private Dao<Object, Object> dao;

    private AbstractModelService<Object, Object> subject;

    @Before
    public void setUp() throws Exception {
        subject = new AbstractModelService<Object, Object>(dao, Object.class) {
        };
    }

    @Test
    public void testGetById() throws Exception {
        Object expected = new Object();
        when(dao.queryForId("A")).thenReturn(expected);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isPresent().get().isSameAs(expected);

    }

    @Test
    public void testGetById_notPresent() throws Exception {
        Object expected = new Object();
        when(dao.queryForId("A")).thenReturn(null);
        Optional<Object> model = subject.getById("A");

        assertThat(model).isNotPresent();

    }

    @Test
    public void testGetById_notPresentFromSQLException() throws Exception {
        Object expected = new Object();
        when(dao.queryForId("A")).thenThrow(new SQLException("error"));

        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.getById("A"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");


    }

    @Test
    public void testOperate() {
        String result = subject.operate(() -> "foo");
        assertThat(result).isEqualTo("foo");
    }

    @Test
    public void testOperate_excpetion() throws Exception {
        SQLOperation<String> operation = () -> {
            throw new SQLException("error");
        };
        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.operate(operation))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    public void testCreate() throws Exception {
        Object model = new Object();
        when(dao.create(model)).thenReturn(1);
        int i = subject.create(model);
        assertThat(i).isEqualByComparingTo(1);
        verify(dao).create(model);
    }

    @Test
    public void testCreate_exception() throws Exception {
        Object model = new Object();
        when(dao.create(model)).thenThrow(new SQLException("error"));
        assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.create(model))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    public void testExists() throws Exception {

        when(dao.idExists("test")).thenReturn(true);
        assertThat(subject.exists("test")).isTrue();
    }

    @Test
    public void testExists_notExists() throws Exception {

        when(dao.idExists("test")).thenReturn(false);
        assertThat(subject.exists("test")).isFalse();
    }

    @Test
    public void testExists_exception() throws Exception {

        when(dao.idExists("test")).thenThrow(new SQLException("error"));
        assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.exists("test"))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }
}