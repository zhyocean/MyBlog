package com.zhy.service.impl;

import com.zhy.constant.CodeType;
import com.zhy.mapper.RewardMapper;
import com.zhy.model.Reward;
import com.zhy.service.RewardService;
import com.zhy.utils.DataMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhangocean
 * @Date: 2019/7/14 15:45
 * Describe:
 */
@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardMapper rewardMapper;

    @Override
    public DataMap save(Reward reward) {
        try {
            rewardMapper.save(reward);
        } catch (Exception e){
            e.printStackTrace();
            log.error("add reward exception", e);
            return DataMap.fail(CodeType.ADD_REWARD_EXCEPTION);
        }
        return DataMap.success(CodeType.ADD_REWARD_SUCCESS)
                .setData(reward.getId());
    }

    @Override
    public DataMap getRewardInfo() {
        List<Reward> rewardList = rewardMapper.getAllReward();
        return DataMap.success().setData(rewardList);
    }

    @Override
    public DataMap deleteReward(int id) {
        try {
            rewardMapper.deleteRewardById(id);
        } catch (Exception e){
            log.error("delete reward exception", e);
            return DataMap.fail(CodeType.DELETE_REWARD_EXCEPTION);
        }
        return DataMap.success(CodeType.DELETE_REWARD_SUCCESS);
    }
}
