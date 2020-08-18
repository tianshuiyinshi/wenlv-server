package com.zte.common.config;

import com.alibaba.fastjson.JSON;
import com.zte.bean.dto.ResourceDto;
import com.zte.bean.vo.SearchVo;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.SensitiveWordUtil;
import com.zte.dao.SearchMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author yinsiwei
 * @date 2020-08-18 17:13
 */
@Aspect
@Configuration
public class HotWordsAspect {

    @Autowired
    SearchMapper searchMapper;


    @Pointcut("execution(* com.zte.rest..*.find*(Object)) && args(record)")
    private void searchResourcePointcut(Object record){

    }

    @Around(value = "searchResourcePointcut(record)", argNames = "record")
    public void addHotWords(ProceedingJoinPoint joinpoint,Object record){
        ResourceDto resourceDto = JSON.parseObject(JSON.toJSONString(record), ResourceDto.class);
        String resourcetitle = resourceDto.getResourcetitle();
        String resourcedetail = resourceDto.getResourcedetail();
        if((resourcetitle!=null||resourcedetail!=null)&&!SensitiveWordUtil.contains(resourcetitle)&&!SensitiveWordUtil.contains(resourcedetail)){
            String dbDatetime = DateUtil.getDBDatetime();
            SearchVo searchVo = new SearchVo();
            searchVo.setCreatetime(dbDatetime);
            searchVo.setKeywords(resourcetitle);
            searchMapper.insertSelective(searchVo);
            searchVo.setKeywords(resourcedetail);
            searchMapper.insertSelective(searchVo);
        }
    }
}
