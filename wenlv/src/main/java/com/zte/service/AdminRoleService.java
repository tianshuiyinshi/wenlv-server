package com.zte.service;

import com.zte.bean.AdminRole;
import com.zte.bean.Menu;
import com.zte.bean.Role;
import com.zte.bean.RoleMenu;
import com.zte.dao.AdminRoleMapper;
import com.zte.dao.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-24 10:06
 */

@Service
public class AdminRoleService {


    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    /**
     * @author yinsiwei
     * @date 2020-07-24 15:32
     * 根据AdminId查询AdminRole
    */
    public List<AdminRole> queryAdminRoleByAdminId(Integer adminId){

        return adminRoleMapper.selectByAdminId(adminId);
    }

    /**
     * @author yinsiwei
     * @date 2020-07-24 16:38
     * 根据RoleId查询AdminRole
    */
    public List<AdminRole> queryAdminRoleByRoleId(Integer roleId){
        List<AdminRole> adminRoles = adminRoleMapper.selectByRoleId(roleId);
        return adminRoles;
    }


    /**
     * @author yinsiwei
     * @date 2020-07-24 16:39
     * 修改AdminRole
     * 根据AdminIds和RoleIds进行双重遍历，装填一个AdminRole，则删除一个AdminRole
    */
    public Integer delAdminRole(AdminRole adminRole){
        return adminRoleMapper.deleteByAdminIdAndRoleId(adminRole.getAdminId(),adminRole.getRoleId());
    }

    /**
     * @author yinsiwei
     * @date 2020-07-24 15:53
     *新增AdminRole
    */

    public Integer addAdminRole(AdminRole adminRole){
        return adminRoleMapper.insertSelective(adminRole);
    }




}
