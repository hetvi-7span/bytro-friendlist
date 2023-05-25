package com.bytro.friendlist.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.bytro.friendlist.entity.Friends;
import com.bytro.friendlist.entity.FriendsId;
import com.bytro.friendlist.utils.TestConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles(TestConstant.TEST_PROFILE)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FriendRepositoryTest {
    @Autowired FriendRepository friendRepository;
    private static final Friends FRIENDS = createFriends();

    private static Friends createFriends() {
        Friends friends = new Friends();
        friends.setId(new FriendsId(1, 2));
        friends.setBlocked(false);
        friends.setBlockedBy(3);
        return friends;
    }

    @Test
    void deleteByIdUserIdAndIdFriendId() {
        friendRepository.save(FRIENDS);
        friendRepository.deleteByIdUserIdAndIdFriendId(1, 2);
        assertFalse(friendRepository.findByIdUserIdAndIdFriendId(1, 2).isPresent());
    }

    @Test
    void findByIdUserIdAndIdFriendId() {
        friendRepository.save(FRIENDS);
        friendRepository.findByIdUserIdAndIdFriendId(1, 2);
        assertTrue(friendRepository.findByIdUserIdAndIdFriendId(1, 2).isPresent());
    }
}
