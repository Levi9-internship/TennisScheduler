package com.tennis.tennisscheduler.dtos;

import java.util.Date;

public class TimeslotDto {

    public long id;
    public Date dateStart;
    public Date dateEnd;
    public int duration;
    public long personId;
    public long courtId;
}
