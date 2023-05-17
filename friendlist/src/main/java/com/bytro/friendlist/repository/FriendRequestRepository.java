package com.bytro.friendlist.repository;

import com.bytro.friendlist.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {}
