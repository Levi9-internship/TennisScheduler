package com.tennis.tennisscheduler.dtos;

import com.tennis.tennisscheduler.validators.annotations.*;

import java.util.Date;


@SameDateValidate
@FutureDateValidate
@DurationValidate
@WorkingDayValidate
@IsReservedValidate
@OverlappingTimeslotsValidate
public class TimeslotDto {
    public long id;
    public Date dateStart;
    public Date dateEnd;
    public long personId;
    public long courtId;
}
