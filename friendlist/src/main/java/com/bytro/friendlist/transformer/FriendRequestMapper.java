package com.bytro.friendlist.transformer;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendRequestMapper {
    @Mapping(
            target = "status",
            expression = "java(com.bytro.friendlist.shared.enums.FriendRequestStatus.SENT)")
    FriendRequest RequestToEntity(SendFriendRequest sendFriendRequest);
}
