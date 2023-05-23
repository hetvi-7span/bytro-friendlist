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

        Optional<FriendRequest> previousPendingRequest = findPreviousPendingRequest(friendRequest);
        if (previousPendingRequest.isPresent()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_ALREADY_SENT.getValue(),
                    messageSource.getMessage(
                            "friend.request.already.sent", new String[] {}, Locale.US));
        }
        friendRequest.setStatus(FriendRequestStatus.SENT);
        sendFriendRequestEmail(friendRequest.getMessage());

        return friendRequestRepository.save(friendRequest);
    }

    private void sendFriendRequestEmail(String message) {
        EmailDetails emailDetails = emailUtils.createEmailTemplate(message);
        emailService.sendFriendRequestMail(emailDetails);
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
        FriendRequest validFriendRequest = validateFriendRequest(friendRequest);
        validFriendRequest.setStatus(FriendRequestStatus.REJECTED);
        friendRequestRepository.save(validFriendRequest);
    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        FriendRequest validFriendRequest = validateFriendRequest(friendRequest);
        validFriendRequest.setStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.save(validFriendRequest);
        friendsService.acceptFriendRequest(friendRequest);
    }

    private FriendRequest validateFriendRequest(FriendRequest friendRequest) {
        Optional<FriendRequest> validFriendRequest =
                friendRequestRepository.findByIdAndReceiverIdAndStatus(
                        friendRequest.getId(),
                        friendRequest.getReceiverId(),
                        FriendRequestStatus.SENT);
        if (validFriendRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIEND_REQUEST_NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found", new String[] {Constant.FRIEND_REQUEST}, Locale.US));
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
    public void cancelFriendRequest(Integer requestId, Integer senderId) {
        Optional<FriendRequest> validFriendRequest =
                friendRequestRepository.findByIdAndSenderIdAndStatus(
                        requestId, senderId, FriendRequestStatus.SENT);
        if (validFriendRequest.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIEND_RECORD_NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found", new String[] {Constant.FRIEND_REQUEST}, Locale.US));
        }

        FriendRequest friendRequest = validFriendRequest.get();
        friendRequest.setStatus(FriendRequestStatus.CANCELED);
        friendRequestRepository.save(friendRequest);
    }
}
