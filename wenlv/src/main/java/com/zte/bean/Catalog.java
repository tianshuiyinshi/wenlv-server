package com.zte.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "栏目基础类")
public class Catalog implements Serializable {

    @ApiModelProperty(value = "栏目id")
    private Integer catalogid;
    @ApiModelProperty(value = "栏目名称")
    private String catalogname;
    @ApiModelProperty(value = "类型")
    private Integer catalogtype;
    @ApiModelProperty(value = "栏目图片")
    private String catalogimage;
    @ApiModelProperty(value = "栏目样式")
    private Integer catalogdisplaystyle;
    @ApiModelProperty(value = "栏目链接地址")
    private String cataloglink;

    private Integer status;

    private Integer creator;

    private String createtime;

    private Integer updater;

    private String updatetime;

    private Integer tableid;

    private static final long serialVersionUID = 1L;

    public Integer getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(Integer catalogid) {
        this.catalogid = catalogid;
    }

    public String getCatalogname() {
        return catalogname;
    }

    public void setCatalogname(String catalogname) {
        this.catalogname = catalogname == null ? null : catalogname.trim();
    }

    public Integer getCatalogtype() {
        return catalogtype;
    }

    public void setCatalogtype(Integer catalogtype) {
        this.catalogtype = catalogtype;
    }

    public String getCatalogimage() {
        return catalogimage;
    }

    public void setCatalogimage(String catalogimage) {
        this.catalogimage = catalogimage == null ? null : catalogimage.trim();
    }

    public Integer getCatalogdisplaystyle() {
        return catalogdisplaystyle;
    }

    public void setCatalogdisplaystyle(Integer catalogdisplaystyle) {
        this.catalogdisplaystyle = catalogdisplaystyle;
    }

    public String getCataloglink() {
        return cataloglink;
    }

    public void setCataloglink(String cataloglink) {
        this.cataloglink = cataloglink == null ? null : cataloglink.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public Integer getUpdater() {
        return updater;
    }

    public void setUpdater(Integer updater) {
        this.updater = updater;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", catalogid=").append(catalogid);
        sb.append(", catalogname=").append(catalogname);
        sb.append(", catalogtype=").append(catalogtype);
        sb.append(", catalogimage=").append(catalogimage);
        sb.append(", catalogdisplaystyle=").append(catalogdisplaystyle);
        sb.append(", cataloglink=").append(cataloglink);
        sb.append(", status=").append(status);
        sb.append(", creator=").append(creator);
        sb.append(", createtime=").append(createtime);
        sb.append(", updater=").append(updater);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}