package whois.core.password;

import whois.core.api.Credential;
import whois.core.model.rpsl.InvalidRpslLineException;

/**
 * Created by yogesh on 12/16/14.
 */
public class PasswordCredential implements Credential {

    private String key = "password";

    private String password;

    public PasswordCredential(String line) {
        parse(line);
    }

    public void parse(String line) {
        String[] lineVals = line.split(":");
        if (lineVals.length != 2) {
            throw new InvalidRpslLineException(line);
        } else if (key.equalsIgnoreCase(lineVals[0].trim())) {
            this.password = lineVals[1];
        } else {
            throw new InvalidRpslLineException(line);
        }

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
