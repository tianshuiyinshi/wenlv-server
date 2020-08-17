package com.zte.dao;

import com.zte.bean.Catalog;
import com.zte.bean.vo.CatalogVo;

import java.util.List;

public interface CatalogMapper {
    int deleteByPrimaryKey(Integer catalogid);

    int insert(Catalog record);

    int insertSelective(Catalog record);

    Catalog selectByPrimaryKey(Integer catalogid);

    int updateByPrimaryKeySelective(Catalog record);

    int updateByPrimaryKey(Catalog record);

    List<CatalogVo> selectAllRows(CatalogVo catalogVo);

    Catalog selectByName(String catalogName);
}