package whois.core.socket;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whois.core.api.Observer;
import whois.core.api.UpdateCommand;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yogesh on 12/22/14.
 */
@Named
public class UpdateSocketEventListener extends AbstractSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(UpdateSocketEventListener.class);

    @Inject
    private Observer observer;

    private final StringBuilder multiLineAccumulator = new StringBuilder();

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String command = message.toString();
        if (isExitCommand(command)) {
            String rpsl = multiLineAccumulator.toString();
            multiLineAccumulator.setLength(0);
            process(session, rpsl);
            session.close();
            return;
        }
        multiLineAccumulator.append(command).append("\n");
    }

    @Override
    public String toString() {
        return "Update handler";
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class getCommandId() {
        return UpdateCommand.class;
    }
}
