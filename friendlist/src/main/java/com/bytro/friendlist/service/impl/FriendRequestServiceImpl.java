package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.repository.FriendRequestRepository;
import com.bytro.friendlist.service.EmailService;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.response.EmailDetails;
import com.bytro.friendlist.utils.EmailUtils;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final MessageSource messageSource;

    private final EmailService emailService;

    private final EmailUtils emailUtils;

    FriendRequestServiceImpl(
            final FriendRequestRepository friendRequestRepository,
            final MessageSource messageSource,
            final EmailService emailService,
            final EmailUtils emailUtils) {
        this.friendRequestRepository = friendRequestRepository;
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
}