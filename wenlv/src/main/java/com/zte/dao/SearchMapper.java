package com.zte.dao;

import com.zte.bean.Search;
import com.zte.bean.vo.SearchVo;

import java.util.List;

public interface SearchMapper {
    int deleteByPrimaryKey(Integer searchid);

    int insert(Search record);

    int insertSelective(Search record);

    Search selectByPrimaryKey(Integer searchid);

    int updateByPrimaryKeySelective(Search record);

    int updateByPrimaryKey(Search record);

    List<SearchVo> selectAllRows(Search record);
}