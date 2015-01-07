package whois.core.command;

import org.springframework.context.annotation.Scope;
import whois.core.framework.Command;
import whois.core.framework.ModelAdapter;
import whois.core.framework.Store;
import whois.core.framework.WhoisObject;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 12/23/14.
 */
@Named
@Scope("prototype")
public class QueryCommand implements Command {

    @Inject
    private Store store;

    @Inject
    private ModelAdapter modelAdapter;

    private String commandLine;

    private StringBuilder queryResultAccumulator = new StringBuilder();

    @Override
    public void run() {
        Class objectType = modelAdapter.getModelClass(null);
        WhoisObject whoisObject = store.load(objectType, commandLine);
        if (whoisObject != null) {
            queryResultAccumulator.append(whoisObject.toString()).append("\n");
        }
    }

    @Override
    public void setParameter(String commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public String getResult() {
        return queryResultAccumulator.toString();
    }

}
