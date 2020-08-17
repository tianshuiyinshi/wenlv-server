package com.zte.service;

import com.zte.bean.Menu;
import com.zte.dao.MenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-23 11:10
 */
@Service
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    MenuMapper menuMapper;

    public List<Menu> queryAllMenus(){
        return menuMapper.selectAllRows();
    }

    public List<Menu> queryMenus(List<Integer> menuIds){
        return menuMapper.selectByMenuIds(menuIds);
    }

    public Menu queryMenuById(Integer menuId){
        return menuMapper.selectByPrimaryKey(menuId);
    }

    public Menu queryMenuByUrl(String menuUrl){
        return menuMapper.selectByMenuUrl(menuUrl);
    }

//  public void updateMenu(Menu menu){
//      menuMapper.updateByPrimaryKeySelective(menu);
//  }

//  public void addMenu(Menu menu){
//      menuMapper.insertSelective(menu);
//  }

//  public void delMenu(Integer menuId){
//      menuMapper.deleteByPrimaryKey(menuId);
//  }
}
