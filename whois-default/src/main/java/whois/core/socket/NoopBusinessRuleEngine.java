package whois.core.socket;

import whois.core.api.*;

import javax.inject.Named;
import java.util.Collection;

/**
 * Created by yogesh on 12/22/14.
 */
@Named
public class NoopBusinessRuleEngine implements Authenticator, Validator, Enricher {
    @Override
    public void authenticate(WhoisObject whoisObject, Collection<Credential> allCredentials, Reporter reporter) {
    }

    @Override
    public void enrich(WhoisObject whoisObject, Reporter reporter) {
    }

    @Override
    public void validate(WhoisObject whoisObject, Reporter reporter) {
    }
}
