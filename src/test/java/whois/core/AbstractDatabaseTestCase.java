package whois.core;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import whois.core.framework.Store;
import whois.core.framework.WhoisObject;
import whois.core.model.rpsl.RpslWhoisObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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

    private IDataSet dataSet;

    private RpslWhoisObject whoisObjectA = null;

    private RpslWhoisObject whoisObjectC = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        dataSet = new FlatXmlDataSetBuilder().build(fileInputStream(getDatasetFilename()));
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

    protected String getDatasetFilename() {
        return "dbunit-dataset.xml";
    }


    protected void assertDataEquals(Map<String, String> expectedData, Store store, Class searchCriteria) {
        List<String> searchResult = new ArrayList<String>();
        List<String> matchedKeys = new ArrayList<String>();
        List<WhoisObject> result = store.load(searchCriteria);
        for (WhoisObject wo : result) {
            RpslWhoisObject rwo = (RpslWhoisObject) wo;
            searchResult.add(rwo.toString());
            for (Map.Entry<String, String> entry : expectedData.entrySet()) {
                if (rwo.get(entry.getKey()) != null) {
                    assertEquals(entry.getValue(), rwo.get(entry.getKey()));
                    matchedKeys.add(entry.getKey());
                }
            }
        }
        assertEquals("Search result: \n" + searchResult, expectedData.size(), result.size());
        assertEquals("Matched keys: " + matchedKeys, matchedKeys.size(), expectedData.size());
    }

    protected String toResult(RpslWhoisObject... rwos) {
        StringBuilder sb = new StringBuilder();
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
     * @param filename
     * @return
     */
    private InputStream fileInputStream(String filename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(filename);
    }

}
