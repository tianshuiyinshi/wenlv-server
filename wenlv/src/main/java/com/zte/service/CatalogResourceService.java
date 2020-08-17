package com.zte.service;

import com.zte.bean.CatalogResource;
import com.zte.bean.vo.CatalogResourceVo;
import com.zte.dao.CatalogResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-07-29 10:06
 */
@Service
public class CatalogResourceService {
    @Autowired
    CatalogResourceMapper catalogResourceMapper;

    public Boolean addCatalogResource(CatalogResource catalogResource){
        return catalogResourceMapper.insertSelective(catalogResource)==1?true:false;
    }

    public Boolean deleteCatalogResource(CatalogResourceVo catalogResourceVo){
        return catalogResourceMapper.deleteByPrimaryKey(catalogResourceVo.getMapid())==1?true:false;
    }

    public Boolean updateCatalogResource(CatalogResourceVo catalogResourceVo){
        return catalogResourceMapper.updateByPrimaryKeySelective(catalogResourceVo)==1?true:false;
    }

    public List<CatalogResourceVo> queryAllRows(CatalogResourceVo catalogResourceVo){
       return catalogResourceMapper.selectAllRows(catalogResourceVo);
    }


}
