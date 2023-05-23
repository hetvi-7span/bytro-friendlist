package com.bytro.friendlist.repository;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.shared.enums.FriendRequestStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    Optional<FriendRequest> findBySenderIdAndReceiverIdAndStatus(
            Integer senderId, Integer receiverId, FriendRequestStatus status);

    Optional<FriendRequest> findByIdAndReceiverIdAndStatus(
            Integer id, int receiverId, FriendRequestStatus friendRequestStatus);

    Page<FriendRequest> findByReceiverIdAndStatus(
            Integer receiverId, FriendRequestStatus status, Pageable pageable);

    Optional<FriendRequest> findByIdAndSenderIdAndStatus(
            Integer id, Integer senderId, FriendRequestStatus friendRequestStatus);
}
