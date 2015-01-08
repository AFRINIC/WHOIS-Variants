package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Command extends Runnable {
    void setParameter(String parameter);

    String getResult();
}
