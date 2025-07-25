package com.aptech.common.event.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatedEvent {
    private String userId;
    private String email;
    private String userName;
}
