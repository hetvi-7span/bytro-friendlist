package com.bytro.friendlist.transformer;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.record.request.AcceptRejectFriendRequest;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import com.bytro.friendlist.shared.record.response.PendingFriendRequestResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestMapper {

    FriendRequest requestToEntity(SendFriendRequest sendFriendRequest);

    @Mapping(target = "friendRequestId", source = "id")
    FriendRequestResponse entityToResponse(FriendRequest requestSent);

    @Mapping(target = "status", ignore = true)
    @Mapping(source = "friendRequestId", target = "id")
    @Mapping(source = "recipientId", target = "receiverId")
    FriendRequest requestToEntity(AcceptRejectFriendRequest acceptRejectFriendRequest);

    PendingFriendRequestResponse pendingRequestResponseList(FriendRequest friendRequest);

    List<PendingFriendRequestResponse> pendingRequestResponse(List<FriendRequest> friendRequest);
}
