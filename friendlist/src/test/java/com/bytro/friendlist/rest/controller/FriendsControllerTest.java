package com.bytro.friendlist.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bytro.friendlist.handler.FriendsHandler;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FriendsController.class)
class FriendsControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean FriendsHandler friendsHandler;
    private static final Integer userId = 1;
    private static final Integer friendId = 2;

    @Test
    void unfriend() throws Exception {

        when(friendsHandler.unfriend(userId, friendId))
                .thenReturn(new BaseResponse<>(0, "user unfriend successfully."));
        final var requestBuilder =
                post("/unfriend/{userId}/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
            {
              "resultCode": 0,
              "resultMessage": "user unfriend successfully."
            }
            """))
                .andReturn();
    }

    @Test
    void blockFriend() throws Exception {
        when(friendsHandler.block(userId, friendId))
                .thenReturn(new BaseResponse<>(0, "friend unblocked successfully."));
        final var requestBuilder =
                post("/block/{userId}/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
                {
                  "resultCode": 0,
                  "resultMessage": "friend unblocked successfully."
                }
                """))
                .andReturn();
    }

    @Test
    void unBlockFriend() throws Exception {
        when(friendsHandler.unblock(userId, friendId))
                .thenReturn(new BaseResponse<>(0, "friend unblocked successfully."));
        final var requestBuilder =
                post("/unblock/{userId}/{friendId}", userId, friendId)
                        .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
                {
                  "resultCode": 0,
                  "resultMessage": "friend unblocked successfully."
                }
                """))
                .andReturn();
    }
}
