package whois.core.model.blob;

import whois.core.framework.StoreModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yogesh on 12/18/14.
 */
@Entity
@Table(name = "Last")
public class BlobModel implements StoreModel, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] object;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getObject() {
        return object;
    }

    public void setObject(byte[] object) {
        this.object = object;
    }


}
