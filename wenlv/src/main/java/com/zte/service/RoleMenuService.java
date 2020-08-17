package com.zte.service;


import com.zte.bean.Menu;
import com.zte.bean.RoleMenu;
import com.zte.dao.MenuMapper;
import com.zte.dao.RoleMapper;
import com.zte.dao.RoleMenuMapper;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yinsiwei
 * @date 2020-07-20 17:14
 */
@Service
public class RoleMenuService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    RoleMenuMapper roleMenuMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    MenuMapper menuMapper;

    /**
     * @author yinsiwei
     * @date 2020-07-21 08:18
     * @params
     * @return
     * @throws
     * @since
     * 根据RoleId查询角色权限
    */
    public Set<Menu> queryMenusByRole(Integer roleId){
    	Set<Menu> menus = new HashSet<Menu>();
        List<RoleMenu> roleMenus = roleMenuMapper.selectByRoleId(roleId);
        for (RoleMenu roleMenu : roleMenus) {
            Menu menu = menuMapper.selectByPrimaryKey(roleMenu.getMenuId());
            menus.add(menu);
        }
        return menus;
    }

//  /**
//   * @author yinsiwei
//   * @date 2020-07-21 11:13
//   * 根据RoleId删除角色权限
//  */
//  public MenuVo deleteMenuByRole(Integer roleId){
//      MenuVo vo = new MenuVo();
//      List<Menu> menus = null;
//      if (roleMapper.selectByPrimaryKey(roleId)==null){
//          logger.debug("queryMenuByRole error：该角色不存在, roleId {}.",roleId);
//          vo.setResultStatus(2);
//          vo.setResultMsg("该角色不存在");
//          return vo;
//      }
//      roleMenuMapper.deleteByRoleId(roleId);
//      vo.setResultStatus(0);
//      vo.setResultMsg("success");
//      return vo;
//  }

    /**
     * @author yinsiwei
     * @date 2020-07-24 11:02
     * 在管理员新增角色时会调用，同步添加角色权限
    */
    public void addRoleMenu(Integer roleId,Set<Integer> menuIds){
        RoleMenu roleMenu = new RoleMenu();

        //插入新角色权限映射关系
        for (Integer menuId : menuIds) {
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insertSelective(roleMenu);
        }
    }

    /**
     * @author yinsiwei
     * @date 2020-07-21 11:14
     * 修改RoleId对应的角色权限
    */
    public void updateRoleMenu(Integer roleId,Set<Integer> menuIds){
        //删除原角色权限映射关系
        roleMenuMapper.deleteByRoleId(roleId);
        //新增新角色权限映射关系
        if (menuIds!=null){
            addRoleMenu(roleId,menuIds);
        }
    }




}
