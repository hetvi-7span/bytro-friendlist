package com.bytro.friendlist.transformer;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestMapper {

    FriendRequest requestToEntity(SendFriendRequest sendFriendRequest);

    @Mapping(target = "friendRequestId", source = "id")
    FriendRequestResponse entityToResponse(FriendRequest requestSent);
}
