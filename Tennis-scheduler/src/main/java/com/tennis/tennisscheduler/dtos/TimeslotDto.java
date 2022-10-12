package com.tennis.tennisscheduler.dtos;

import java.util.Date;

public class TimeslotDto {

    public long id;
    public Date dateStart;
    public Date dateEnd;
    public int duration;

    public TimeslotDto(long id, Date dateStart, Date dateEnd, int duration) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
    }
}
