package whois.core.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.DatabaseHelper;
import whois.core.framework.Store;
import whois.core.model.blob.BlobModel;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 1/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class QueryCommandTest {

    @Inject
    private Store store;

    @Inject
    private DatabaseHelper dbHelper;

    @Inject
    private QueryCommand subject;

    @Before
    public void setUp() {
        dbHelper.setUpData();
    }

    @Test
    public void testQuery() {
        subject.setParameter("blob");
        subject.run();

        assertEquals(2, store.load(BlobModel.class).size());
        assertEquals(
                dbHelper.toResult(dbHelper.getWhoisObject1(), dbHelper.getWhoisObject2()),
                subject.getResult());

    }
}
