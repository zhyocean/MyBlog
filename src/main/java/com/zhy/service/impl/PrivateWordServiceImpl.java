package com.zhy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhy.mapper.PrivateWordMapper;
import com.zhy.model.PrivateWord;
import com.zhy.service.PrivateWordService;
import com.zhy.service.UserService;
import com.zhy.utils.DataMap;
import com.zhy.utils.StringUtil;
import com.zhy.utils.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhangocean
 * @Date: 2018/7/22 20:21
 * Describe:
 */
@Service
public class PrivateWordServiceImpl implements PrivateWordService {

    @Autowired
    private PrivateWordMapper privateWordMapper;
    @Autowired
    UserService userService;

    @Override
    public DataMap publishPrivateWord(String privateWordContent, String username) {
        TimeUtil timeUtil = new TimeUtil();
        PrivateWord privateWord = new PrivateWord(privateWordContent, userService.findIdByUsername(username), timeUtil.getFormatDateForSix());
        privateWordMapper.save(privateWord);
        return DataMap.success();
    }

    @Override
    public DataMap getPrivateWordByPublisher(String publisher, int rows, int pageNum) {
        int publisherId = userService.findIdByUsername(publisher);
        PageHelper.startPage(pageNum, rows);
        List<PrivateWord> privateWords = privateWordMapper.getPrivateWordByPublisher(publisherId);
        PageInfo<PrivateWord> pageInfo = new PageInfo<>(privateWords);

        JSONObject returnJson = new JSONObject();
        JSONObject privateWordJson;
        JSONArray privateWordJsonArray = new JSONArray();
        for(PrivateWord privateWord : privateWords){
            privateWordJson = new JSONObject();
            privateWordJson.put("privateWord", privateWord.getPrivateWord());
            privateWordJson.put("publisher", publisher);
            if(privateWord.getReplyContent() == null){
                privateWordJson.put("replier", StringUtil.BLANK);
                privateWordJson.put("replyContent", StringUtil.BLANK);
            } else {
                privateWordJson.put("replier", userService.findUsernameById(privateWord.getReplierId()));
                privateWordJson.put("replyContent", privateWord.getReplyContent());
            }
            String publisherDate = privateWord.getPublisherDate().substring(0,4) + "年" + privateWord.getPublisherDate().substring(5,7) + "月" +
                    privateWord.getPublisherDate().substring(8,10) + "日" + privateWord.getPublisherDate().substring(11);
            privateWordJson.put("publisherDate", publisherDate);
            privateWordJsonArray.add(privateWordJson);
        }
        returnJson.put("result",privateWordJsonArray);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum",pageInfo.getPageNum());
        pageJson.put("pageSize",pageInfo.getPageSize());
        pageJson.put("total",pageInfo.getTotal());
        pageJson.put("pages",pageInfo.getPages());
        pageJson.put("isFirstPage",pageInfo.isIsFirstPage());
        pageJson.put("isLastPage",pageInfo.isIsLastPage());

        returnJson.put("pageInfo",pageJson);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap getAllPrivateWord() {
        List<PrivateWord> privateWords = privateWordMapper.getAllPrivateWord();

        JSONObject returnJson = new JSONObject();
        JSONObject userJson;
        JSONArray allJsonArray = new JSONArray();
        JSONObject newUserJson;

        List<String> publishers = new ArrayList<>();
        String publisher;
        for(PrivateWord privateWord : privateWords){
            userJson = new JSONObject();
            userJson.put("privateWord", privateWord.getPrivateWord());
            publisher = userService.findUsernameById(privateWord.getPublisherId());
            userJson.put("publisher", publisher);
            userJson.put("publisherDate", privateWord.getPublisherDate());
            userJson.put("id", privateWord.getId());
            if(privateWord.getReplyContent() == null){
                userJson.put("replier", StringUtil.BLANK);
                userJson.put("replyContent", StringUtil.BLANK);
            } else {
                userJson.put("replyContent",privateWord.getReplyContent());
                userJson.put("replier",userService.findUsernameById(privateWord.getReplierId()));
            }
            if(!publishers.contains(publisher)){
                publishers.add(publisher);
                newUserJson = new JSONObject();
                newUserJson.put("publisher",publisher);
                newUserJson.put("content",new JSONArray());
                allJsonArray.add(newUserJson);
            }
            for(int i=0;i<allJsonArray.size();i++){
                JSONObject arrayUser = (JSONObject) allJsonArray.get(i);
                if(arrayUser.get("publisher").equals(publisher)){
                    JSONArray jsonArray = (JSONArray) arrayUser.get("content");
                    jsonArray.add(userJson);
                    arrayUser.put("publisher", publisher);
                    arrayUser.put("content", jsonArray);
                    allJsonArray.remove(i);

                    allJsonArray.add(arrayUser);
                    break;
                }
            }
        }
        returnJson.put("result",allJsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap replyPrivateWord(String replyContent, String username, int id) {
        Map<String, Object> dataMap = new HashMap<>(4);
        privateWordMapper.replyPrivateWord(replyContent, userService.findIdByUsername(username), id);
        dataMap.put("replyContent",replyContent);
        dataMap.put("replyId",id);
        return DataMap.success().setData(dataMap);
    }
}
