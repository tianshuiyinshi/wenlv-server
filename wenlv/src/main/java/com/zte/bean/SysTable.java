package com.zte.bean;

import java.io.Serializable;

public class SysTable implements Serializable {
    private Integer tableid;

    private String tablename;

    private String tabledesc;

    private static final long serialVersionUID = 1L;

    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
    }

    public String getTabledesc() {
        return tabledesc;
    }

    public void setTabledesc(String tabledesc) {
        this.tabledesc = tabledesc == null ? null : tabledesc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tableid=").append(tableid);
        sb.append(", tablename=").append(tablename);
        sb.append(", tabledesc=").append(tabledesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}