package com.cwsm.platfrom.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by Ming on 5/15/17.
 */

/**
 * extend this class if entity has generated id only
 * @param <K>
 */
@MappedSuperclass
public abstract class AbstractEntity<K extends Serializable> extends EntityObject<K> {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private K id;

    @Override
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return null == this.id;
    }

}
