package whois.core;

import whois.core.framework.Store;
import whois.core.framework.WhoisObject;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 1/6/15.
 */
@Named
public class DatabaseHelper {

    @Inject
    private Store store;

    private boolean isDataSetup = false;

    private RpslWhoisObject whoisObject1 = null;

    private RpslWhoisObject whoisObject2 = null;

    public void setUpData() {
        if (!isDataSetup) {
            whoisObject1 = new RpslWhoisObject();
            whoisObject1.put("a", "b");
            whoisObject2 = new RpslWhoisObject();
            whoisObject2.put("c", "d");
            store.persist(whoisObject1, null);
            store.persist(whoisObject2, null);
            isDataSetup = true;
        }

    }

    public void assertDataEquals(Map<String, String> expectedData, Class searchCriteria) {
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

    public String toResult(RpslWhoisObject... rwos) {
        StringBuilder sb = new StringBuilder();
        for (RpslWhoisObject rwo : rwos) {
            sb.append(rwo.toString()).append("\n");
        }
        return sb.toString();
    }

    public RpslWhoisObject getWhoisObject1() {
        return whoisObject1;
    }

    public RpslWhoisObject getWhoisObject2() {
        return whoisObject2;
    }

}
