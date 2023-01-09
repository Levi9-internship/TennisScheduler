package com.tennis.tennisscheduler.dto;

import com.tennis.tennisscheduler.validator.annotations.*;
import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotDto {
    private long id = 0L;
    private Date dateStart;
    private Date dateEnd;
    private long personId;
    private long courtId;
}
