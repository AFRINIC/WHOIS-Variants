package whois.core.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.DatabaseHelper;
import whois.core.model.blob.BlobModel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yogesh on 1/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class UpdateCommandTest {

    @Inject
    private DatabaseHelper dbHelper;

    @Inject
    private UpdateCommand subject;

    @Before
    public void setUp() {
        dbHelper.setUpData();
    }

    @Test
    public void testUpdate_001() {
        subject.setParameter("w:q");
        subject.run();

        Map<String, String> expectedData = new HashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("c", "d");
        expectedData.put("w", "q");
        dbHelper.assertDataEquals(expectedData, BlobModel.class);
    }

    @Test
    public void testUpdate_002() {
        subject.setParameter("e:r");
        subject.run();

        Map<String, String> expectedData = new HashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("c", "d");
        expectedData.put("w", "q");
        expectedData.put("e", "r");
        dbHelper.assertDataEquals(expectedData, BlobModel.class);
    }
}
