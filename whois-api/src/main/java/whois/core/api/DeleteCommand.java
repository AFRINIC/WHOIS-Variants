package whois.core.api;

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 1/9/15.
 */
@Named
@Scope("prototype")
public class DeleteCommand implements Command {

    @Inject
    private Store store;

    @Inject
    private Observer observer;

    @Inject
    private ModelAdapter modelAdapter;

    private String commandLine;

    public void setParameter(String parameter) {
        commandLine = parameter;
    }

    public void run() {
        Class objectType = modelAdapter.getModelClass(null);
        store.delete(objectType, commandLine, observer);
    }

    public String getResult() {
        return observer.report();
    }

}
