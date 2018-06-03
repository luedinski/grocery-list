package org.luedinski.grocery.it;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.context.GroceryListPersistenceSpringConfiguration;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GroceryListPersistenceSpringConfiguration.class})
@TestPropertySource("/META-INF/test-connection.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class CategoryIT {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    private User user;

    @BeforeEach
    void setUp() {
        user = userService.create("test", "pw");
    }

    @Test
    void testAddCategory() {
        Category getränke = categoryService.create("Getränke", user);
        Assertions.assertThat(getränke.getName()).isEqualTo("Getränke");
        user = userService.getById("test").orElseThrow(IllegalArgumentException::new);
        Assertions.assertThat(new ArrayList<>(user.getCategories())).hasSize(1).element(0).extracting(Category::getName).containsExactly("Getränke");
    }
}
