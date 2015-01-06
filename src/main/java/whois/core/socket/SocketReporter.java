package whois.core.socket;

import whois.core.framework.Reporter;

import javax.inject.Named;

/**
 * Created by yogesh on 1/5/15.
 */
@Named
public class SocketReporter implements Reporter {

    private StringBuilder responseAccumulator = new StringBuilder();

    @Override
    public void report(String message) {
        responseAccumulator.append(message).append("\n");
    }

    @Override
    public void clear() {
        responseAccumulator.setLength(0);
    }

    @Override
    public String toString() {
        return responseAccumulator.toString();
    }

}
