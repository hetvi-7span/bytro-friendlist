package com.bytro.friendlist.service;

import com.bytro.friendlist.entity.FriendRequest;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FriendRequestService {
    FriendRequest send(FriendRequest sendFriendRequest);

    Optional<FriendRequest> findPreviousPendingRequest(FriendRequest friendRequest);

    void rejectFriendRequest(FriendRequest friendRequest);

    void acceptFriendRequest(FriendRequest friendRequest);

    Page<FriendRequest> getFriendRequestList(Integer userId, Integer page, Integer size);

    void cancel(Integer requestId, Integer senderId);

    FriendRequest getStatus(int friendRequestId);
}
