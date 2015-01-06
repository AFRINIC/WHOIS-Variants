package whois.core.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.DatabaseHelper;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 1/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class CommandIntegrationTest {

    @Inject
    private DatabaseHelper dbHelper;

    @Inject
    private QueryCommand queryCommand;

    @Inject
    private UpdateCommand updateCommand;

    private RpslWhoisObject whoisObject3 = null;

    private RpslWhoisObject whoisObject4 = null;

    @Before
    public void setUp() {
        dbHelper.setUpData();
        whoisObject3 = new RpslWhoisObject();
        whoisObject3.put("w", "q");
        whoisObject4 = new RpslWhoisObject();
        whoisObject4.put("a", "s");
    }

    @Test
    public void testIntegration_001() {
        updateCommand.setParameter("w:q");
        updateCommand.run();
        queryCommand.setParameter("blob");
        queryCommand.run();

        assertEquals(
                dbHelper.toResult(dbHelper.getWhoisObject1(), dbHelper.getWhoisObject2(), whoisObject3),
                queryCommand.getResult());
    }

    @Test
    public void testIntegration_002() {
        updateCommand.setParameter("a:s");
        updateCommand.run();
        queryCommand.setParameter("blob");
        queryCommand.run();

        assertEquals(
                dbHelper.toResult(dbHelper.getWhoisObject1(), dbHelper.getWhoisObject2(), whoisObject3, whoisObject4),
                queryCommand.getResult());
    }
}
