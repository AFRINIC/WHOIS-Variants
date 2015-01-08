package whois.core.auth;

import whois.core.api.Credential;
import whois.core.model.rpsl.InvalidRpslLineException;

/**
 * Created by yogesh on 12/16/14.
 */
public class PasswordCredential implements Credential {

    private static final String KEY = "password";

    private String password;

    public PasswordCredential(String line) {
        parse(line);
    }

    void parse(String line) {
        String[] lineVals = line.split(":");
        if (lineVals.length != 2) {
            throw new InvalidRpslLineException(line);
        } else if (KEY.equalsIgnoreCase(lineVals[0].trim())) {
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
