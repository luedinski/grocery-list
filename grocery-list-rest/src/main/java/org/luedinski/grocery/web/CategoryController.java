package org.luedinski.grocery.web;

import org.luedinski.grocery.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CategoryController {

    @RequestMapping(value = "/user/{userId}/categories/", method = RequestMethod.GET)
    @ResponseBody
    public List<Category> getCategories(@PathVariable String userId) {
        return Collections.singletonList(new Category("123", "Misc", "1"));
    }

}
