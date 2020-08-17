package com.zte.bean.vo;
import com.zte.bean.Admin;
import com.zte.bean.Menu;
import com.zte.bean.Role;
import com.zte.bean.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author yinsiwei
 * @date 2020-07-20 10:08
 * 针对角色接口消息对象
 */
public class RoleVo extends Role {

    private static final long serialVersionUID = 1L;

    Set<Admin> adminSet;
    Set<Integer> menuIdSet;



    public Set<Admin> getAdminSet() {
        return adminSet;
    }

    public void setAdminSet(Set<Admin> adminSet) {
        this.adminSet = adminSet;
    }

    public Set<Integer> getMenuIdSet() {
        return menuIdSet;
    }

    public void setMenuIdSet(Set<Integer> menuIdSet) {
        this.menuIdSet = menuIdSet;
    }
}
