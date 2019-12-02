package com.zhy.mapper;

import com.zhy.model.DailySpeech;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/11/28 15:35
 * Describe:
 */
@Mapper
@Repository
public interface TodayMapper {

    @Insert("insert into daily_speech(content,mood,picsUrl,publishDate) values(#{content}, #{mood}, #{picsUrl}, #{publishDate})")
    void save(DailySpeech dailySpeech);

    @Select("select * from daily_speech order by id desc")
    List<DailySpeech> getTodayInfo();

}
