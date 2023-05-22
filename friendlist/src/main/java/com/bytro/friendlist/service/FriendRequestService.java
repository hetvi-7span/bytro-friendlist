package com.bytro.friendlist.service;

import com.bytro.friendlist.entity.FriendRequest;
import java.util.Optional;

public interface FriendRequestService {
    FriendRequest send(FriendRequest sendFriendRequest);

    Optional<FriendRequest> findPreviousPendingRequest(FriendRequest friendRequest);
}
