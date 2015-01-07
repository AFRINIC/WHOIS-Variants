package whois.core.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 1/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class QueryCommandTest extends AbstractDatabaseTestCase {

    @Inject
    private QueryCommand subject;

    @Test
    public void testQuery_001() {
        subject.setParameter("a:b");
        subject.run();

        assertEquals(
                toResult(getWhoisObjectA()),
                subject.getResult());
    }

    @Test
    public void testQuery_002() {
        subject.setParameter("c:d");
        subject.run();

        assertEquals(
                toResult(getWhoisObjectC()),
                subject.getResult());
    }
}
