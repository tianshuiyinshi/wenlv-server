package com.zte.service;

import com.zte.bean.Search;
import com.zte.bean.vo.SearchVo;
import com.zte.dao.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yinsiwei
 * @date 2020-08-18 18:12
 */
@Service
public class SearchService {

    @Autowired
    SearchMapper searchMapper;

    public Boolean addRecord(Search record){
        return searchMapper.insertSelective(record)==1?true:false;
    }

    public Boolean deleteByRecord(Integer searchId){
        return searchMapper.deleteByPrimaryKey(searchId)==1?true:false;
    }

    public Boolean updateByRecord(Search record){
        return searchMapper.updateByPrimaryKeySelective(record)==1?true:false;
    }

    public List<SearchVo> queryAllRows(Search record){
        return searchMapper.selectAllRows(record);
    }


}
