package whois.core.framework;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Enricher {
    void enrich(WhoisObject whoisObject, Reporter reporter);
}
