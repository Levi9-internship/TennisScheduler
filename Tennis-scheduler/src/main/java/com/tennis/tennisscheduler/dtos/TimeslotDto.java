package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.validators.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@SameDateValidate
@FutureDateValidate
@DurationValidate
@WorkingDayValidate
@IsReservedValidate
@OverlappingTimeslotsValidate
@Builder
@Getter
@Setter
public class TimeslotDto {
    public long id = 0L;
    public Date dateStart;
    public Date dateEnd;
    public long personId;
    public long courtId;
    public TimeslotDto () {}
    public TimeslotDto(long id, Date dateStart, Date dateEnd, long personId, long courtId) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.personId = personId;
        this.courtId = courtId;
    }
}
