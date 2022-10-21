package com.tennis.tennisscheduler.models;

public class TimeslotMessage {
    public static final String SAME_DATE_MESSAGE = "Selected date must be at same day, and start has to be before end!";
    public static final String FUTURE_DATE_MESSAGE = "Selected dates must be in future!";
    public static final String DURATION_MESSAGE = "Duration must be between 30 and 120 minutes!";
    public static final String WORKING_TIME_MESSAGE = "Working time isn't valid!";
    public static final String ALREADY_RESERVED_MESSAGE = "You can only reserve one timeslot for current day.";
    public static final String OVERLAPPING_MESSAGE = "Selected court is not available for selected time.";
}
