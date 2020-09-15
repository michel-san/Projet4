package p4.model;

import java.util.List;
import java.util.Objects;

/**
 * Model object representing a Meeting
 */
public class Meeting {
    /**
     * to put the time
     */
    private long time;
    /**
     * The ref to for the Rooms
     */
    private Room room;
    /**
     * To describe the meeting's subject
     */
    private String subject;
    /**
     * A list for the participants's emails
     */
    private List<String> participant;

    private String date;

    public Meeting(long time, Room room, String subject, List<String> participant, String date) {
        this.time = time;
        this.room = room;
        this.subject = subject;
        this.participant = participant;
        this.date = date;
    }

    public Meeting() {}

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(hashCode(), meeting.hashCode());
    }

    public boolean completeMeeting() {
        return participant != null && room != null && subject != null && date != null
                && time != 0;
    }
}

