package whois.core.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 1/9/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class DeleteCommandTest extends AbstractDatabaseTestCase {

    @Inject
    private DeleteCommand subject;

    @Test
    public void testDelete() {
        subject.setParameter("a:b");
        subject.run();
        assertEquals("Successfully deleted\na:              b", subject.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotFound() {
        subject.setParameter("a:b");
        subject.run();
        subject.setParameter("a:b");
        subject.run();

    }
}
