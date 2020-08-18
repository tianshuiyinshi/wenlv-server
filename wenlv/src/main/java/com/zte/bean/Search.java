package com.zte.bean;

import java.io.Serializable;

public class Search implements Serializable {
    private Integer searchid;

    private Integer userid;

    private String keywords;

    private String createtime;

    private static final long serialVersionUID = 1L;

    public Integer getSearchid() {
        return searchid;
    }

    public void setSearchid(Integer searchid) {
        this.searchid = searchid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
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
        sb.append(", searchid=").append(searchid);
        sb.append(", userid=").append(userid);
        sb.append(", keywords=").append(keywords);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}