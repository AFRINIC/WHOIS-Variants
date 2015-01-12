package whois.core.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yogesh on 1/6/15.
 */
@SuppressWarnings("unchecked")
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

        Map<String, String> expectedData = new LinkedHashMap<String, String>();
        expectedData.put("w", "q");
        assertDataEquals(expectedData, store, "w:q");
    }

    @Test
    public void testUpdate_002() {
        subject.setParameter("w:q");
        subject.run();
        subject.setParameter("w:q\ne:r");
        subject.run();

        Map<String, String> expectedData = new LinkedHashMap<String, String>();
        expectedData.put("w", "q");
        expectedData.put("e", "r");
        assertDataEquals(expectedData, store, "w:q");
    }
}
