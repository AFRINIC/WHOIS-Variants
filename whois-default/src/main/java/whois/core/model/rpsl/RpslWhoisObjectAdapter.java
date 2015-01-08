package whois.core.model.rpsl;

import whois.core.api.Credential;
import whois.core.api.WhoisObject;
import whois.core.api.WhoisObjectAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by yogesh on 12/16/14.
 */
@Named
public class RpslWhoisObjectAdapter implements WhoisObjectAdapter {

    @Inject
    private RpslCredentialFilterChain rpslCredentialFilterChain;

    public Collection<Credential> extractGlobalTokens(String rpslObjectS) {
        return rpslCredentialFilterChain.extractGlobalTokens(rpslObjectS);
    }

    public String cleanGlobalTokens(String rpslObjectS) {
        return rpslCredentialFilterChain.cleanGlobalTokens(rpslObjectS);
    }

    public Collection<String> split(String rpslObjectS) {
        return splitMultipleRpsl(rpslObjectS);
    }

    public Collection<Credential> extractLocalTokens(String rpslObjectS) {
        validateSingleRpslObject(rpslObjectS);
        return rpslCredentialFilterChain.extractLocalTokens(rpslObjectS);
    }

    public String cleanLocalTokens(String rpslObjectS) {
        validateSingleRpslObject(rpslObjectS);
        return rpslCredentialFilterChain.cleanLocalTokens(rpslObjectS);
    }

    public WhoisObject convertToWhoisObject(String rpslObjectS) {
        validateCleanSingleRpslObject(rpslObjectS);
        if (rpslObjectS != null) {
            rpslObjectS = rpslCleanBlankLines(rpslObjectS);
            Scanner scanner = new Scanner(rpslObjectS);
            RpslWhoisObject rpslWhoisObject = new RpslWhoisObject();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(":");
                if (splitLine.length != 2) {
                    throw new InvalidRpslLineException(line);
                }
                rpslWhoisObject.put(splitLine[0], splitLine[1]);
            }
            return rpslWhoisObject;

        }
        return null;
    }

    /**
     * Split multiple RPSL objects over blank lines
     *
     * @param rpslObjectS String representation of multiple RPSL objects
     * @return Collection of split RPSL objects
     */
    static Collection<String> splitMultipleRpsl(String rpslObjectS) {
        if (rpslObjectS != null) {
            rpslObjectS = rpslCleanBlankLines(rpslObjectS);
            return Arrays.asList(rpslObjectS.split("\n\n"));
        }
        return null;
    }

    /**
     * Remove space and tabs from blank lines and blank lines at the top of RPSL
     *
     * @param rpsl RPSL to cleanLocalTokens
     * @return Clean RPSL
     */
    static String rpslCleanBlankLines(String rpsl) {
        return rpsl.replaceAll("\n\\s+\n", "\n\n").replaceAll("^\\s+\n", "");
    }

    /**
     * Throw an exception in case the text is not a valid single RPSL object
     *
     * @param rpslObjectS RPSL
     */
    final void validateSingleRpslObject(String rpslObjectS) {
        if (rpslObjectS != null) {
            String rpslObjectS2 = rpslCleanBlankLines(rpslObjectS);
            if (rpslObjectS2.contains("\n\n")) {
                throw new InvalidRpslObjectException(rpslObjectS);
            }
        }
    }

    /**
     * Throw an exception in case the text is not a cleanLocalTokens single RPSL object
     *
     * @param rpslObjectS RPSL
     */
    final void validateCleanSingleRpslObject(String rpslObjectS) {
        validateSingleRpslObject(rpslObjectS);
        if (rpslObjectS != null) {
            if (rpslCredentialFilterChain.extractLocalTokens(rpslObjectS) != null) {
                throw new RpslObjectNotCleanException(rpslObjectS);
            }
        }
    }
}
