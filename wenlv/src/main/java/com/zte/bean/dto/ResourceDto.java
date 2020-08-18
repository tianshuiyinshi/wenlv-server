package com.zte.bean.dto;

import java.io.Serializable;

/**
 * @author yinsiwei
 * @date 2020-08-18 17:54
 */
public class ResourceDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resourcetitle;

    private String resourcedetail;

    public String getResourcetitle() {
        return resourcetitle;
    }

    public void setResourcetitle(String resourcetitle) {
        this.resourcetitle = resourcetitle;
    }

    public String getResourcedetail() {
        return resourcedetail;
    }

    public void setResourcedetail(String resourcedetail) {
        this.resourcedetail = resourcedetail;
    }
}
