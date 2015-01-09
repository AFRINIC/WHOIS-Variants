package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
public interface Enricher {
    void enrich(WhoisObject whoisObject, Observer observer);
}
