package com.zhy.mapper;

import com.zhy.model.Visitor;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangocean
 * @Date: 2018/6/16 16:22
 * Describe: 访客sql
 */
@Mapper
@Repository
public interface VisitorMapper {

    @Insert("insert into visitor(visitorNum,pageName) values(0,#{pageName})")
    void save(String pageName);

    @Select("select * from visitor where pageName=#{pageName}")
    Visitor getVisitorNumByPageName(@Param("pageName") String pageName);

    @Select("select visitorNum from visitor where pageName='totalVisitor'")
    long getTotalVisitor();

    @Update("update visitor set visitorNum=#{visitorNum} where pageName=#{pageName}")
    void updateVisitorNumByPageName(@Param("pageName") String pageName, @Param("visitorNum") String visitorNum);
}
