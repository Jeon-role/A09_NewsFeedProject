
package com.example.newsfeedproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {
    private String username;
    private String password;
    private String newPassword;
}
