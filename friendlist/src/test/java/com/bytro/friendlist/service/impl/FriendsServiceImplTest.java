package com.bytro.friendlist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bytro.friendlist.entity.Friends;
import com.bytro.friendlist.entity.FriendsId;
import com.bytro.friendlist.repository.FriendRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FriendsServiceImplTest {

    @InjectMocks FriendsServiceImpl friendsService;

    @Mock FriendRepository friendRepository;
    private static final Friends FRIENDS = createFriends();

    private static Friends createFriends() {
        Friends friends = new Friends();
        friends.setId(new FriendsId(1, 2));
        friends.setBlocked(false);
        friends.setBlockedBy(3);
        return friends;
    }

    @Test
    void unfriend() {
        Integer userId = 1;
        Integer friendId = 2;

        when(friendRepository.findByIdUserIdAndIdFriendId(userId, friendId))
                .thenReturn(Optional.of(FRIENDS));
        assertDoesNotThrow(() -> friendsService.unfriend(userId, friendId));
        verify(friendRepository, times(1)).deleteByIdUserIdAndIdFriendId(userId, friendId);
        verify(friendRepository, times(1)).deleteByIdUserIdAndIdFriendId(friendId, userId);
    }
}
