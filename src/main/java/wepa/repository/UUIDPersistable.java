package wepa.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.domain.Persistable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDPersistable implements Persistable<String> {

    @Id
    private String id;

    public UUIDPersistable() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return false;
    }

    // muuta mahdollista
}
