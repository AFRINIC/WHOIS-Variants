package whois.core.model.rpsl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.api.Credential;
import whois.core.password.PasswordCredential;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by yogesh on 12/16/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class RpslWhoisObjectAdapterTest {

    @Inject
    private RpslWhoisObjectAdapter subject;

    private final String multiple_whoisObjects_withCredentials_01 = " \n password:b\n \n b:c\n \n x:y \n \npassword:e\n \n";
    private final String multiple_whoisObjects_withoutCredentials_01 = " b:c\n \n x:y ";
    private final String single_whoisObjects_withCredentials_01 = " b:c\n password:y\t\npassword:x¥z\n g:h ";
    private final String single_whoisObjects_withoutCredentials_01 = " b:ç\n #:h ";

    @Test
    public void testExtractGlobalCredentials01() {
        Collection<Credential> collectedCredentials = subject.extractGlobalTokens(multiple_whoisObjects_withCredentials_01);
        assertEquals(2, collectedCredentials.size());
        Iterator<Credential> i = collectedCredentials.iterator();
        PasswordCredential pc = (PasswordCredential) i.next();
        Assert.assertEquals("b", pc.getPassword());
        pc = (PasswordCredential) i.next();
        Assert.assertEquals("e", pc.getPassword());
    }

    @Test
    public void testExtractGlobalCredentials02() {
        Collection<Credential> collectedCredentials = subject.extractGlobalTokens(multiple_whoisObjects_withoutCredentials_01);
        assertNull(collectedCredentials);
    }

    @Test
    public void testExtractGlobalCredentials03() {
        Collection<Credential> collectedCredentials = subject.extractGlobalTokens(single_whoisObjects_withCredentials_01);
        assertNull(collectedCredentials);
    }

    @Test
    public void testExtractGlobalCredentials04() {
        Collection<Credential> collectedCredentials = subject.extractGlobalTokens(single_whoisObjects_withoutCredentials_01);
        assertNull(collectedCredentials);
    }

    @Test
    public void testCleanGlobalCredentials01() {
        assertEquals(" b:c\n\n x:y ", subject.cleanGlobalTokens(multiple_whoisObjects_withCredentials_01));
    }

    @Test
    public void testCleanGlobalCredentials02() {
        assertEquals(" b:c\n\n x:y ", subject.cleanGlobalTokens(multiple_whoisObjects_withoutCredentials_01));
    }

    @Test
    public void testCleanGlobalCredentials03() {
        assertEquals(" b:c\n password:y\t\npassword:x¥z\n g:h ", subject.cleanGlobalTokens(single_whoisObjects_withCredentials_01));
    }

    @Test
    public void testCleanGlobalCredentials04() {
        assertEquals(" b:ç\n #:h ", subject.cleanGlobalTokens(single_whoisObjects_withoutCredentials_01));
    }

    @Test
    public void testSplit01() {
        Collection<String> whoisObjectCollection = subject.split(multiple_whoisObjects_withCredentials_01);
        Iterator<String> i = whoisObjectCollection.iterator();
        assertEquals(" password:b", i.next());
        assertEquals(" b:c", i.next());
        assertEquals(" x:y ", i.next());
        assertEquals("password:e", i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testSplit02() {
        Collection<String> whoisObjectCollection = subject.split(multiple_whoisObjects_withoutCredentials_01);
        Iterator<String> i = whoisObjectCollection.iterator();
        assertEquals(" b:c", i.next());
        assertEquals(" x:y ", i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testSplit03() {
        Collection<String> whoisObjectCollection = subject.split(single_whoisObjects_withCredentials_01);
        Iterator<String> i = whoisObjectCollection.iterator();
        assertEquals(" b:c\n password:y\t\npassword:x¥z\n g:h ", i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void testSplit04() {
        Collection<String> whoisObjectCollection = subject.split(single_whoisObjects_withoutCredentials_01);
        Iterator<String> i = whoisObjectCollection.iterator();
        assertEquals(" b:ç\n #:h ", i.next());
        assertFalse(i.hasNext());
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testExtractLocalCredentials01() {
        Collection<Credential> collectedCredentials = subject.extractLocalTokens(multiple_whoisObjects_withCredentials_01);
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testExtractLocalCredentials02() {
        Collection<Credential> collectedCredentials = subject.extractLocalTokens(multiple_whoisObjects_withoutCredentials_01);
    }

    @Test
    public void testExtractLocalCredentials03() {
        Collection<Credential> collectedCredentials = subject.extractLocalTokens(single_whoisObjects_withCredentials_01);
        assertEquals(2, collectedCredentials.size());
        Iterator<Credential> i = collectedCredentials.iterator();
        PasswordCredential pc = (PasswordCredential) i.next();
        Assert.assertEquals("y\t", pc.getPassword());
        pc = (PasswordCredential) i.next();
        Assert.assertEquals("x¥z", pc.getPassword());
    }

    @Test
    public void testExtractLocalCredentials04() {
        Collection<Credential> collectedCredentials = subject.extractLocalTokens(single_whoisObjects_withoutCredentials_01);
        assertNull(collectedCredentials);
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testCleanLocalTokens01() {
        String s = subject.cleanLocalTokens(multiple_whoisObjects_withCredentials_01);
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testCleanLocalTokens02() {
        String s = subject.cleanLocalTokens(multiple_whoisObjects_withoutCredentials_01);
    }

    @Test
    public void testCleanLocalTokens03() {
        assertEquals(" b:c\n g:h ", subject.cleanLocalTokens(single_whoisObjects_withCredentials_01));
    }

    @Test
    public void testCleanLocalTokens04() {
        assertEquals(" b:ç\n #:h ", subject.cleanLocalTokens(single_whoisObjects_withoutCredentials_01));
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testConvert01() {
        subject.convertToWhoisObject(multiple_whoisObjects_withCredentials_01);
    }

    @Test(expected = InvalidRpslObjectException.class)
    public void testConvert02() {
        subject.convertToWhoisObject(multiple_whoisObjects_withoutCredentials_01);
    }

    @Test(expected = RpslObjectNotCleanException.class)
    public void testConvert03() {
        subject.convertToWhoisObject(single_whoisObjects_withCredentials_01);
    }

    @Test
    public void testConvert04() {
        RpslWhoisObject rpslWhoisObject = (RpslWhoisObject) subject.convertToWhoisObject(single_whoisObjects_withoutCredentials_01);
        assertEquals("ç", rpslWhoisObject.get("B"));
        assertEquals("h ", rpslWhoisObject.get(" # "));
    }

}
