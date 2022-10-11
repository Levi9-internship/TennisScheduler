package com.tennis.tennisscheduler.dtos;

import java.util.Date;

public class TimeslotNewDto {
    public Long id;
    public Date dateStart;
    public Date dateEnd;
    public int duration;
    public Long personId;
    public Long courtId;

    public TimeslotNewDto(Long id, Date dateStart, Date dateEnd, int duration, Long personId, Long courtId) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.personId = personId;
        this.courtId = courtId;
    }

    public TimeslotNewDto() {
    }
}
