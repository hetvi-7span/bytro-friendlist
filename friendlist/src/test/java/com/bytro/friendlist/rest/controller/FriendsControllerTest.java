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

    @Test
    void unfriend() throws Exception {
        final Integer userId = 1;
        final Integer friendId = 2;
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
}
