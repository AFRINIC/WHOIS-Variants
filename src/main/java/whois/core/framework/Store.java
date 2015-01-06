package whois.core.framework;

import java.util.List;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Store {
    void persist(WhoisObject whoisObject, Reporter reporter);

    List<WhoisObject> load(Class clazz);
}
