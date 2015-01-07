package whois.core.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;
import whois.core.framework.Store;
import whois.core.model.blob.BlobModel;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yogesh on 1/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class UpdateCommandTest extends AbstractDatabaseTestCase {

    @Inject
    private Store store;

    @Inject
    private UpdateCommand subject;

    @Test
    public void testUpdate_001() {
        subject.setParameter("w:q");
        subject.run();

        Map<String, String> expectedData = new HashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("c", "d");
        expectedData.put("w", "q");
        assertDataEquals(expectedData, store, BlobModel.class);
    }

    @Test
    public void testUpdate_002() {
        subject.setParameter("w:q");
        subject.run();
        subject.setParameter("e:r");
        subject.run();

        Map<String, String> expectedData = new HashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("c", "d");
        expectedData.put("w", "q");
        expectedData.put("e", "r");
        assertDataEquals(expectedData, store, BlobModel.class);
    }
}
