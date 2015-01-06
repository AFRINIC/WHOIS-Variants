package whois.core.model.rpsl;

import whois.core.framework.Credential;
import whois.core.framework.CredentialFilter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yogesh on 12/17/14.
 */
@Named
public class RpslCredentialFilterChain {

    @Inject
    Collection<CredentialFilter> credentialFilterCollection;

    public Collection<Credential> extractGlobalTokens(String rpslObjectS) {
        Collection<Credential> allCredentials = new ArrayList<Credential>();
        String rpslCopy = new String(rpslObjectS);
        for (CredentialFilter currentFilter : credentialFilterCollection) {
            Collection<Credential> globalCredentials = currentFilter.extractGlobalTokens(rpslCopy);
            if (globalCredentials != null) {
                allCredentials.addAll(globalCredentials);
                rpslCopy = currentFilter.cleanGlobalTokens(rpslCopy);
            }
        }
        return allCredentials.isEmpty() ? null : allCredentials;
    }

    public Collection<Credential> extractLocalTokens(String rpslObjectS) {
        Collection<Credential> allCredentials = new ArrayList<Credential>();
        String rpslCopy = new String(rpslObjectS);
        for (CredentialFilter currentFilter : credentialFilterCollection) {
            Collection<Credential> localCredentials = currentFilter.extractLocalTokens(rpslCopy);
            if (localCredentials != null) {
                allCredentials.addAll(localCredentials);
                rpslCopy = currentFilter.cleanLocalTokens(rpslCopy);
            }
        }
        return allCredentials.isEmpty() ? null : allCredentials;
    }

    public String cleanLocalTokens(String rpslObjectS) {
        String rpslCopy = new String(rpslObjectS);
        for (CredentialFilter currentFilter : credentialFilterCollection) {
            rpslCopy = currentFilter.cleanLocalTokens(rpslCopy);
        }
        return rpslCopy;
    }

    public String cleanGlobalTokens(String rpslObjectS) {
        String rpslCopy = new String(rpslObjectS);
        for (CredentialFilter currentFilter : credentialFilterCollection) {
            rpslCopy = currentFilter.cleanGlobalTokens(rpslCopy);
        }
        return rpslCopy;
    }
}
