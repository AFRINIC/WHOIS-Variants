package whois.core.api;

import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 12/23/14.
 */
@Named
@Scope("prototype")
public class QueryCommand implements Command<String, String> {

    @Inject
    private Store store;

    @Inject
    private Observer observer;

    private String commandLine;

    public void run() {
        //noinspection unchecked
        store.load(commandLine, observer);
    }

    public void setParameter(String commandLine) {
        this.commandLine = commandLine;
    }

    public String getResult() {
        return observer.report();
    }

}
