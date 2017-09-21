package com.cwsm.platfrom.model.dao;

import java.util.List;

/**
 * Created by Ming on 5/16/17.
 */
public interface JpaGenericDao {

    /**
     * execute jpa sql
     * @param sql
     * @param params
     * @return
     */
    List executeSql(String sql, Object... params);

    /**
     * execute native sql
     * @param sql
     * @param params
     * @return
     */
    List executeNativeSql(String sql, Object... params);
}
