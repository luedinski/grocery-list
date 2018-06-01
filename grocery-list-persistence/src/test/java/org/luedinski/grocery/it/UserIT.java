package org.luedinski.grocery.it;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.IdentifierInUseException;
import org.luedinski.grocery.context.GroceryListPersistenceSpringConfiguration;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GroceryListPersistenceSpringConfiguration.class})
@TestPropertySource("/META-INF/test-connection.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIT {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAll() throws Exception {

        User user = userService.create("Lüder", "12345");
        Assertions.assertThat(user.getId()).isEqualTo("Lüder");
        Assertions.assertThat(user.getPassword()).isNotEmpty();
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
    public void testDuplacteId() throws Exception {
        User user = userService.create("Lüder", "12345");
        Assertions.assertThat(user.getId()).isEqualTo("Lüder");
        Assertions.assertThat(user.getPassword()).isNotEmpty();
        //        Assertions.assertThat(new ArrayList<>(user.getLists())).isEmpty();

        Assertions.assertThatExceptionOfType(IdentifierInUseException.class)
                .isThrownBy(() -> userService.create("Lüder", "12345"))
                .withMessage("Element with id Lüder already exists.");

    }
}
