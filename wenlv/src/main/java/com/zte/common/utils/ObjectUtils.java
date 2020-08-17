package com.zte.common.utils;

import com.zte.bean.Admin;
import com.zte.bean.dto.UserInfo;

/**
 * @author yinsiwei
 * @date 2020-07-22 18:50
 */
public class ObjectUtils {
    //将Admin转换成UserInfo
    public static UserInfo AdminToUserInfo(Admin admin){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(admin.getAdminId());
        userInfo.setAccount(admin.getAccount());
        userInfo.setPassword(admin.getPassword());
        userInfo.setAdminName(admin.getAdminName());
        userInfo.setMobile(admin.getMobile());
        userInfo.setCreateTime(admin.getCreateTime());
        userInfo.setUpdateTime(admin.getUpdateTime());
        userInfo.setSalt(admin.getSalt());
        userInfo.setAdminType(admin.getAdminType());
        userInfo.setEntId(admin.getEntId());
        userInfo.setStatus(admin.getStatus());

        return userInfo;
    }

}
