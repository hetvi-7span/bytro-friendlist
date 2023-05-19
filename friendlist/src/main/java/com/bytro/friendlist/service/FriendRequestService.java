package com.bytro.friendlist.service;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.record.response.EmailDetails;
import java.util.Optional;

public interface FriendRequestService {
    FriendRequest send(FriendRequest sendFriendRequest, EmailDetails emailDetails);

    Optional<FriendRequest> findPreviousPendingRequest(FriendRequest friendRequest);
}
