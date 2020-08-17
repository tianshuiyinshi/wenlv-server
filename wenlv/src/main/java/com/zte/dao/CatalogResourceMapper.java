package com.zte.dao;

import com.zte.bean.CatalogResource;
import com.zte.bean.vo.CatalogResourceVo;

import java.util.List;

public interface CatalogResourceMapper {
    int deleteByPrimaryKey(Integer mapid);

    int insert(CatalogResource record);

    int insertSelective(CatalogResource record);

    CatalogResource selectByPrimaryKey(Integer mapid);

    int updateByPrimaryKeySelective(CatalogResource record);

    int updateByPrimaryKey(CatalogResource record);

    List<CatalogResourceVo> selectAllRows(CatalogResourceVo catalogResourceVo);
}