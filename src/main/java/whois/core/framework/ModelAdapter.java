package whois.core.framework;

/**
 * Created by yogesh on 12/23/14.
 */
public interface ModelAdapter {
    WhoisObject convertToWhoisObject(StoreModel storeModel);

    StoreModel convertToStoreModel(WhoisObject whoisObject);

    Class getModelClass(String id);
}
