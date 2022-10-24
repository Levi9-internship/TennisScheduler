package com.tennis.tennisscheduler.models.enumes;

public enum WorkingHours {
    WEEKEND(17, 22),
    WORKING_DAYS( 18, 23);

    public final int startHours;
    public final int endHours;

    WorkingHours(int from, int to) {
        this.startHours = from;
        this.endHours = to;
    }
}
