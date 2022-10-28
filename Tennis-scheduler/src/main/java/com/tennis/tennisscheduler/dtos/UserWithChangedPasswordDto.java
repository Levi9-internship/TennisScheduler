package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.models.enumes.UserType;

public class UserWithChangedPasswordDto {

    public UserTokenStateDto userTokenStateDto;
    public String message;

    public UserWithChangedPasswordDto(UserTokenStateDto userTokenStateDto, String message) {
        this.userTokenStateDto = userTokenStateDto;
        this.message = message;
    }
}
