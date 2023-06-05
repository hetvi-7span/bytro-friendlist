package com.bytro.friendlist.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bytro.friendlist.handler.FriendRequestHandler;
import com.bytro.friendlist.shared.record.request.SendFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.FriendRequestResponse;
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
    @MockBean private FriendRequestHandler friendRequestHandler;

    @Test
    void send() throws Exception {
        when(friendRequestHandler.send(any(SendFriendRequest.class)))
                .thenReturn(
                        new BaseResponse<>(
                                0,
                                "Friend request sent successfully.",
                                new FriendRequestResponse(2, "SENT")));
        final var requestBuilder =
                post("/send-friend-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                  {
                  "senderId": 11,
                  "receiverId": 21,
                  "message": "hello"
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
                                          "resultMessage": "Friend request sent successfully.",
                                          "result": {
                                            "friendRequestId": 2,
                                            "status": "SENT"
                                          }
                                        }
                                        """))
                .andReturn();
    }
}
