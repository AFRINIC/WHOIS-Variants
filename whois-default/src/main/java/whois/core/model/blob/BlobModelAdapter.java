package whois.core.model.blob;

import whois.core.api.ModelAdapter;
import whois.core.api.StoreModel;
import whois.core.api.WhoisObject;
import whois.core.api.WhoisObjectAdapter;
import whois.core.model.rpsl.RpslWhoisObject;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yogesh on 12/23/14.
 */
@Named
public class BlobModelAdapter implements ModelAdapter {

    @Inject
    private WhoisObjectAdapter whoisObjectAdapter;

    public WhoisObject convertToWhoisObject(StoreModel storeModel) {
        if (storeModel instanceof BlobModel) {
            BlobModel blobModel = (BlobModel) storeModel;
            return whoisObjectAdapter.convertToWhoisObject(new String(blobModel.getObject()));
        }
        return null;
    }

    public StoreModel convertToStoreModel(WhoisObject whoisObject) {
        if (whoisObject instanceof RpslWhoisObject) {
            Iterator<Map.Entry<String, String>> iterator = whoisObject.getKeyValueIterator();
            boolean processedFirst = false;
            StringBuilder id = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (processedFirst) {
                    sb.append("\n").append(entry.getKey()).append(":").append(entry.getValue());
                } else {
                    sb.append(entry.getKey()).append(":").append(entry.getValue());
                    id.append(entry.getKey()).append(":").append(entry.getValue());
                    processedFirst = true;
                }
            }
            BlobModel model = new BlobModel();
            model.setId(id.toString());
            model.setObject(sb.toString().getBytes());
            return model;
        }
        return null;
    }

    public Class getModelClass(String id) {
        return BlobModel.class;
    }
}
