package com.zhy.service;

import com.zhy.model.FriendLink;
import com.zhy.utils.DataMap;

/**
 * @author: zhangocean
 * @Date: 2019/5/16 17:08
 * Describe:
 */
public interface FriendLinkService {

    DataMap addFriendLink(FriendLink friendLink);

    DataMap getAllFriendLink();

    DataMap updateFriendLink(FriendLink friendLink, int id);

    DataMap deleteFriendLink(int id);

    DataMap getFriendLink();
}
