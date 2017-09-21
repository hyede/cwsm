package com.cwsm.platfrom.model.entity;

import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Ming on 5/15/17.
 */

/**
 * extend this class if entity has any kind of id, entity should define the id by itself
 * @param <K>
 */
@MappedSuperclass
public abstract class EntityObject<K extends Serializable> implements Serializable, Persistable<K> {

    public abstract K getId();
    public abstract void setId(K id);

    @Override
    public boolean isNew() {
        return null == this.getId();
    }
}
