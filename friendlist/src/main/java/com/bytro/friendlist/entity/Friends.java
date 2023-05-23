package com.bytro.friendlist.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friends extends Auditable {
    @EmbeddedId private FriendsId id;
    boolean isBlocked = false;
    private int blockedBy;
}
