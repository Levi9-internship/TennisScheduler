package com.tennis.tennisscheduler.dto;

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
