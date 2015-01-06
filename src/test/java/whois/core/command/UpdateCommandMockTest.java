package whois.core.command;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import whois.core.framework.*;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.*;

/**
 * Created by yogesh on 12/15/14.
 *
 * TODO Delete this test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateCommandMockTest extends TestCase {

    @Mock
    private Reporter reporter;

    @Mock
    private WhoisObjectAdapter whoisObjectAdapter;

    @Mock
    private Authenticator authenticator;

    @Mock
    private Validator validator;

    @Mock
    private Enricher enricher;

    @Mock
    private Store store;

    @InjectMocks
    private UpdateCommand subject;

    @Mock
    private WhoisObject whoisObject;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCommand() {
        // Mock setup
        String rpsl = "key: a\ndesc: b";
        Collection<String> rpslCollection = Arrays.asList(rpsl);

        when(whoisObjectAdapter.extractGlobalTokens(rpsl)).thenReturn(null);
        when(whoisObjectAdapter.cleanGlobalTokens(rpsl)).thenReturn(rpsl);
        when(whoisObjectAdapter.split(rpsl)).thenReturn(rpslCollection);
        when(whoisObjectAdapter.extractLocalTokens(rpsl)).thenReturn(null);
        when(whoisObjectAdapter.cleanLocalTokens(rpsl)).thenReturn(rpsl);
        when(whoisObjectAdapter.convertToWhoisObject(rpsl)).thenReturn(whoisObject);

        // Execute
        subject.setParameter(rpsl);
        subject.run();

        // Verify
        verify(whoisObjectAdapter).extractLocalTokens(anyString());
    }
}
