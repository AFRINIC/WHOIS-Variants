package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Store {
    void persist(WhoisObject whoisObject, Observer observer);

    WhoisObject load(Class clazz, String key, Observer observer);

    void delete(Class clazz, String key, Observer observer);
}
