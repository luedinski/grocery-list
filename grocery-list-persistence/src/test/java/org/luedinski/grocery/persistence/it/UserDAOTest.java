package org.luedinski.grocery.persistence.it;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAOTest extends AbstractIntegrationTest {

    @Autowired
    private Dao<UserDAO, Integer> userDao;

    @Test
    public void testCreateUser() throws Exception {
        UserDAO userDAO = new UserDAO("peter", "1234");
        int i = userDao.create(userDAO);
        Assertions.assertThat(i).isEqualByComparingTo(1);
        Assertions.assertThat(userDAO.getId()).isGreaterThan(0);
        Assertions.assertThat(userDAO.getName()).isEqualTo("peter");
        Assertions.assertThat(userDAO.getPassword()).isEqualTo("1234");
        //        Assertions.assertThat(new ArrayList<>(userDAO.getLists())).isEmpty();
        //
        //        TodoList list = todoListService.create("My first list", userDAO);
        //        Assertions.assertThat(list.getName()).isEqualTo("My first list");
        //        Assertions.assertThat(list.getUser().getId()).isEqualTo("Lüder");
        //
        //        Assertions.assertThat(new ArrayList<>(list.getTasks())).isEmpty();
        //        Task task1 = taskService.create("Einkaufen", list);
        //        Assertions.assertThat(task1.getName()).isEqualTo("Einkaufen");
        //
        //        Assertions.assertThat(new ArrayList<>(list.getTasks()))
        //                .hasSize(1)
        //                .element(0)
        //                .extracting(Task::getName, Task::isDone)
        //                .containsExactly("Einkaufen", false);
        //
        //        Task task2 = taskService.create("Tanken", list);
        //        Assertions.assertThat(task2.getName()).isEqualTo("Tanken");
        //
        //        Assertions.assertThat(new ArrayList<>(list.getTasks()))
        //                .hasSize(2)
        //                .extracting(Task::getName, Task::isDone)
        //                .containsExactly(Tuple.tuple("Einkaufen", false), Tuple.tuple("Tanken", false));

    }

    @Test
    public void testChangeName() throws Exception {
        UserDAO userDAO = new UserDAO("peter", "1234");
        userDao.create(userDAO);
        Assertions.assertThat(userDAO.getName()).isEqualTo("peter");

        userDAO.setName("horst");
        userDAO.setPassword("4321");
        userDao.update(userDAO);

        UserDAO updatedUserDAO = userDao.queryForId(userDAO.getId());
        Assertions.assertThat(updatedUserDAO).extracting(UserDAO::getId, UserDAO::getName, UserDAO::getPassword).containsExactly(userDAO.getId(), "horst", "4321");

    }
}
