package com.bytro.friendlist.service;

import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;

public interface FriendRequestService {
    BaseResponse<Void> send(SendFriendRequest sendFriendRequest);
}
