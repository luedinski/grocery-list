package org.luedinski.grocery.persistence.it;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import org.junit.runner.RunWith;
import org.luedinski.grocery.persistence.context.GroceryListPersistenceSpringConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {GroceryListPersistenceSpringConfiguration.class})
@TestPropertySource("/META-INF/test-grocery-list-persistence.properties")
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
abstract class AbstractIntegrationTest {
}
