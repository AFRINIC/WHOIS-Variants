package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Command<T, V> extends Runnable {
    void setParameter(T parameter);

    V getResult();
}
