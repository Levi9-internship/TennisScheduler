package com.tennis.tennisscheduler.dtos;

import java.util.Date;

public class TimeslotDto {
    public Long id;
    public Date dateStart;
    public Date dateEnd;
    public int duration;

    public TimeslotDto(Long id, Date dateStart, Date dateEnd, int duration) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
    }
}
