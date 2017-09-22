package com.cwsm.platfrom.model.bean;

import java.io.Serializable;

/**
 * Created by Ming on 6/24/17.
 */
public interface UserDetails extends Serializable {

    String UUID();
    Object getUserId();
    String getOrganizationCode();
    String getUserName();
    String getPassword();
}
