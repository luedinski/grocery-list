package org.luedinski.grocery.it;

import org.junit.jupiter.api.extension.ExtendWith;
import org.luedinski.grocery.context.GroceryListPersistenceSpringConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GroceryListPersistenceSpringConfiguration.class})
@TestPropertySource("/META-INF/test-connection.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
abstract class AbstractIntegrationTest {
    static final String TAG_NAME = "IT";
}
