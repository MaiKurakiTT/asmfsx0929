package com.hsd.asmfsx.bean;

/**
 * Created by apple on 2016/11/25.
 */

public class LoginBean2 extends BaseBean2{
    private String phone;
    private String password;
    private UserBean2 user;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBean2 getUser() {
        return user;
    }

    public void setUser(UserBean2 user) {
        this.user = user;
    }
}
