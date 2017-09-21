package com.cwsm.platfrom.model.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by Ming on 11/17/16.
 */
public abstract class GenericObject implements Serializable {

    @Override
    public int hashCode() {
        int result = super.hashCode();
        try {
            int prime = 31;
            Field[] fields = this.getClass().getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(this);
                result = result * prime + value.hashCode();
            }
        } catch(Exception e) {

        }
        return result;

    }

}
