package whois.core.framework;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Validator {
    void validate(WhoisObject whoisObject, Reporter reporter);
}
