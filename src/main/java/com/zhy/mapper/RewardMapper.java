package com.zhy.mapper;

import com.zhy.model.Reward;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2019/7/14 15:47
 * Describe:
 */
@Repository
@Mapper
public interface RewardMapper {

    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, statementType = StatementType.STATEMENT, resultType = int.class)
    @Insert("insert into reward(fundRaiser,fundRaisingSources,fundraisingPlace,rewardMoney,remarks,rewardDate,rewardUrl) " +
            "values(#{fundRaiser},#{fundRaisingSources},#{fundraisingPlace},#{rewardMoney},#{remarks},#{rewardDate},#{rewardUrl})")
    int save(Reward reward);

    @Select("select * from reward order by rewardDate desc")
    List<Reward> getAllReward();

    @Delete("delete from reward where id=#{id}")
    int deleteRewardById(int id);
}
