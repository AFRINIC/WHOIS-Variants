package whois.core.model.rpsl;

import whois.core.api.CommandException;

/**
 * Created by yogesh on 12/16/14.
 */
class InvalidRpslObjectException extends CommandException {

    public InvalidRpslObjectException(String text) {
        super("Invalid RPSL object: \"" + text + "\"");
    }
}
