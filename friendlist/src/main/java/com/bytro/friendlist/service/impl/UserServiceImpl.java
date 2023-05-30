package com.bytro.friendlist.service.impl;

import com.bytro.friendlist.exception.CustomException;
import com.bytro.friendlist.service.UserService;
import com.bytro.friendlist.shared.enums.ResultCode;
import com.bytro.friendlist.utils.UserUtils;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final MessageSource messageSource;

    public UserServiceImpl(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void checkUser(int userId) {
        if (!UserUtils.checkUsersExistence(userId)) {
            throw new CustomException(
                    ResultCode.NOT_FOUND.getValue(),
                    messageSource.getMessage(
                            "user.not.found", new String[] {}, Locale.getDefault()));
        }
    }
}
