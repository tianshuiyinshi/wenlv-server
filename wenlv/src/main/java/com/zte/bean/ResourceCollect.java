package com.zte.bean;

import java.io.Serializable;

public class ResourceCollect implements Serializable {
    private Integer collectid;

    private Integer userid;

    private Integer resourceid;

    private Integer resourcetoptypeid;

    private Integer resourcetypeid;

    private Integer tableid;

    private String linkurl;

    private String createtime;

    private static final long serialVersionUID = 1L;

    public Integer getCollectid() {
        return collectid;
    }

    public void setCollectid(Integer collectid) {
        this.collectid = collectid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl == null ? null : linkurl.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", collectid=").append(collectid);
        sb.append(", userid=").append(userid);
        sb.append(", resourceid=").append(resourceid);
        sb.append(", resourcetoptypeid=").append(resourcetoptypeid);
        sb.append(", resourcetypeid=").append(resourcetypeid);
        sb.append(", tableid=").append(tableid);
        sb.append(", linkurl=").append(linkurl);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}