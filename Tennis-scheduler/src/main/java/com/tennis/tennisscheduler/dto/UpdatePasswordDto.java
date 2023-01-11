package com.tennis.tennisscheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {

    private String oldPassword;
    private String newPassword;
}
