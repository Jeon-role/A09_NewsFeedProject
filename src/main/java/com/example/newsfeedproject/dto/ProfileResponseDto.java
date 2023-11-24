// ProfileResponseDto.java

package com.example.newsfeedproject.dto;

import com.example.newsfeedproject.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto extends CommonResponseDto{
    private String username;
    private String email;
    private UserRoleEnum role;
}
