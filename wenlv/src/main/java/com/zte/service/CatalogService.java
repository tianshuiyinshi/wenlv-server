package com.zte.service;

import com.zte.bean.Catalog;
import com.zte.bean.vo.CatalogVo;
import com.zte.dao.CatalogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-29 10:06
 */
@Service
public class CatalogService {
    @Autowired
    CatalogMapper catalogMapper;

    public Integer addCatalog(Catalog catalog){
        return catalogMapper.insertSelective(catalog);
    }

    public Integer deleteCatalog(Integer catalogId){
        return catalogMapper.deleteByPrimaryKey(catalogId);
    }

    public Integer updateCatalog(Catalog catalog){
        return catalogMapper.updateByPrimaryKeySelective(catalog);
    }

    public Catalog queryCatalogById(Integer catalogId){
        return catalogMapper.selectByPrimaryKey(catalogId);
    }

    public Catalog queryCatalogByName(String catalogName){
        return catalogMapper.selectByName(catalogName);
    }

    public List<CatalogVo> queryAllRows(CatalogVo catalogVo) {
        return catalogMapper.selectAllRows(catalogVo);
    }
}
