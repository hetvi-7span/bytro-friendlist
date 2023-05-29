package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.entity.FriendRequest;
import com.bytro.friendlist.entity.Friends;
import com.bytro.friendlist.entity.FriendsId;
import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.repository.FriendRepository;
import com.bytro.friendlist.service.FriendsService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.utils.Constant;
import jakarta.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {
    private final FriendRepository friendRepository;
    private final MessageSource messageSource;

    FriendsServiceImpl(final FriendRepository friendRepository, final MessageSource messageSource) {
        this.friendRepository = friendRepository;
        this.messageSource = messageSource;
    }

    @Override
    public void acceptFriendRequest(FriendRequest friendRequest) {
        friendRepository.save(
                addFriend(friendRequest.getSenderId(), friendRequest.getReceiverId()));
        friendRepository.save(
                addFriend(friendRequest.getReceiverId(), friendRequest.getSenderId()));
    }

    private Friends addFriend(int userId, int friendId) {
        Friends friends = new Friends();
        friends.setId(new FriendsId(userId, friendId));
        return friends;
    }

    @Override
    public void unfriend(Integer userId, Integer friendId) {
        Optional<Friends> friends = friendRepository.findByIdUserIdAndIdFriendId(userId, friendId);
        if (friends.isEmpty()) {
            throw new CustomException(
                    ResultCode.NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "no.data.found",
                            new String[] {Constant.FRIENDSHIP},
                            Locale.getDefault()));
        }
        friendRepository.deleteByIdUserIdAndIdFriendId(userId, friendId);
        friendRepository.deleteByIdUserIdAndIdFriendId(friendId, userId);
    }

    @Override
    public void block(Integer userId, Integer friendId) {
        Friends friendShip = checkIfFriendshipExists(userId, friendId);
        friendShip.setBlocked(true);

        Optional<Friends> inverseFriendshipOptional =
                friendRepository.findByIdUserIdAndIdFriendId(friendId, userId);
        if (inverseFriendshipOptional.isPresent()) {
            Friends inverseFriendship = inverseFriendshipOptional.get();
            inverseFriendship.setBlockedBy(userId);
            friendRepository.save(inverseFriendship);
        }
        friendRepository.save(friendShip);
    }

    @Override
    public void unblock(Integer userId, Integer friendId) {
        Friends friendShip = checkIfFriendshipExists(userId, friendId);
        friendShip.setBlocked(false);

        Optional<Friends> inverseFriendshipOptional =
                friendRepository.findByIdUserIdAndIdFriendId(friendId, userId);
        if (inverseFriendshipOptional.isPresent()) {
            Friends inverseFriendship = inverseFriendshipOptional.get();
            inverseFriendship.setBlockedBy(null);
            friendRepository.save(inverseFriendship);
        }
        friendRepository.save(friendShip);
    }

    @Override
    public void checkIfUserIsBlockedOrNot(int userId, int friendId) {
        Optional<Friends> friends = friendRepository.findByIdFriendIdAndIdUserId(userId, friendId);
        if (friends.isPresent() && friends.get().isBlocked()) {
            throw new CustomException(
                    ResultCode.BLOCKED_BY_USER.getValue(),
                    messageSource.getMessage(
                            "blocked.by.user", new String[] {}, Locale.getDefault()));
        }
    }

    private Friends checkIfFriendshipExists(Integer userId, Integer friendId) {
        Optional<Friends> friends = friendRepository.findByIdUserIdAndIdFriendId(userId, friendId);
        if (friends.isEmpty()) {
            throw new CustomException(
                    ResultCode.FRIENDSHIP_DOES_NOT_EXISTS.getValue(),
                    messageSource.getMessage(
                            "friendship.does.not.exists", new String[] {}, Locale.getDefault()));
        }
        return friends.get();
    }
}
