package com.tennis.tennisscheduler.dtos;

import java.util.Date;

public class TimeslotNewDto {
    public Date dateStart;
    public Date dateEnd;
    public int duration;
    public long personId;
    public long courtId;

    public TimeslotNewDto(Date dateStart, Date dateEnd, int duration, long personId, long courtId) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.personId = personId;
        this.courtId = courtId;
    }
}
