package whois.core.socket;

import whois.core.api.*;

import javax.inject.Named;
import java.util.Collection;

/**
 * Created by yogesh on 12/22/14.
 */
@Named
public class NoopBusinessRuleEngine implements Authenticator, Validator, Enricher {
    public void authenticate(WhoisObject whoisObject, Collection<Credential> allCredentials, Observer observer) {
    }

    public void enrich(WhoisObject whoisObject, Observer observer) {
    }

    public void validate(WhoisObject whoisObject, Observer observer) {
    }
}
