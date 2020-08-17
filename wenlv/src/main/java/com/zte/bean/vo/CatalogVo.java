package com.zte.bean.vo;

import com.zte.bean.Catalog;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-11 20:07
 */
public class CatalogVo extends Catalog {
    private static final long serialVersionUID = 1L;

    private List<CatalogResourceVo> catalogResourcesVo;

    private List<ResourceNewsVo> resourceNewsVos;
    private List<ResourceSiteVo> resourceSiteVos;
    private List<ResourceActivityVo> resourceActivityVos;

    public List<CatalogResourceVo> getCatalogResourceVos() {
        return catalogResourcesVo;
    }

    public void setCatalogResourceVos(List<CatalogResourceVo> catalogResourceVos) {
        this.catalogResourcesVo = catalogResourceVos;
    }
}
