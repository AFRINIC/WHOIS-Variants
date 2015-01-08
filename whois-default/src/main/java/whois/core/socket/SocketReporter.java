package whois.core.socket;

import whois.core.api.Reporter;

import javax.inject.Named;

/**
 * Created by yogesh on 1/5/15.
 */
@Named
public class SocketReporter implements Reporter {

    private final StringBuilder responseAccumulator = new StringBuilder();

    public void report(String message) {
        responseAccumulator.append(message).append("\n");
    }

    public void clear() {
        responseAccumulator.setLength(0);
    }

    @Override
    public String toString() {
        return responseAccumulator.toString();
    }

}
