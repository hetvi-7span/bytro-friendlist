package com.bytro.friendlist.handler;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import com.bytro.friendlist.transformer.FriendRequestMapper;
import com.bytro.friendlist.utils.UserUtils;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestHandler {

    private final FriendRequestService friendRequestService;
    private final FriendRequestMapper friendRequestMapper;
    private final MessageSource messageSource;

    FriendRequestHandler(
            final FriendRequestService friendRequestService,
            final FriendRequestMapper friendRequestMapper,
            final MessageSource messageSource) {
        this.friendRequestService = friendRequestService;
        this.friendRequestMapper = friendRequestMapper;
        this.messageSource = messageSource;
    }

    public BaseResponse<FriendRequestResponse> send(SendFriendRequest sendFriendRequest) {
        validateFriendRequest(sendFriendRequest);

        FriendRequest friendRequest = friendRequestMapper.requestToEntity(sendFriendRequest);
        FriendRequest requestSent = friendRequestService.send(friendRequest);
        FriendRequestResponse friendRequestResponse =
                friendRequestMapper.entityToResponse(requestSent);

        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage(
                        "friend.request.sent.successfully", new String[] {}, Locale.US),
                friendRequestResponse);
    }

    private void validateFriendRequest(SendFriendRequest sendFriendRequest) {
        if (sendFriendRequest.senderId() == sendFriendRequest.receiverId()) {
            throw new CustomException(
                    ResultCode.SENDER_RECEIVER_SAME.getValue(),
                    messageSource.getMessage(
                            "send.receiver.should.not.be.same", new String[] {}, Locale.US));
        }
        checkUser(sendFriendRequest.senderId());
        checkUser(sendFriendRequest.receiverId());
    }

    private void checkUser(int userId) {
        if (!UserUtils.checkUsersExistence(userId)) {
            throw new CustomException(
                    ResultCode.USER_NOT_FOUND.getValue(),
                    messageSource.getMessage("user.not.found", new String[] {}, Locale.US));
        }
    }
}