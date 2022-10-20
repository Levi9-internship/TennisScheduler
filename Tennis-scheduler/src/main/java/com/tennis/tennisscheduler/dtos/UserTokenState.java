package com.tennis.tennisscheduler.dtos;

public class UserTokenState {
    public String accessToken;
    public String role;
    public Boolean enabled;
    public long id;

    public UserTokenState(String accessToken, String role, Boolean enabled, long id) {
        this.accessToken = accessToken;
        this.role = role;
        this.enabled = enabled;
        this.id = id;
    }
}
