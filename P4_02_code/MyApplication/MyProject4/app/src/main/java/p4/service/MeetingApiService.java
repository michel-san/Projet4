package p4.service;

import java.util.List;

import p4.model.Meeting;
import p4.model.Room;

/**
 * Meeting API client
 */
public interface MeetingApiService {
    /**
     * Get all the Meeting
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Give a list of meetings with the same date
     * @return {@link List}
     */
    List<Meeting> filterDate(String date);

    /**
     * Give a list of Meetings with the same room
     */
    List<Meeting> filterRooms(Room room);
}
