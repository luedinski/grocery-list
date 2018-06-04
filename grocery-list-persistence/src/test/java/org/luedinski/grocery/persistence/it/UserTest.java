package org.luedinski.grocery.persistence.it;

import com.j256.ormlite.dao.Dao;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.luedinski.grocery.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTest extends AbstractIntegrationTest {

    @Autowired
    private Dao<User, Integer> userDao;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("peter", "1234");
        int i = userDao.create(user);
        Assertions.assertThat(i).isEqualByComparingTo(1);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user.getName()).isEqualTo("peter");
        Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        //        Assertions.assertThat(new ArrayList<>(user.getLists())).isEmpty();
        //
        //        TodoList list = todoListService.create("My first list", user);
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
        User user = new User("peter", "1234");
        userDao.create(user);
        Assertions.assertThat(user.getName()).isEqualTo("peter");

        user.setName("horst");
        user.setPassword("4321");
        userDao.update(user);

        User updatedUser = userDao.queryForId(user.getId());
        Assertions.assertThat(updatedUser).extracting(User::getId, User::getName, User::getPassword).containsExactly(user.getId(), "horst", "4321");

    }
}
