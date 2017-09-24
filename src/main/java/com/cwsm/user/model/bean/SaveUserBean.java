package com.cwsm.user.model.bean;

import com.cwsm.platfrom.model.bean.GenericObject;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yede on 2017/9/22.
 */
public class SaveUserBean extends GenericObject {
    private Long userId;

    @NotEmpty(message="用户名: 不能为空")
    private  String  userName;

    @NotEmpty(message="密码: 不能为空")
    private  String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
