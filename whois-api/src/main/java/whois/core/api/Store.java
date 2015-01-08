package whois.core.api;

import java.util.List;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Store {
    void persist(WhoisObject whoisObject, Reporter reporter);

    List<WhoisObject> load(Class clazz);

    WhoisObject load(Class clazz, String key);
}
