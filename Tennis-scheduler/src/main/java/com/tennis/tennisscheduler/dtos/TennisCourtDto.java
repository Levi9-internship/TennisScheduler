package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.models.Address;
import com.tennis.tennisscheduler.models.enumes.SurfaceType;

public class TennisCourtDto {

    public long id;
    public String name;
    public SurfaceType surfaceType;
    public String description;
    public String image;
    public Address address;

}
