package whois.core.database;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import whois.core.api.*;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 12/18/14.
 */
@Named
public class SpringDao implements Store<String, WhoisObject> {

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private ModelAdapter modelAdapter;

    public void persist(WhoisObject model, Observer observer) {
        Session session = sessionFactory.openSession();
        StoreModel storeModel = modelAdapter.convertToStoreModel(model);
        session.saveOrUpdate(storeModel);
        session.flush();
        observer.notify("Successfully added:\n" + model.toString());
        session.close();
    }

    /**
     * Before querying, Key is normalized according to rules in <code>RpslWhoisObject#put</code>.
     *
     * @param key      Lookup key
     * @param observer Observer
     * @return WHOIS Object found
     */
    public WhoisObject load(String key, Observer observer) {
        key = key.trim().replaceFirst(":\\s+", ":");
        Session session = sessionFactory.openSession();
        StoreModel storeModel = (StoreModel) session.get(modelAdapter.getModelClass(null), key);
        WhoisObject retVal = null;
        if (storeModel != null) {
            retVal = modelAdapter.convertToWhoisObject(storeModel);
            observer.notify("Found:\n" + retVal.toString());
        } else {
            observer.notify("Object with key \"" + key + "\" not found");
        }
        session.close();
        return retVal;
    }

    public void delete(String key, Observer observer) {
        key = key.trim().replaceFirst(":\\s+", ":");
        Session session = sessionFactory.openSession();
        StoreModel storeModel = (StoreModel) session.get(modelAdapter.getModelClass(null), key);
        if (storeModel != null) {
            session.delete(storeModel);
            session.flush();
            WhoisObject whoisObject = modelAdapter.convertToWhoisObject(storeModel);
            observer.notify("Successfully deleted:\n" + whoisObject.toString());
        } else {
            observer.notify("Object with key \"" + key + "\" not found");
        }
        session.close();
    }
}
