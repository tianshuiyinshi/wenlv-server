package com.zte.bean.vo;

import com.zte.bean.*;

import java.util.List;
import java.util.Set;

/**
 * @comment
 * @Author zhongyong 2020/7/13 14:20
 */
public class AdminVo extends Admin {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Set<Role> roleSet;
    
	private Set<Menu> MenuSet;

	private String adminToken;

	public AdminVo() {
	}
	


	public String getAdminToken() {
		return adminToken;
	}


	public void setAdminToken(String adminToken) {
		this.adminToken = adminToken;
	}


	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public Set<Menu> getMenuSet() {
		return MenuSet;
	}

	public void setMenuSet(Set<Menu> menuSet) {
		MenuSet = menuSet;
	}


    
    
    
}