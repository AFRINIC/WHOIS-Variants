package whois.core.framework;

import java.util.Map;

/**
 * Created by yogesh on 12/23/14.
 */
public interface ModelAdapter {
    WhoisObject convertToWhoisObject(StoreModel storeModel);

    StoreModel convertToStoreModel(WhoisObject whoisObject);

    Map<String, Class> getKeyToModelClassMap();
}
