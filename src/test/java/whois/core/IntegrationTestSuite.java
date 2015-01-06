package whois.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import whois.core.command.CommandIntegrationTest;
import whois.core.socket.SocketEventListenerIntegrationTest;

/**
 * Created by yogesh on 1/6/15.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CommandIntegrationTest.class,
        SocketEventListenerIntegrationTest.class})
public class IntegrationTestSuite {
}
