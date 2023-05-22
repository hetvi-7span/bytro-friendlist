package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.entity.Friends;
import com.bytro.friendlist.entity.FriendsId;
import com.bytro.friendlist.repository.FriendRepository;
import com.bytro.friendlist.service.FriendsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {
    private final FriendRepository friendRepository;

    FriendsServiceImpl(final FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        friendRepository.save(
                addFriend(friendRequest.getSenderId(), friendRequest.getReceiverId()));
        friendRepository.save(
                addFriend(friendRequest.getReceiverId(), friendRequest.getSenderId()));
    }

    private Friends addFriend(int userId, int friendId) {
        Friends friends = new Friends();
        friends.setId(new FriendsId(userId, friendId));
        return friends;
    }
}
