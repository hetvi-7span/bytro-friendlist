package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import com.bytro.friendlist.shared.enums.ResultCode;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendsService friendsService;
    private final MessageSource messageSource;

    FriendRequestServiceImpl(
            final FriendRequestRepository friendRequestRepository,
            final FriendsService friendsService,
            final MessageSource messageSource) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendsService = friendsService;
        this.messageSource = messageSource;
    }

    @Override
    public FriendRequest send(FriendRequest friendRequest) {

        Optional<FriendRequest> previousPendingRequest = findPreviousPendingRequest(friendRequest);
        if (previousPendingRequest.isPresent()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_ALREADY_SENT.getValue(),
                    messageSource.getMessage(
                            "friend.request.already.sent", new String[] {}, Locale.US));
        }
        friendRequest.setStatus(FriendRequestStatus.SENT);
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public Optional<FriendRequest> findPreviousPendingRequest(FriendRequest friendRequest) {
        return friendRequestRepository.findBySenderIdAndReceiverIdAndStatus(
                friendRequest.getSenderId(),
                friendRequest.getReceiverId(),
                FriendRequestStatus.SENT);
    }

    @Override
    public void rejectFriendRequest(FriendRequest friendRequest) {
        FriendRequest previousPendingRequest = previousPendingRequest(friendRequest);
        previousPendingRequest.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(previousPendingRequest);
    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        FriendRequest previousPendingRequest = previousPendingRequest(friendRequest);
        previousPendingRequest.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(previousPendingRequest);
        friendsService.acceptFriendRequest(friendRequest);
    }

    private FriendRequest previousPendingRequest(FriendRequest friendRequest) {
        Optional<FriendRequest> previousPendingRequest = findPreviousPendingRequest(friendRequest);
        if (previousPendingRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.USER_NOT_FOUND.getValue(),
                    messageSource.getMessage("no.data.found", new String[] {}, Locale.US));
        }
        return previousPendingRequest.get();
    }
}
