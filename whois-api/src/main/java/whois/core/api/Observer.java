package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Observer {
    void notify(String message);

    String report();
}
