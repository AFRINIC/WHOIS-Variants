package whois.core.model.blob;

import whois.core.framework.StoreModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by yogesh on 12/18/14.
 */
@Entity
@Table(name = "Last")
public class BlobModel implements StoreModel, Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private byte[] object;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public byte[] getObject() {
        return object;
    }

    public void setObject(byte[] object) {
        this.object = object;
    }


}
