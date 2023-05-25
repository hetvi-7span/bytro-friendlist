package com.bytro.friendlist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        friendRequest.setSenderId(2);
        friendRequest.setStatus(FriendRequestStatus.SENT);
        return friendRequest;
    }

    @Test
    void rejectFriendRequest() {

        when(friendRequestRepository.findByIdAndStatus(REQUEST.getId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.of(REQUEST));

        friendRequestService.rejectFriendRequest(REQUEST);
        assertEquals(FriendRequestStatus.REJECTED, REQUEST.getStatus());
        verify(friendRequestRepository, times(1)).save(REQUEST);
    }

    @Test
    void acceptFriendRequest() {
        when(friendRequestRepository.findByIdAndStatus(REQUEST.getId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.of(REQUEST));

        friendRequestService.acceptFriendRequest(REQUEST);

        assertEquals(FriendRequestStatus.ACCEPTED, REQUEST.getStatus());
        verify(friendRequestRepository, times(1)).save(REQUEST);
        verify(friendsService, times(1)).acceptFriendRequest(REQUEST);
    }

    @Test
    void getFriendRequestList() {
        Integer userId = 1;
        Integer page = 0;
        Integer size = 3;

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(new FriendRequest(1, 2, userId, FriendRequestStatus.SENT, "hii"));
        friendRequestList.add(new FriendRequest(2, 3, userId, FriendRequestStatus.SENT, "hii"));
        friendRequestList.add(new FriendRequest(3, 4, userId, FriendRequestStatus.SENT, "hii"));

        Page<FriendRequest> friendRequestPage = new PageImpl<>(friendRequestList);

        when(friendRequestRepository.findByReceiverIdAndStatus(
                        eq(userId), eq(FriendRequestStatus.SENT), any(Pageable.class)))
                .thenReturn(friendRequestPage);

        final var resultPage = friendRequestService.getFriendRequestList(userId, page, size);

        assertEquals(friendRequestList.size(), resultPage.getTotalElements());
        assertEquals(page, resultPage.getNumber());
        assertEquals(size, resultPage.getSize());
        assertEquals(friendRequestList, resultPage.getContent());
    }

    @Test
    void cancel() {
        Integer requestId = 1;
        Integer senderId = 2;
        when(friendRequestRepository.findByIdAndStatus(REQUEST.getId(), FriendRequestStatus.SENT))
                .thenReturn(Optional.of(REQUEST));
        when(friendRequestRepository.save(REQUEST)).thenReturn(REQUEST);
        friendRequestService.cancel(requestId, senderId);
        verify(friendRequestRepository, times(1)).save(REQUEST);
    }
}
