package whois.core.model.rpsl;

import whois.core.framework.CommandException;

/**
 * Created by yogesh on 12/16/14.
 */
public class InvalidRpslObjectException extends CommandException {

    public InvalidRpslObjectException(String text) {
        super("Invalid RPSL object: \"" + text + "\"");
    }
}
