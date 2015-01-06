package whois.core.command;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import whois.core.framework.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yogesh on 12/15/14.
 */
@Named
@Scope("prototype")
public class UpdateCommand implements Command {

    private String rpsl;

    @Inject
    private Reporter reporter;

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

    private String result;

    @Transactional
    @Override
    public void run() {
        Collection<Credential> globalCredentials = whoisObjectAdapter.extractGlobalTokens(rpsl);
        rpsl = whoisObjectAdapter.cleanGlobalTokens(rpsl);
        Collection<String> rpslCollection = whoisObjectAdapter.split(rpsl);
        reporter.report((rpslCollection == null ? 0 : rpslCollection.size()) + " object(s) found");
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
            authenticator.authenticate(whoisObject, allCredentials, reporter);
            validator.validate(whoisObject, reporter);
            enricher.enrich(whoisObject, reporter);
            store.persist(whoisObject, reporter);
        }
        result = reporter.toString();
        reporter.clear();
    }

    @Override
    public void setParameter(String rpsl) {
        this.rpsl = rpsl;
    }

    @Override
    public String getResult() {
        return result;
    }
}
