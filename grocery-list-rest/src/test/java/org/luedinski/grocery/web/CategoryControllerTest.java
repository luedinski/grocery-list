package org.luedinski.grocery.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.luedinski.grocery.model.Category;
import org.luedinski.grocery.persistence.repo.CategoryRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {


    private MockMvc mockMvc;

    @Mock
    private CategoryRepository categoryRepository;

    private ObjectMapper objectMapper;

    @InjectMocks
    private CategoryController categoryController;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCategories() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/user/123/categories/")).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).isNotBlank();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Category.class);
        List<Category> list = objectMapper.readValue(content, collectionType);
        Assertions.assertThat(list).hasSize(1).element(0)
                .extracting(Category::getId, Category::getName, Category::getVersion)
                .containsExactly("123", "Misc", "1");
    }
}