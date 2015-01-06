package whois.core.model.blob;

import whois.core.framework.ModelAdapter;
import whois.core.framework.StoreModel;
import whois.core.framework.WhoisObject;
import whois.core.framework.WhoisObjectAdapter;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yogesh on 12/23/14.
 */
@Named
public class BlobModelAdapter implements ModelAdapter {

    @Inject
    private WhoisObjectAdapter whoisObjectAdapter;

    private Map<String, Class> keyToModelClassMap;

    public BlobModelAdapter() {
        keyToModelClassMap = new HashMap<String, Class>();
        keyToModelClassMap.put("blob", BlobModel.class);
        keyToModelClassMap.put("store", StoreModel.class);
    }

    @Override
    public WhoisObject convertToWhoisObject(StoreModel storeModel) {
        if (storeModel instanceof BlobModel) {
            BlobModel blobModel = (BlobModel) storeModel;
            return whoisObjectAdapter.convertToWhoisObject(new String(blobModel.getObject()));
        }
        return null;
    }

    @Override
    public StoreModel convertToStoreModel(WhoisObject whoisObject) {
        if (whoisObject instanceof RpslWhoisObject) {
            Iterator<Map.Entry<String, String>> iterator = whoisObject.getKeyValueIterator();
            boolean processedFirst = false;
            StringBuilder sb = new StringBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (processedFirst) {
                    sb.append("\n").append(entry.getKey()).append(":").append(entry.getValue());
                } else {
                    sb.append(entry.getKey()).append(":").append(entry.getValue());
                    processedFirst = true;
                }
            }
            BlobModel model = new BlobModel();
            model.setObject(sb.toString().getBytes());
            return model;
        }
        return null;
    }

    @Override
    public Map<String, Class> getKeyToModelClassMap() {
        return keyToModelClassMap;
    }
}