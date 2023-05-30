package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.EmailService;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.response.EmailDetails;
import com.bytro.friendlist.utils.Constant;
import com.bytro.friendlist.utils.EmailUtils;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final MessageSource messageSource;
    private final FriendsService friendsService;
    private final EmailService emailService;

    private final EmailUtils emailUtils;

    FriendRequestServiceImpl(
            final FriendRequestRepository friendRequestRepository,
            final FriendsService friendsService,
            final MessageSource messageSource,
            final EmailService emailService,
            final EmailUtils emailUtils) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendsService = friendsService;
        this.messageSource = messageSource;
        this.emailService = emailService;
        this.emailUtils = emailUtils;
    }

    @Override
    public FriendRequest send(FriendRequest friendRequest) {
        validateFriendsEligibility(friendRequest);
        friendRequest.setStatus(FriendRequestStatus.SENT);
        sendFriendRequestEmail(friendRequest.getMessage());
        return friendRequestRepository.save(friendRequest);
    }

    private void validateFriendsEligibility(FriendRequest friendRequest) {
        // Check if user blocked you or not
        friendsService.checkIfUserIsBlockedOrNot(
                friendRequest.getSenderId(), friendRequest.getReceiverId());

        Optional<FriendRequest> previousPendingRequest = findPreviousPendingRequest(friendRequest);
        if (previousPendingRequest.isPresent()) {

            // check if both users are friends or not
            if (previousPendingRequest.get().getStatus().equals(FriendRequestStatus.ACCEPTED)) {
                throw new CustomException(
                        ResultCode.ALREADY_FRIENDS.getValue(),
                        messageSource.getMessage(
                                "already.friends", new String[] {}, Locale.getDefault()));
            }

            // check if friend request is previously sent or not
            if (previousPendingRequest.get().getStatus().equals(FriendRequestStatus.SENT)) {
                throw new CustomException(
                        ResultCode.FRIEND_REQUEST_ALREADY_SENT.getValue(),
                        messageSource.getMessage(
                                "friend.request.already.sent",
                                new String[] {},
                                Locale.getDefault()));
            }
        }
        // check if friend request already received by user
        Optional<FriendRequest> friendRequestAlreadyReceivedByUser =
                friendRequestRepository.findByReceiverIdAndSenderIdOrderByIdDesc(
                        friendRequest.getSenderId(), friendRequest.getReceiverId());
        if (friendRequestAlreadyReceivedByUser.isPresent()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_ALREADY_RECEIVED.getValue(),
                    messageSource.getMessage(
                            "friend.request.already.received",
                            new String[] {},
                            Locale.getDefault()));
        }
    }

    private void sendFriendRequestEmail(String message) {
        EmailDetails emailDetails = emailUtils.createEmailTemplate(message);
        emailService.sendFriendRequestMail(emailDetails);
    }

    @Override
    public Optional<FriendRequest> findPreviousPendingRequest(FriendRequest friendRequest) {
        return friendRequestRepository.findBySenderIdAndReceiverIdOrderByIdDesc(
                friendRequest.getSenderId(), friendRequest.getReceiverId());
    }

    @Override
    public void rejectFriendRequest(FriendRequest friendRequest) {
        FriendRequest validFriendRequest = validateFriendRequest(friendRequest);
        validFriendRequest.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(validFriendRequest);
    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        FriendRequest validFriendRequest = validateFriendRequest(friendRequest);
        if (validFriendRequest.getReceiverId() == friendRequest.getReceiverId()) {
            validFriendRequest.setStatus(FriendRequestStatus.ACCEPTED);
            friendRequestRepository.save(validFriendRequest);
            friendsService.acceptFriendRequest(validFriendRequest);
        }
    }

    private FriendRequest validateFriendRequest(FriendRequest friendRequest) {
        Optional<FriendRequest> validFriendRequest =
                friendRequestRepository.findByIdAndStatus(
                        friendRequest.getId(), FriendRequestStatus.SENT);
        if (validFriendRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found",
                            new String[] {Constant.FRIEND_REQUEST},
                            Locale.getDefault()));
        }
        return validFriendRequest.get();
    }

    @Override
    public Page<FriendRequest> getFriendRequestList(Integer userId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return friendRequestRepository.findByReceiverIdAndStatus(
                userId, FriendRequestStatus.SENT, paging);
    }

    @Override
    public void cancel(Integer requestId, Integer senderId) {
        Optional<FriendRequest> validFriendRequest =
                friendRequestRepository.findByIdAndStatus(requestId, FriendRequestStatus.SENT);
        if (validFriendRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIEND_RECORD_NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found", new String[] {Constant.FRIEND_REQUEST}, Locale.US));
        }

        FriendRequest friendRequest = validFriendRequest.get();

        if (friendRequest.getSenderId() == senderId) {
            friendRequest.setStatus(FriendRequestStatus.CANCELED);
            friendRequestRepository.save(friendRequest);
        }
    }

    @Override
    public FriendRequest getStatus(int friendRequestId) {
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(friendRequestId);
        if (friendRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found",
                            new String[] {Constant.FRIEND_REQUEST},
                            Locale.getDefault()));
        }
        return friendRequest.get();
    }
}
