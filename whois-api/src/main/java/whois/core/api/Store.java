package whois.core.api;

/**
 * Created by yogesh on 12/15/14.
 */
public interface Store<T, V> {
    void persist(V model, Observer observer);

    V load(T key, Observer observer);

    void delete(T key, Observer observer);
}
