package org.luedinski.grocery.it;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.model.User;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CategoryTest extends AbstractIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    private User user;

    @Before
    public void setUp() {
        user = userService.create("test", "pw");
    }

    @Test
    public void testAddCategory() {
        Category getränke = categoryService.create("Getränke", user);
        Assertions.assertThat(getränke.getName()).isEqualTo("Getränke");
        user = userService.getById(user.getId()).orElseThrow(IllegalArgumentException::new);
        Assertions.assertThat(new ArrayList<>(user.getCategories())).hasSize(1).element(0).extracting(Category::getName).containsExactly("Getränke");
    }
}
