package whois.core.api;

import java.util.Collection;

/**
 * Created by yogesh on 12/15/14.
 */
@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
public interface Authenticator {
    void authenticate(WhoisObject whoisObject, Collection<Credential> allCredentials, Reporter reporter);
}
