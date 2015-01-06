package whois.core.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.DatabaseHelper;
import whois.core.framework.Store;
import whois.core.model.blob.BlobModel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yogesh on 12/18/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class SpringDaoTest {

    @Inject
    private DatabaseHelper dbHelper;

    @Inject
    private Store subject;

    @Before
    public void setUp() {
        dbHelper.setUpData();
    }

    @Test
    public void testPublic() {
        Map<String, String> expectedData = new HashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("c", "d");
        dbHelper.assertDataEquals(expectedData, BlobModel.class);
    }
}
