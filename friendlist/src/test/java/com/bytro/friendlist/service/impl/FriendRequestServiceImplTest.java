package com.bytro.friendlist.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.EmailService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import com.bytro.friendlist.utils.Constant;
import com.bytro.friendlist.utils.EmailUtils;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FriendRequestServiceImplTest {
    @InjectMocks private FriendRequestServiceImpl friendRequestService;
    @Mock private FriendRequestRepository friendRequestRepository;
    @Mock private EmailService emailService;
    @Mock private EmailUtils emailUtils;
    private static final FriendRequest REQUEST = createFriendRequest();

    private static FriendRequest createFriendRequest() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(1);
        friendRequest.setReceiverId(2);
        friendRequest.setStatus(FriendRequestStatus.SENT);
        return friendRequest;
    }

    @Test
    void send() {
        when(friendRequestRepository.findBySenderIdAndReceiverIdAndStatus(
                        REQUEST.getSenderId(), REQUEST.getReceiverId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.empty());
        when(friendRequestRepository.save(REQUEST)).thenReturn(REQUEST);
        final var result = friendRequestService.send(REQUEST);
        assertNotNull(result);
        assertEquals(FriendRequestStatus.SENT, result.getStatus());
    }
}
