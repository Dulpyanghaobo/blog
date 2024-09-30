package com.hab.blog.feature.v1.entities.User;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendId implements Serializable {

    private Long userId;
    private Long friendId;
}
