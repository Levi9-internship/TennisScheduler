package com.tennis.tennisscheduler.dto;

import com.tennis.tennisscheduler.model.Address;
import com.tennis.tennisscheduler.model.enumes.SurfaceType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TennisCourtDto {

    private long id;
    private String name;
    private SurfaceType surfaceType;
    private String description;
    private String image;
    private Address address;
    private WorkingTimeDto workingTimeDto;
}
