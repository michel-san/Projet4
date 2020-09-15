package p4;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import p4.di.DI;
import p4.model.Meeting;
import p4.model.Room;
import p4.service.DummyMeetingGenerator;
import p4.service.DummyRoomGenerator;
import p4.service.MeetingApiService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static p4.service.DummyMeetingApiService.meetingList;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;
    private Room roomForTest;
    private List<Meeting> meetings = new ArrayList<>();
    //    private boolean isComplete;
    // convert field to locale variable in method "completeMeetingWithSuccess"

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        meetingList.clear();
        roomForTest = DummyRoomGenerator.getRooms()[0];
        for (int i = 0; i < 4; i++) {
            Meeting m = new Meeting();
            m.setRoom(roomForTest);
            m.setDate("12/12/2020");
            meetings.add(m);
            meetingList.add(m);
        }
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> expectedMeetings = DummyMeetingGenerator.LIST_MEETINGS();
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * Unit test on delete Meetings == -Meetings
     */
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    /**
     * Unit test  filter Date
     */
    @Test
    public void filterDateWithSuccess() {
        String date = "01/01/2021";
        Meeting meeting = new Meeting();
        meeting.setDate(date);
        meeting.setRoom(roomForTest);
        service.getMeetings().add(meeting);
        List<Meeting> compare = service.filterDate(date);
        assertEquals(compare.size(), 1);
    }

    /**
     * Unit test  filter rooms
     */
    @Test
    public void filterRoomsWithSuccess() {
        Room roomAd = new Room("MeetingX", 0);
        Meeting meeting = new Meeting();
        meeting.setRoom(roomAd);
        service.getMeetings().add(meeting);
        assertEquals(service.getMeetings().size(), 5);
        List<Meeting> compare = service.filterRooms(roomAd);
        assertTrue(compare.contains(meeting));
        assertEquals(compare.size(), 1);
    }

    Meeting meeting = new Meeting();

    @Test
    public void completeMeetingWithSuccess() {
        meeting.setSubject("Test");
        meeting.setDate("12/12/2020");
        meeting.setTime(1);
        boolean isComplete = meeting.completeMeeting();
        assertFalse(isComplete);
        meeting.setRoom(DummyRoomGenerator.getRooms()[0]);
        List<String> mailsForTest = Collections.singletonList("mail@test.com");
        meeting.setParticipant(mailsForTest);
        isComplete = meeting.completeMeeting();
        assertTrue(isComplete);
    }
}