package com.zte.bean.vo;

import com.zte.bean.Search;

/**
 * @author yinsiwei
 * @date 2020-08-18 18:19
 */
public class SearchVo extends Search {
    private static final long serialVersionUID = 1L;

    private Integer keywordsCount;

    private String  startTime;

    private String  endTime;


    public Integer getKeywordsCount() {
        return keywordsCount;
    }

    public void setKeywordsCount(Integer keywordsCount) {
        this.keywordsCount = keywordsCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
