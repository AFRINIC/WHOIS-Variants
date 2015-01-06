package whois.core.model.rpsl;

/**
 * Created by yogesh on 12/16/14.
 */
public class RpslObjectNotCleanException extends RuntimeException {

    public RpslObjectNotCleanException(String text) {
        super("RPSL object not cleanLocalTokens: \"" + text + "\"");
    }
}
