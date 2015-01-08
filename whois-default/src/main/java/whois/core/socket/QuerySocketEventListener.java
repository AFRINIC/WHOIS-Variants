package whois.core.socket;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whois.core.api.QueryCommand;

import javax.inject.Named;

/**
 * Created by yogesh on 12/22/14.
 */
@Named
public class QuerySocketEventListener extends AbstractSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(QuerySocketEventListener.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        String command = message.toString();
        process(session, command);
        session.close();
    }

    @Override
    public String toString() {
        return "Query handler";
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected Class getCommandId() {
        return QueryCommand.class;
    }
}
