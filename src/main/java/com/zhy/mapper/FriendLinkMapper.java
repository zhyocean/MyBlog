package com.zhy.mapper;

import com.zhy.model.FriendLink;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2019/5/16 17:12
 * Describe:
 */
@Repository
@Mapper
public interface FriendLinkMapper {

    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, statementType = StatementType.STATEMENT,resultType=int.class)
    @Insert("insert into friendlink(blogger,url) values(#{blogger},#{url})")
    int save(FriendLink friendLink);

    @Update("update friendlink set blogger=#{friendLink.blogger},url=#{friendLink.url} where id=#{id}")
    void updateFriendLink(@Param("friendLink") FriendLink friendLink, @Param("id") int id);

    @Select("select * from friendlink")
    List<FriendLink> getAllFriendLink();

    @Select("select IFNULL((select id from friendlink where blogger=#{blogger}), 0)")
    int findIsExistByBlogger(String blogger);

    @Delete("delete from friendlink where id=#{id}")
    void deleteFriendLinkById(int id);

}
