package com.tennis.tennisscheduler.models;

public class TimeslotMessage {
    public static final String sameDateMessage = "Selected date must be at same day, and start has to be before end!";
    public static final String futureDateMessage = "Selected dates must be in future!";
    public static final String durationMessage = "Duration must be between 30 and 120 minutes!";
    public static final String workingTimeMessage = "Working time isn't valid!";
    public static final String alreadyReservedMessage = "You can only reserve one timeslot for current day.";
    public static final String overlappingMessage = "Selected court is not available for selected time.";
}
