package com.bytro.friendlist.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bytro.friendlist.handler.FriendRequestHandler;
import com.bytro.friendlist.shared.record.request.AcceptRejectFriendRequest;
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
@WebMvcTest(FriendRequestController.class)
class FriendRequestControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean FriendRequestHandler friendRequestHandler;

    @Test
    void acceptRejectFriendRequest() throws Exception {
        when(friendRequestHandler.acceptRejectFriendRequest(any(AcceptRejectFriendRequest.class)))
                .thenReturn(new BaseResponse<>(0, "Friend request accepted successfully."));
        final var requestBuilder =
                post("/accept-reject-friend-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                {
                      "recipientId": 2,
                      "friendRequestId": 6,
                      "isAccepted": true
                    }
                """);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
                        {
                          "resultCode": 0,
                          "resultMessage": "Friend request accepted successfully."
                        }
                        """,
                                        true))
                .andReturn();
    }
}
