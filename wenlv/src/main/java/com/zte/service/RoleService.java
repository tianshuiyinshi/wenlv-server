package com.zte.service;

import com.zte.bean.Role;
import com.zte.dao.RoleMapper;
import com.zte.dao.RoleMenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-20 11:26
 */
@Service
public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleMenuMapper roleMenuMapper;


    public List<Role> queryAllRows(){
        return roleMapper.selectAllRows();
    }

    public List<Role> queryRoles(List<Integer> roleIds){
        return roleMapper.selectRoles(roleIds);
    }

    public Role queryRoleById(Integer roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    }

    public Role queryRoleByName(String roleName){
        return roleMapper.selectByRoleName(roleName);
    }

    public void updateRole(Role role){
        roleMapper.updateByPrimaryKeySelective(role);
    }

    public Integer delRole(Integer roleId){
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    public Integer addRole(Role role){
        return roleMapper.insertSelective(role);
    }
}
