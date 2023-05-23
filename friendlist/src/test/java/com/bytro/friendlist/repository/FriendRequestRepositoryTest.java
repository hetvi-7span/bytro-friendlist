package com.bytro.friendlist.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
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
class FriendRequestRepositoryTest {
    @Autowired private FriendRequestRepository friendRequestRepository;

    private static final FriendRequest REQUEST = createFriendRequest();

    private static FriendRequest createFriendRequest() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(1);
        friendRequest.setReceiverId(2);
        friendRequest.setStatus(FriendRequestStatus.SENT);
        return friendRequest;
    }

    @Test
    void findByIdAndReceiverIdAndStatus() {
        friendRequestRepository.save(REQUEST);
        final var result =
                friendRequestRepository.findByIdAndReceiverIdAndStatus(
                        REQUEST.getId(), REQUEST.getReceiverId(), FriendRequestStatus.SENT);
        assertTrue(result.isPresent());
        assertEquals(REQUEST, result.get());
    }
}
