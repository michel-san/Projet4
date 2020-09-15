package p4.service;

import java.util.ArrayList;
import java.util.List;

import p4.model.Meeting;
import p4.model.Room;

public class DummyMeetingApiService implements MeetingApiService {

    public static List<Meeting> meetingList;

    public DummyMeetingApiService() {
        if (meetingList == null) {
            meetingList = new ArrayList<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetingList;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }

    @Override
    public List<Meeting> filterDate(String date) {

        List<Meeting> list = new ArrayList<>();

        for (int i = 0; i < DummyMeetingApiService.meetingList.size(); i++) {
            if ((DummyMeetingApiService.meetingList.get(i).getDate()).equals(date)) {
                list.add(DummyMeetingApiService.meetingList.get(i));
            }
        }
        return list;
    }

    @Override
    public List<Meeting> filterRooms(Room room) {

        List<Meeting> list = new ArrayList<>();

        for (int i = 0; i < DummyMeetingApiService.meetingList.size(); i++) {
            if (DummyMeetingApiService.meetingList.get(i).getRoom().getName().equals(room.getName())) {
                list.add(DummyMeetingApiService.meetingList.get(i));
            }
        }
        return list;
    }
}

