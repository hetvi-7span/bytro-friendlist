package com.bytro.friendlist.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bytro.friendlist.handler.FriendRequestHandler;
import com.bytro.friendlist.shared.record.request.AcceptRejectFriendRequest;
import com.bytro.friendlist.shared.record.response.BaseResponse;
import com.bytro.friendlist.shared.record.response.PendingFriendRequestResponse;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void getPendingFriendRequestList() throws Exception {
        final Integer userId = 1;
        final Integer page = 0;
        final Integer size = 1;
        List<PendingFriendRequestResponse> pendingFriendRequestResponseList = new ArrayList<>();
        pendingFriendRequestResponseList.add(
                new PendingFriendRequestResponse(8, null, 11, 31, "string"));
        pendingFriendRequestResponseList.add(
                new PendingFriendRequestResponse(9, null, 12, 31, "string"));
        pendingFriendRequestResponseList.add(
                new PendingFriendRequestResponse(10, null, 13, 31, "string"));
        when(friendRequestHandler.getFriendRequestList(userId, page, size))
                .thenReturn(
                        new BaseResponse<>(
                                0,
                                "pending request fetched successfully.",
                                3l,
                                1,
                                0,
                                pendingFriendRequestResponseList));
        final var requestBuilder =
                get("/friend-requests")
                        .param("userId", String.valueOf(userId))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
            {
              "resultCode": 0,
              "resultMessage": "pending request fetched successfully.",
              "totalItems": 3,
              "totalPages": 1,
              "currentPage": 0,
              "result": [
                {
                  "id": 8,
                  "senderId": 11,
                  "receiverId": 31,
                  "message": "string"
                },
                {
                  "id": 9,
                  "senderId": 12,
                  "receiverId": 31,
                  "message": "string"
                },
                {
                  "id": 10,
                  "senderId": 13,
                  "receiverId": 31,
                  "message": "string"
                }
              ]
            }
            """))
                .andReturn();
    }

    @Test
    void cancelFriendRequest() throws Exception {
        Integer requestId = 12;
        Integer senderId = 15;
        when(friendRequestHandler.cancel(requestId, senderId))
                .thenReturn(new BaseResponse<>(0, "Friend request cancelled successfully."));
        final var requestBuilder =
                post("/api/friends/cancel-request/{requestId}/{senderId}", requestId, senderId)
                        .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        """
                    {
                      "resultCode": 0,
                      "resultMessage": "Friend request cancelled successfully."
                    }
                    """,
                                        true))
                .andReturn();
    }
}
