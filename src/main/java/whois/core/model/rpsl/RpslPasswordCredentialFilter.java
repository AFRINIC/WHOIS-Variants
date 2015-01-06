package whois.core.model.rpsl;

import whois.core.framework.Credential;
import whois.core.framework.CredentialFilter;
import whois.core.password.PasswordCredential;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by yogesh on 12/17/14.
 */
@Named
public class RpslPasswordCredentialFilter implements CredentialFilter {

    @Override
    public Collection<Credential> extractGlobalTokens(String rpslObjectS) {
        if (rpslObjectS != null) {
            rpslObjectS = RpslWhoisObjectAdapter.rpslCleanBlankLines(rpslObjectS);
            Collection<String> splitRpsl = RpslWhoisObjectAdapter.splitMultipleRpsl(rpslObjectS);
            Collection<Credential> allCrs = new ArrayList<Credential>();
            for (String oneRpsl : splitRpsl) {
                if (cleanLocalTokens(oneRpsl).trim().isEmpty()) {
                    allCrs.addAll(extractCredentials(oneRpsl));
                }
            }
            return allCrs.isEmpty() ? null : allCrs;
        }
        return null;
    }

    @Override
    public Collection<Credential> extractLocalTokens(String rpslObjectS) {
        if (rpslObjectS != null) {
            rpslObjectS = RpslWhoisObjectAdapter.rpslCleanBlankLines(rpslObjectS);
            return extractCredentials(rpslObjectS);
        }
        return null;
    }

    @Override
    public String cleanGlobalTokens(String rpslObjectS) {
        if (rpslObjectS != null) {
            rpslObjectS = RpslWhoisObjectAdapter.rpslCleanBlankLines(rpslObjectS);
            Collection<String> splitRpsl = RpslWhoisObjectAdapter.splitMultipleRpsl(rpslObjectS);
            StringBuilder concatUtil = new StringBuilder();
            boolean processedFirstOne = false;
            for (String oneRpsl : splitRpsl) {
                if (!cleanLocalTokens(oneRpsl).trim().isEmpty()) {
                    if (processedFirstOne) {
                        concatUtil.append("\n\n").append(oneRpsl);
                    } else {
                        concatUtil.append(oneRpsl);
                        processedFirstOne = true;
                    }
                }
            }
            return concatUtil.toString();
        }
        return null;
    }

    @Override
    public String cleanLocalTokens(String rpslObjectS) {
        Scanner scanner = new Scanner(rpslObjectS);
        StringBuilder concatUtil = new StringBuilder();
        boolean processedFirstOne = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
                new PasswordCredential(line);
            } catch (InvalidRpslLineException e) {
                if (processedFirstOne) {
                    concatUtil.append("\n").append(line);
                } else {
                    concatUtil.append(line);
                    processedFirstOne = true;
                }
            }
        }
        return concatUtil.toString();
    }

    /**
     * Extract all credentials from RPSL
     *
     * @param rpsl RPSL to extractLocalTokens from
     * @return
     */
    public Collection<Credential> extractCredentials(String rpsl) {
        Scanner scanner = new Scanner(rpsl);
        Collection<Credential> credentialCollection = new ArrayList<Credential>();
        while (scanner.hasNextLine()) {
            try {
                credentialCollection.add(new PasswordCredential(scanner.nextLine()));
            } catch (InvalidRpslLineException e) {
            }
        }
        return credentialCollection.isEmpty() ? null : credentialCollection;
    }


}
