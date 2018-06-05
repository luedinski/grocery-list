package org.luedinski.grocery.web;

import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.service.CategoryService;
import org.luedinski.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/user/{userId}/categories/", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategories(@PathVariable String userId) {
        return Collections.emptyList();
    }

}
