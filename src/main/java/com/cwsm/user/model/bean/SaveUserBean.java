package com.cwsm.user.model.bean;

import com.cwsm.platfrom.model.bean.GenericObject;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by yede on 2017/9/22.
 */
public class SaveUserBean extends GenericObject {
    private Long userId;

    @NotEmpty(message="用户名: 不能为空")
    @Pattern(regexp="^[\\u4E00-\\u9FA5a-zA-Z0-9_]{2,20}$", message="用户名必须汉字 英文字母 数字 下划线组成，2-20位")
    private  String  userName;

    @NotEmpty(message="密码: 不能为空")
    @Pattern(regexp="^[0-9]{6,10}", message="请输入6-10的数字密码")
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
