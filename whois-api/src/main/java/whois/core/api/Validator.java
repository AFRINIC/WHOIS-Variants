package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
public interface Validator {
    void validate(WhoisObject whoisObject, Observer observer);
}
