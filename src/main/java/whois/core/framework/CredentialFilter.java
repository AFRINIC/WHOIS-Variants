package whois.core.framework;

import java.util.Collection;

/**
 * Created by yogesh on 12/17/14.
 */
public interface CredentialFilter {
    Collection<Credential> extractGlobalTokens(String rpslObjectS);

    Collection<Credential> extractLocalTokens(String rpslObjectS);

    String cleanGlobalTokens(String rpslObjectS);

    String cleanLocalTokens(String rpslObjectS);
}
