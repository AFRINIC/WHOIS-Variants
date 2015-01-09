package whois.core.api;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * Created by yogesh on 1/5/15.
 */
@Named
@Scope("prototype")
public class BufferedObserver implements Observer {

    private final StringBuilder notificationAccumulator = new StringBuilder();

    public void notify(String message) {
        notificationAccumulator.append(message).append("\n");
    }

    public String report() {
        return notificationAccumulator.toString();
    }

    @Override
    public String toString() {
        return report();
    }

}
