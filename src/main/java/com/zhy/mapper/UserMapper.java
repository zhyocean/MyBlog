package com.zhy.mapper;

import com.zhy.model.Role;
import com.zhy.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2018/6/4 15:52
 * Describe: user表SQL语句
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where phone=#{phone}")
    @Results({
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "phone", property = "roles", javaType = List.class, many = @Many(select = "com.zhy.mapper.UserMapper.getRoleNameByPhone")),
    })
    User getUsernameAndRolesByPhone(@Param("phone") String phone);

    @Select("select r.name from user u LEFT JOIN user_role sru on u.id= sru.User_id LEFT JOIN role r on sru.Role_id=r.id where phone=#{phone}")
    Role getRoleNameByPhone(String phone);

    @Select("select * from user where phone=#{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Select("select username from user where id=#{id}")
    String findUsernameById(int id);

    @Insert("insert into user(phone,username,password,gender,avatarImgUrl) values(#{phone},#{username},#{password},#{gender},#{avatarImgUrl})")
    void save(User user);

    @Select("select username from user where phone=#{phone}")
    User findUsernameByPhone(@Param("phone") String phone);

    @Select("select * from user where username=#{username}")
    User findUsernameByUsername(@Param("username") String username);

    @Insert("insert into user_role(User_id, Role_id) values (#{userId}, #{roleId})")
    void saveRole(@Param("userId") int userId, @Param("roleId") int roleId);

    @Select("select Role_id from user_role where User_id=#{userId}")
    List<Object> findRoleIdByUserId(@Param("userId") int userId);

    @Select("select id from user where phone=#{phone}")
    int findUserIdByPhone(@Param("phone") String phone);

    @Update("update user set password=#{password} where phone=#{phone}")
    void updatePassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select phone from user where username=#{username}")
    String findPhoneByUsername(@Param("username") String username);

    @Select("select id from user where username=#{username}")
    int findIdByUsername(String username);

    @Update("update user set recentlyLanded=#{recentlyLanded} where phone=#{phone}")
    void updateRecentlyLanded(@Param("phone") String phone, @Param("recentlyLanded") String recentlyLanded);

    @Update("update user set avatarImgUrl=#{avatarImgUrl} where id=#{id}")
    void updateAvatarImgUrlById(@Param("avatarImgUrl") String avatarImgUrl, @Param("id") int id);

    @Select("select avatarImgUrl from user where id=#{id}")
    String getHeadPortraitUrl(@Param("id") int id);

    @Select("select * from user where username=#{username}")
    User getUserPersonalInfoByUsername(@Param("username") String username);

    @Update("update user set username=#{user.username},gender=#{user.gender},trueName=#{user.trueName},birthday=#{user.birthday},email=#{user.email},personalBrief=#{user.personalBrief} where username=#{username}")
    void savePersonalDate(@Param("user") User user, @Param("username") String username);

    @Select("select count(*) from user")
    int countUserNum();
}
