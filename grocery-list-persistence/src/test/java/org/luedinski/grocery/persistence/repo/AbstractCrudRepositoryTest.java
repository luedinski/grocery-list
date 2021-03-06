package org.luedinski.grocery.persistence.repo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.DAONotFoundException;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractCrudRepositoryTest {

    @Mock
    private Dao<Object, Integer> dao;

    @Mock
    private TableFactory tableFactory;

    private AbstractCrudRepository<Object> subject;

    @Before
    public void setUp() {
        subject = new AbstractCrudRepository<Object>(dao, tableFactory, Object.class) {

        };
    }

    @Test
    public void testGetById() throws Exception {
        Object expected = new Object();
        when(dao.queryForId(123)).thenReturn(expected);
        Object model = subject.getById(123);

        assertThat(model).isSameAs(expected);

    }

    @Test
    public void testGetById_notPresent() throws Exception {
        Object expected = new Object();
        when(dao.queryForId(123)).thenReturn(null);
        assertThatExceptionOfType(DAONotFoundException.class)
                .isThrownBy(() -> subject.getById(123))
                .withMessage("Object with id'123' not found.");

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
    public void testOperate_exception() {
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
    public void testInitTable_initFails() throws Exception {
        ConnectionSource connectionSource = mock(ConnectionSource.class);
        when(dao.getConnectionSource()).thenReturn(connectionSource);
        doThrow(new SQLException("test error")).when(tableFactory).initTable(connectionSource, Object.class);
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> subject.initTable())
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("test error");

    }

    @Test
    public void testInitTable() throws Exception {
        ConnectionSource connectionSource = mock(ConnectionSource.class);
        when(dao.getConnectionSource()).thenReturn(connectionSource);
        subject.initTable();

        verify(tableFactory).initTable(connectionSource, Object.class);

    }

    @Test
    public void testSave() throws Exception {
        Object model = new Object();
        subject.save(model);

        verify(dao).update(model);
    }

    @Test
    public void testRefresh() throws Exception {
        Object model = new Object();
        subject.refresh(model);

        verify(dao).refresh(model);
    }
}