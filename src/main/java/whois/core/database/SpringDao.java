package whois.core.database;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import whois.core.framework.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogesh on 12/18/14.
 */
@Named
public class SpringDao implements Store {

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private ModelAdapter modelAdapter;

    @Override
    public void persist(WhoisObject whoisObject, Reporter reporter) {
        Session session = sessionFactory.openSession();
        StoreModel storeModel = modelAdapter.convertToStoreModel(whoisObject);
        session.saveOrUpdate(storeModel);
        session.flush();
        if (reporter != null) {
            reporter.report("Successfully added:\n" + whoisObject.toString());
        }
        session.close();
    }

    @Override
    public List<WhoisObject> load(Class clazz) {
        Session session = sessionFactory.openSession();
        Criteria c = session.createCriteria(clazz);
        List<StoreModel> result = c.list();
        List<WhoisObject> retVal = new ArrayList<WhoisObject>();
        for (StoreModel storeModel : result) {
            retVal.add(modelAdapter.convertToWhoisObject(storeModel));
        }
        session.close();
        return retVal;
    }

    @Override
    public WhoisObject load(Class clazz, String key) {
        Session session = sessionFactory.openSession();
        Criteria c = session.createCriteria(clazz);
        StoreModel storeModel = (StoreModel) session.get(clazz, key);
        WhoisObject retVal = modelAdapter.convertToWhoisObject(storeModel);
        session.close();
        return retVal;
    }
}
