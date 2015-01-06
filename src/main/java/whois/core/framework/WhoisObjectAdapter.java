package whois.core.framework;

import java.util.Collection;

/**
 * Created by yogesh on 12/15/14.
 */
public interface WhoisObjectAdapter {
    Collection<Credential> extractGlobalTokens(String rpslObjectS);

    String cleanGlobalTokens(String rpslObjectS);

    Collection<String> split(String rpslObjectS);

    Collection<Credential> extractLocalTokens(String rpslObjectS);

    String cleanLocalTokens(String rpslObjectS);

    WhoisObject convertToWhoisObject(String rpslObjectS);
}
