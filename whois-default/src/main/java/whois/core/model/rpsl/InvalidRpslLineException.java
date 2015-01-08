package whois.core.model.rpsl;

import whois.core.api.CommandException;

/**
 * Created by yogesh on 12/16/14.
 */
public class InvalidRpslLineException extends CommandException {

    public InvalidRpslLineException(String line) {
        super("Invalid RPSL line: \"" + line + "\"");
    }
}
