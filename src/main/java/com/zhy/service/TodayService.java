package com.zhy.service;

import com.zhy.model.DailySpeech;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: zhangocean
 * @Date: 2018/11/28 15:33
 * Describe: 藏心阁-今日
 */
public interface TodayService {

    JSONObject publishISay(DailySpeech dailySpeech);

    JSONObject getTodayInfo(int rows, int pageNum);

}
