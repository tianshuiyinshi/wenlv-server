package com.zte.bean.dto;


import com.zte.bean.Menu;
import com.zte.bean.Role;

import java.io.Serializable;
import java.util.Set;

/**
 * @author yinsiwei
 * @date 2020-07-21 18:11
 * 登录用户信息操作解耦，登录操作时，用户账户和管理员账户登录均通过SystemUtils公共类方法校验,后续使用可通过userType或其它标志字段进行识别
 */
public class UserInfo implements Serializable {
    private Integer userId;

    private String account;

    private String password;

    private String adminName;

    private String mobile;

    private String createTime;

    private String updateTime;

    private String salt;

    private Integer adminType;

    private Integer entId;

    private Integer status;

    private Set<Role> roleSet;

    private Set<Menu> menuSet;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", salt='" + salt + '\'' +
                ", adminType=" + adminType +
                ", entId=" + entId +
                ", status=" + status +
                '}';
    }
}
