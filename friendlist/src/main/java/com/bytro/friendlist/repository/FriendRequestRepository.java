package com.bytro.friendlist.repository;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    Optional<FriendRequest> findBySenderIdAndReceiverIdAndStatus(
            Integer senderId, Integer receiverId, FriendRequestStatus status);
}
