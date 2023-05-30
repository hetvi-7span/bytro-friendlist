package com.bytro.friendlist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import java.util.ArrayList;
import java.util.List;
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

    private static final List<FriendRequest> REQUESTLIST = createFriendRequestList();

    private static List<FriendRequest> createFriendRequestList() {
        FriendRequest friendRequest = new FriendRequest();
        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(new FriendRequest(1, 2, 1, FriendRequestStatus.SENT, "hii"));
        friendRequestList.add(new FriendRequest(2, 3, 1, FriendRequestStatus.SENT, "hii"));
        friendRequestList.add(new FriendRequest(3, 4, 1, FriendRequestStatus.SENT, "hii"));
        return friendRequestList;
    }

    @Test
    void getFriendRequestList() {
        Integer userId = 1;
        Integer page = 0;
        Integer size = 3;

        Page<FriendRequest> friendRequestPage = new PageImpl<>(REQUESTLIST);

        when(friendRequestRepository.findByReceiverIdAndStatus(
                        eq(userId), eq(FriendRequestStatus.SENT), any(Pageable.class)))
                .thenReturn(friendRequestPage);

        final var resultPage = friendRequestService.getFriendRequestList(userId, page, size);
        assertEquals(REQUESTLIST.size(), resultPage.getTotalElements());
        assertEquals(page, resultPage.getNumber());
        assertEquals(size, resultPage.getSize());
        assertEquals(REQUESTLIST, resultPage.getContent());
    }
}
