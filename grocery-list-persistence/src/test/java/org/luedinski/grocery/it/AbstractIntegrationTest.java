package org.luedinski.grocery.it;

import org.junit.runner.RunWith;
import org.luedinski.grocery.context.GroceryListPersistenceSpringConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GroceryListPersistenceSpringConfiguration.class})
@TestPropertySource("/META-INF/test-connection.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class AbstractIntegrationTest {
    static final String TAG_NAME = "IT";
}
