package whois.core.socket;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.MDC;
import whois.core.api.Command;
import whois.core.api.CommandException;
import whois.core.api.CommandFactory;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by yogesh on 1/6/15.
 */
abstract class AbstractSocketEventListener extends IoHandlerAdapter {


    private static final String EOF = "\u0004";

    @Inject
    private CommandFactory commandFactory;

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        MDC.put("clientIp", extractClientIp(session));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        if (cause instanceof CommandException) {
            session.write("ERROR: " + cause.getMessage());
        } else {
            getLogger().error("Exception on socket listener", cause);
            session.write("UNEXPECTED ERROR: " + cause.getMessage());
        }
        session.close();
    }

    protected abstract Logger getLogger();

    protected abstract Class getCommandId();

    String extractClientIp(IoSession session) {
        InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
        InetAddress inetAddress = socketAddress.getAddress();
        return inetAddress.getHostAddress();
    }

    boolean isExitCommand(String command) {
        String trimmedCommand = command.trim();
        return command.startsWith(EOF)
                || "quit".equalsIgnoreCase(trimmedCommand)
                || "exit".equalsIgnoreCase(trimmedCommand)
                || "bye".equalsIgnoreCase(trimmedCommand);
    }

    void process(IoSession session, String commandLine) {
        Command updateCommand = commandFactory.getCommand(getCommandId());
        updateCommand.setParameter(commandLine);
        updateCommand.run();
        session.write(updateCommand.getResult());
    }

}
