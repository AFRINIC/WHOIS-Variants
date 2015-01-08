package whois.core.api;

import org.springframework.context.annotation.Scope;

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

    private final StringBuilder queryResultAccumulator = new StringBuilder();

    public void run() {
        Class objectType = modelAdapter.getModelClass(null);
        WhoisObject whoisObject = store.load(objectType, commandLine);
        if (whoisObject != null) {
            queryResultAccumulator.append(whoisObject.toString()).append("\n");
        }
    }

    public void setParameter(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getResult() {
        return queryResultAccumulator.toString();
    }

}
