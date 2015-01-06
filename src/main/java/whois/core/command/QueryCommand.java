package whois.core.command;

import org.springframework.context.annotation.Scope;
import whois.core.framework.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

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
        Class objectType = modelAdapter.getKeyToModelClassMap().get(commandLine);
        if (objectType == null) {
            throw new CommandException("Unknown object type. Known types: " + modelAdapter.getKeyToModelClassMap().keySet());
        }
        List<WhoisObject> result = store.load(objectType);
        for (WhoisObject whoisObject : result) {
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
