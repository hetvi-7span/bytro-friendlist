package com.bytro.friendlist.service;

import com.bytro.friendlist.entity.FriendRequest;

public interface FriendsService {
    void acceptFriendRequest(FriendRequest friendRequest);

    void unfriend(Integer userId, Integer friendId);

    void block(Integer userId, Integer friendId);

    void unblock(Integer userId, Integer friendId);
}
