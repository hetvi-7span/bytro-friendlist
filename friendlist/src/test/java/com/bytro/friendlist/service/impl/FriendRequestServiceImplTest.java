package com.bytro.friendlist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FriendRequestServiceImplTest {
    @InjectMocks FriendRequestServiceImpl friendRequestService;
    @Mock FriendRequestRepository friendRequestRepository;
    @Mock FriendsService friendsService;

    private static final FriendRequest REQUEST = createFriendRequest();

    private static FriendRequest createFriendRequest() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(1);
        friendRequest.setReceiverId(2);
        friendRequest.setStatus(FriendRequestStatus.SENT);
        return friendRequest;
    }

    @Test
    void rejectFriendRequest() {

        when(friendRequestRepository.findByIdAndReceiverIdAndStatus(
                        REQUEST.getId(), REQUEST.getReceiverId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.of(REQUEST));

        friendRequestService.rejectFriendRequest(REQUEST);
        assertEquals(FriendRequestStatus.REJECTED, REQUEST.getStatus());
        verify(friendRequestRepository, times(1)).save(REQUEST);
    }

    @Test
    void acceptFriendRequest() {
        when(friendRequestRepository.findByIdAndReceiverIdAndStatus(
                        REQUEST.getId(), REQUEST.getReceiverId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.of(REQUEST));

        friendRequestService.acceptFriendRequest(REQUEST);

        assertEquals(FriendRequestStatus.ACCEPTED, REQUEST.getStatus());
        verify(friendRequestRepository, times(1)).save(REQUEST);
        verify(friendsService, times(1)).acceptFriendRequest(REQUEST);
    }
}
