package whois.core.api;

/**
 * Created by yogesh on 12/23/14.
 */
@SuppressWarnings("UnusedParameters")
public interface ModelAdapter {
    WhoisObject convertToWhoisObject(StoreModel storeModel);

    StoreModel convertToStoreModel(WhoisObject whoisObject);

    Class getModelClass(@SuppressWarnings("SameParameterValue") String id);
}
