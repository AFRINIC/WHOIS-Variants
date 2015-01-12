package whois.core.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;
import whois.core.api.Observer;
import whois.core.api.Store;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by yogesh on 12/18/14.
 */
@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class SpringDaoTest extends AbstractDatabaseTestCase {

    @Inject
    private Store subject;

    @Inject
    private Observer observer;

    @Test
    public void testQuery() {
        Map<String, String> expectedDataA = new LinkedHashMap<String, String>();
        expectedDataA.put("a", "b");
        Map<String, String> expectedDataC = new LinkedHashMap<String, String>();
        expectedDataC.put("c", "d");
        assertDataEquals(expectedDataA, subject, "a:b");
        assertDataEquals(expectedDataC, subject, "c:d");
    }

    @Test
    public void testUpdate() {
        RpslWhoisObject rwo = new RpslWhoisObject();
        rwo.put("a", " b");
        rwo.put("b", "x");
        subject.persist(rwo, observer);

        Map<String, String> expectedData = new LinkedHashMap<String, String>();
        expectedData.put("a", "b");
        expectedData.put("b", "x");
        assertDataEquals(expectedData, subject, " a: b  ");
    }

    @Test
    public void testDelete() {
        assertNotNull(subject.load("a:b", observer));
        subject.delete("a:b", observer);
        assertNull(subject.load("a:b", observer));
    }
}
