package com.ocean.clouduser.vo;


import com.ocean.clouduser.entity.User;

/**
 * @author gaoyuzhe
 * @date 2017/12/15.
 */
public class UserVo {
    /**
     * 更新的用户对象
     */
    private User User = new User();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
    }

    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
    }
}
