package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.models.enumes.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserWithChangedPasswordDto {

    private UserTokenStateDto userTokenStateDto;
    private String message;
}
