package whois.core.socket;

import org.apache.mina.core.session.IoSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.AbstractDatabaseTestCase;

import javax.inject.Inject;

import static org.mockito.Mockito.verify;

/**
 * Created by yogesh on 1/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class SocketEventListenerIntegrationTest extends AbstractDatabaseTestCase {

    @Inject
    private UpdateSocketEventListener usel;

    @Inject
    private QuerySocketEventListener qsel;

    @Mock
    private IoSession uSession;

    @Mock
    private IoSession qSession;

    @Before
    public void setUpMockito() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIntegration() throws Exception {
        String rpsl001 = "e:f";
        String rpsl002 = "g:h";
        String query = "blob";
        String uExpectation001 = "1 object(s) found\nSuccessfully added:\ne:              f\n\n";
        String uExpectation002 = "1 object(s) found\nSuccessfully added:\ng:              h\n\n";
        String qExpectation = "a:              b\n\n" +
                "c:              d\n\n" +
                "e:              f\n\n" +
                "g:              h\n\n";

        // Execute
        usel.messageReceived(uSession, rpsl001);
        usel.messageReceived(uSession, "bye");
        usel.messageReceived(uSession, rpsl002);
        usel.messageReceived(uSession, "bye");
        qsel.messageReceived(qSession, query);

        // Verify
        verify(uSession).write(uExpectation001);
        verify(uSession).write(uExpectation002);
        verify(qSession).write(qExpectation);
    }

}
