package org.luedinski.grocery.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Optional;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractDAOServiceTest {

    @Mock
    private Dao<Object, Integer> dao;

    @Mock
    private TableFactory tableFactory;

    private AbstractDAOService<Object> subject;

    @Before
    public void setUp() throws Exception {
        subject = new AbstractDAOService<Object>(dao, tableFactory, Object.class) {
        };
    }

    @Test
    public void testGetById() throws Exception {
        Object expected = new Object();
        when(dao.queryForId(123)).thenReturn(expected);
        Optional<Object> model = subject.getById(123);

        assertThat(model).isPresent().get().isSameAs(expected);

    }

    @Test
    public void testGetById_notPresent() throws Exception {
        Object expected = new Object();
        when(dao.queryForId(123)).thenReturn(null);
        Optional<Object> model = subject.getById(123);

        assertThat(model).isNotPresent();

    }

    @Test
    public void testGetById_notPresentFromSQLException() throws Exception {
        Object expected = new Object();
        when(dao.queryForId(123)).thenThrow(new SQLException("error"));

        assertThatExceptionOfType(DatabaseOperationException.class)
                .isThrownBy(() -> subject.getById(123))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");


    }

    @Test
    public void testOperate() {
        String result = subject.operate(() -> "foo");
        assertThat(result).isEqualTo("foo");
    }

    @Test
    public void testOperate_exception() throws Exception {
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

        when(dao.idExists(123)).thenReturn(true);
        assertThat(subject.exists(123)).isTrue();
    }

    @Test
    public void testExists_notExists() throws Exception {

        when(dao.idExists(123)).thenReturn(false);
        assertThat(subject.exists(123)).isFalse();
    }

    @Test
    public void testExists_exception() throws Exception {

        when(dao.idExists(123)).thenThrow(new SQLException("error"));
        assertThatExceptionOfType(DatabaseOperationException.class).isThrownBy(() -> subject.exists(123))
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("error");
    }

    @Test
    public void testInitTable() throws Exception {
        ConnectionSource connectionSource = mock(ConnectionSource.class);
        when(dao.getConnectionSource()).thenReturn(connectionSource);
        doThrow(new SQLException("test error")).when(tableFactory).initTable(connectionSource, Object.class);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> subject.initTable())
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("test error");

    }
}