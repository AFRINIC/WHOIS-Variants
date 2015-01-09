package whois.core;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import whois.core.api.Observer;
import whois.core.api.Store;
import whois.core.api.WhoisObject;
import whois.core.model.rpsl.RpslWhoisObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yogesh on 1/7/15.
 */
public abstract class AbstractDatabaseTestCase implements InitializingBean {

    @Value("${jdbc.databaseUrl}")
    private String databaseUrl;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    private IDatabaseTester databaseTester;

    private RpslWhoisObject whoisObjectA = null;

    private RpslWhoisObject whoisObjectC = null;

    public void afterPropertiesSet() throws Exception {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(fileInputStream(getDatasetFilename()));
        databaseTester = new JdbcDatabaseTester(driverClassName, databaseUrl, username, password);
        databaseTester.setDataSet(dataSet);
        whoisObjectA = new RpslWhoisObject();
        whoisObjectA.put("a", "b");
        whoisObjectC = new RpslWhoisObject();
        whoisObjectC.put("c", "d");
    }

    @Before
    public void setUpDatabaseTester() throws Exception {
        databaseTester.onSetup();
    }

    @After
    public void tearDownDatabaseTester() throws Exception {
        databaseTester.onTearDown();
    }

    @SuppressWarnings({"WeakerAccess", "SameReturnValue"})
    public String getDatasetFilename() {
        return "dbunit-dataset.xml";
    }


    protected void assertDataEquals(Map<String, String> expectedData, Store store, Class searchCriteria, String key) {
        List<String> matchedKeys = new ArrayList<String>();
        WhoisObject wo = store.load(searchCriteria, key, new Observer() {
            public void notify(String message) {
            }

            public String report() {
                return null;
            }
        });
        assertNotNull(wo);
        RpslWhoisObject rwo = (RpslWhoisObject) wo;
        for (Map.Entry<String, String> entry : expectedData.entrySet()) {
            if (rwo.get(entry.getKey()) != null) {
                assertEquals(entry.getValue(), rwo.get(entry.getKey()));
                matchedKeys.add(entry.getKey());
            }
        }
        assertEquals("Matched keys: " + matchedKeys, matchedKeys.size(), expectedData.size());
    }

    protected String toResult(RpslWhoisObject... rwos) {
        StringBuilder sb = new StringBuilder("Found:\n");
        for (RpslWhoisObject rwo : rwos) {
            sb.append(rwo.toString()).append("\n");
        }
        return sb.toString();
    }

    protected RpslWhoisObject getWhoisObjectA() {
        return whoisObjectA;
    }

    protected RpslWhoisObject getWhoisObjectC() {
        return whoisObjectC;
    }

    /**
     * Look for file on classpath
     *
     * @param filename File name.
     * @return Input stream
     */
    private InputStream fileInputStream(String filename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(filename);
    }

}
