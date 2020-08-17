package com.zte.bean;

import java.io.Serializable;

public class CatalogResource implements Serializable {
    private Integer mapid;

    private Integer catalogid;

    private Integer resourceid;

    private Integer resourcetoptypeid;

    private Integer resourcetypeid;

    private Integer tableid;

    private Integer seqno;

    private Integer upflag;

    private Integer status;

    private Integer creator;

    private String createtime;

    private Integer updater;

    private String updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getMapid() {
        return mapid;
    }

    public void setMapid(Integer mapid) {
        this.mapid = mapid;
    }

    public Integer getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(Integer catalogid) {
        this.catalogid = catalogid;
    }

    public Integer getResourceid() {
        return resourceid;
    }

    public void setResourceid(Integer resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getResourcetoptypeid() {
        return resourcetoptypeid;
    }

    public void setResourcetoptypeid(Integer resourcetoptypeid) {
        this.resourcetoptypeid = resourcetoptypeid;
    }

    public Integer getResourcetypeid() {
        return resourcetypeid;
    }

    public void setResourcetypeid(Integer resourcetypeid) {
        this.resourcetypeid = resourcetypeid;
    }

    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public Integer getUpflag() {
        return upflag;
    }

    public void setUpflag(Integer upflag) {
        this.upflag = upflag;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mapid=").append(mapid);
        sb.append(", catalogid=").append(catalogid);
        sb.append(", resourceid=").append(resourceid);
        sb.append(", resourcetoptypeid=").append(resourcetoptypeid);
        sb.append(", resourcetypeid=").append(resourcetypeid);
        sb.append(", tableid=").append(tableid);
        sb.append(", seqno=").append(seqno);
        sb.append(", upflag=").append(upflag);
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