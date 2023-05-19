package com.bytro.friendlist.repository;

import com.bytro.friendlist.entity.Friends;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Integer> {

    void deleteByIdUserIdAndIdFriendId(Integer userId, Integer friendId);

    Optional<Friends> findByIdUserIdAndIdFriendId(Integer userId, Integer friendId);
}
