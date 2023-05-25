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
    Optional<FriendRequest> findBySenderIdAndReceiverIdOrderByIdDesc(
            Integer senderId, Integer receiverId);

    Page<FriendRequest> findByReceiverIdAndStatus(
            Integer receiverId, FriendRequestStatus status, Pageable pageable);

    Optional<FriendRequest> findByReceiverIdAndSenderIdOrderByIdDesc(int receiverId, int senderId);

    Optional<FriendRequest> findByIdAndStatus(Integer id, FriendRequestStatus friendRequestStatus);
}
