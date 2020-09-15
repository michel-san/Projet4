package p4.events;

import p4.model.Meeting;

public class DeleteMeetingEvent {
    /**
     * Meeting to delete
     */
    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }

}
