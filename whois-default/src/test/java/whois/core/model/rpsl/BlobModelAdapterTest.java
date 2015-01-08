package whois.core.model.rpsl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import whois.core.model.blob.BlobModel;
import whois.core.model.blob.BlobModelAdapter;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

/**
 * Created by yogesh on 12/23/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/whois-core-context.xml", "/whois-core-test-context.xml"})
public class BlobModelAdapterTest {

    @Inject
    private BlobModelAdapter subject;

    @Test
    public void testConvertToWhoisObject() {
        BlobModel blobModel = new BlobModel();
        blobModel.setObject("a:b".getBytes());
        RpslWhoisObject rpslWhoisObject = (RpslWhoisObject) subject.convertToWhoisObject(blobModel);
        assertEquals("b", rpslWhoisObject.get("a"));
    }

    @Test
    public void testConvertToModel() {
        RpslWhoisObject whoisObject = new RpslWhoisObject();
        whoisObject.put("a", "b");
        BlobModel blobModel = (BlobModel) subject.convertToStoreModel(whoisObject);
        assertEquals("a:b", new String(blobModel.getObject()));
    }

}
