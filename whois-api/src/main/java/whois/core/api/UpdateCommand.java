package whois.core.api;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yogesh on 12/15/14.
 */
@Named
@Scope("prototype")
public class UpdateCommand implements Command<String, String> {

    private String rpsl;

    @Inject
    private Observer observer;

    @Inject
    private WhoisObjectAdapter whoisObjectAdapter;

    @Inject
    private Authenticator authenticator;

    @Inject
    private Validator validator;

    @Inject
    private Enricher enricher;

    @Inject
    private Store store;

    @Transactional
    public void run() {
        Collection<Credential> globalCredentials = whoisObjectAdapter.extractGlobalTokens(rpsl);
        rpsl = whoisObjectAdapter.cleanGlobalTokens(rpsl);
        Collection<String> rpslCollection = whoisObjectAdapter.split(rpsl);
        observer.notify((rpslCollection == null ? 0 : rpslCollection.size()) + " object(s) found");
        assert rpslCollection != null;
        for (String rpslObjectS : rpslCollection) {
            Collection<Credential> localCredentials = whoisObjectAdapter.extractLocalTokens(rpslObjectS);
            Collection<Credential> allCredentials = new ArrayList<Credential>();
            if (localCredentials != null) {
                allCredentials.addAll(localCredentials);
            }
            if (globalCredentials != null) {
                allCredentials.addAll(globalCredentials);
            }
            rpslObjectS = whoisObjectAdapter.cleanLocalTokens(rpslObjectS);
            WhoisObject whoisObject = whoisObjectAdapter.convertToWhoisObject(rpslObjectS);
            authenticator.authenticate(whoisObject, allCredentials, observer);
            validator.validate(whoisObject, observer);
            enricher.enrich(whoisObject, observer);
            //noinspection unchecked
            store.persist(whoisObject, observer);
        }
    }

    public void setParameter(String rpsl) {
        this.rpsl = rpsl;
    }

    public String getResult() {
        return observer.report();
    }
}
