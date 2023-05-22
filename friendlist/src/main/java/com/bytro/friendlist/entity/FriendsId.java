package com.bytro.friendlist.entity;

import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendsId implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer friendId;
}
