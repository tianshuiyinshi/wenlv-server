package com.zte.bean.vo;

import com.zte.bean.CatalogResource;

/**
 * @author yinsiwei
 * @date 2020-08-11 19:37
 */
public class CatalogResourceVo extends CatalogResource {
    private static final long serialVersionUID = 1L;

    private ResourceSiteVo resourceSiteVo;
    private ResourceActivityVo resourceActivityVo;
    private ResourceNewsVo resourceNewsVo;

    public ResourceSiteVo getResourceSiteVo() {
        return resourceSiteVo;
    }

    public void setResourceSiteVo(ResourceSiteVo resourceSiteVo) {
        this.resourceSiteVo = resourceSiteVo;
    }

    public ResourceActivityVo getResourceActivityVo() {
        return resourceActivityVo;
    }

    public void setResourceActivityVo(ResourceActivityVo resourceActivityVo) {
        this.resourceActivityVo = resourceActivityVo;
    }

    public ResourceNewsVo getResourceNewsVo() {
        return resourceNewsVo;
    }

    public void setResourceNewsVo(ResourceNewsVo resourceNewsVo) {
        this.resourceNewsVo = resourceNewsVo;
    }
}
