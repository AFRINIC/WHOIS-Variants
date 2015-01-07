package whois.core.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;
import whois.core.framework.Store;
import whois.core.model.blob.BlobModel;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yogesh on 12/18/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class SpringDaoTest extends AbstractDatabaseTestCase {

    @Inject
    private Store subject;

    @Test
    public void testQuery() {
        Map<String, String> expectedDataA = new LinkedHashMap<String, String>();
        expectedDataA.put("a", "b");
        Map<String, String> expectedDataC = new LinkedHashMap<String, String>();
        expectedDataC.put("c", "d");
        assertDataEquals(expectedDataA, subject, BlobModel.class, "a:b");
        assertDataEquals(expectedDataC, subject, BlobModel.class, "c:d");
    }

    @Test
    public void testUpdate() {
        RpslWhoisObject rwo = new RpslWhoisObject();
        rwo.put("a", " b");
        rwo.put("b", "x");
        subject.persist(rwo, null);

        Map<String, String> expectedData = new LinkedHashMap<String, String>();
        expectedData.put("a", " b");
        expectedData.put("b", "x");
        assertDataEquals(expectedData, subject, BlobModel.class, "a: b");

        Map<String, String> expectedDataA = new LinkedHashMap<String, String>();
        expectedDataA.put("a", "b");
        assertDataEquals(expectedDataA, subject, BlobModel.class, "a:b");
    }
}
