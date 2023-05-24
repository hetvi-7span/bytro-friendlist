package com.bytro.friendlist.handler;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.service.FriendRequestService;
import com.bytro.friendlist.service.UserService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.shared.record.request.AcceptRejectFriendRequest;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import com.bytro.friendlist.shared.record.response.PendingFriendRequestResponse;
import com.bytro.friendlist.transformer.FriendRequestMapper;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestHandler {

    private final FriendRequestService friendRequestService;
    private final FriendRequestMapper friendRequestMapper;
    private final MessageSource messageSource;

    private final UserService userService;

    FriendRequestHandler(
            final FriendRequestService friendRequestService,
            final FriendRequestMapper friendRequestMapper,
            final MessageSource messageSource,
            final UserService userService) {
        this.friendRequestService = friendRequestService;
        this.friendRequestMapper = friendRequestMapper;
        this.messageSource = messageSource;
        this.userService = userService;
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
                        "friend.request.sent.successfully", new String[] {}, Locale.getDefault()),
                friendRequestResponse);
    }

    private void validateFriendRequest(SendFriendRequest sendFriendRequest) {
        if (sendFriendRequest.senderId() == sendFriendRequest.receiverId()) {
            throw new CustomException(
                    ResultCode.SENDER_RECEIVER_SAME.getValue(),
                    messageSource.getMessage(
                            "send.receiver.should.not.be.same",
                            new String[] {},
                            Locale.getDefault()));
        }
        userService.checkUser(sendFriendRequest.senderId());
        userService.checkUser(sendFriendRequest.receiverId());
    }

    public BaseResponse<Void> acceptRejectFriendRequest(
            AcceptRejectFriendRequest acceptRejectFriendRequest) {
        userService.checkUser(acceptRejectFriendRequest.recipientId());
        FriendRequest friendRequest =
                friendRequestMapper.requestToEntity(acceptRejectFriendRequest);
        if (acceptRejectFriendRequest.isAccepted()) {
            friendRequestService.acceptFriendRequest(friendRequest);
            return new BaseResponse<>(
                    ResultCode.SUCCESS.getValue(),
                    messageSource.getMessage(
                            "friend.request.accept.successfully",
                            new String[] {},
                            Locale.getDefault()));
        }
        friendRequestService.rejectFriendRequest(friendRequest);
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage(
                        "friend.request.declined.successfully",
                        new String[] {},
                        Locale.getDefault()));
    }

    public BaseResponse<List<PendingFriendRequestResponse>> getFriendRequestList(
            Integer userId, Integer page, Integer size) {
        Page<FriendRequest> friendRequestPage =
                friendRequestService.getFriendRequestList(userId, page, size);
        List<PendingFriendRequestResponse> pendingResponsesList =
                friendRequestMapper.pendingRequestResponse(friendRequestPage.getContent());
        return new BaseResponse<>(
                ResultCode.SUCCESS.getValue(),
                messageSource.getMessage(
                        "pending.request.fetched.successfully",
                        new String[] {},
                        Locale.getDefault()),
                friendRequestPage.getTotalElements(),
                friendRequestPage.getTotalPages(),
                friendRequestPage.getNumber(),
                pendingResponsesList);
    }
}
