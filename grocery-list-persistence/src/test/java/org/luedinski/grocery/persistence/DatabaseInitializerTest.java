package org.luedinski.grocery.persistence;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.dao.UserDAO;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseInitializerTest {

    @Test
    public void testInit() throws Exception {
        ConnectionSource source = mock(ConnectionSource.class);
        DatabaseType dbType = mock(DatabaseType.class);
        when(source.getDatabaseType()).thenReturn(dbType);
        when(dbType.extractDatabaseTableConfig(source, UserDAO.class)).thenThrow(new SQLException("test error"));
        DatabaseInitializer subject = new DatabaseInitializer(source, UserDAO.class);
        Assertions.assertThatExceptionOfType(IllegalStateException.class).isThrownBy(subject::init)
                .withCauseExactlyInstanceOf(SQLException.class)
                .withMessageContaining("test error");
    }
}