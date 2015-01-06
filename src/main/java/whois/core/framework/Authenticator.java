package whois.core.framework;

import java.util.Collection;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Authenticator {
    void authenticate(WhoisObject whoisObject, Collection<Credential> allCredentials, Reporter reporter);
}
