package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.models.enumes.UserType;

public class UserTokenStateDto {
    public String accessToken;
    public UserType role;

    public UserTokenStateDto(String accessToken, UserType role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}
