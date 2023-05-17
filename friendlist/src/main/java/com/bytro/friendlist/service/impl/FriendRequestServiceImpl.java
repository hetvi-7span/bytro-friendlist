package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.transformer.FriendRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestMapper friendRequestMapper;
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestServiceImpl(
            FriendRequestMapper friendRequestMapper,
            FriendRequestRepository friendRequestRepository) {
        this.friendRequestMapper = friendRequestMapper;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public BaseResponse<Void> send(SendFriendRequest sendFriendRequest) {
        FriendRequest friendRequest = friendRequestMapper.RequestToEntity(sendFriendRequest);
        friendRequestRepository.save(friendRequest);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(), "Friend request sent successfully");
    }
}
