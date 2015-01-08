package whois.core.api;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yogesh on 12/15/14.
 */
public interface WhoisObject {
    Iterator<Map.Entry<String, String>> getKeyValueIterator();
}
