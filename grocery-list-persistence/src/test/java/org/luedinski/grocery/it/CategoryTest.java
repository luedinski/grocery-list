package org.luedinski.grocery.it;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Tag(AbstractIntegrationTest.TAG_NAME)
public class CategoryTest extends AbstractIntegrationTest {
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
        user = userService.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Assertions.assertThat(new ArrayList<>(user.getCategories())).hasSize(1).element(0).extracting(Category::getName).containsExactly("Getränke");
    }
}
