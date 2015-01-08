package whois.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import whois.core.api.QueryCommandTest;
import whois.core.api.UpdateCommandTest;
import whois.core.database.SpringDaoTest;
import whois.core.model.rpsl.BlobModelAdapterTest;
import whois.core.model.rpsl.RpslWhoisObjectAdapterTest;

/**
 * Created by yogesh on 12/23/14.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SpringDaoTest.class,
        BlobModelAdapterTest.class,
        RpslWhoisObjectAdapterTest.class,
        QueryCommandTest.class,
        UpdateCommandTest.class})
public class UnitTestSuite {
}
