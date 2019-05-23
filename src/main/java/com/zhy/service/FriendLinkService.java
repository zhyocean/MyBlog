package com.zhy.service;

import com.zhy.model.FriendLink;
import com.zhy.model.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author: zhangocean
 * @Date: 2019/5/16 17:08
 * Describe:
 */
public interface FriendLinkService {

    Result addFriendLink(FriendLink friendLink);

    JSONArray getAllFriendLink();

    Result updateFriendLink(FriendLink friendLink, int id);

    Result deleteFriendLink(int id);

    Result getFriendLink();
}
