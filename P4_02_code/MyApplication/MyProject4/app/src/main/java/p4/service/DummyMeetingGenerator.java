package p4.service;

import java.util.ArrayList;
import java.util.List;
import p4.model.Meeting;
import p4.model.Room;

public abstract class DummyMeetingGenerator {

//    public static List<Room> mRooms = DummyRoomGenerator.getRooms();
//    private static List<String> LIST_MAIL = Arrays.asList("max@gmail.com", "laure@gmail.com");
//
//    public static List<Meeting> LIST_MEETINGS = Arrays.asList(
//            new Meeting(0, mRooms.get(0), "My", LIST_MAIL,"10/08/2020"),
//            new Meeting(100893930, mRooms.get(1), "Meeting", LIST_MAIL,"11/08/2020")
//    );
//    public static List<Meeting> generateMeetings(){
//
//        return new ArrayList<>(LIST_MEETINGS);

    public static List<Meeting> LIST_MEETINGS() {

        List<String> LIST_MAIL = new ArrayList<>();
        Room[] mRooms = DummyRoomGenerator.getRooms();
        MeetingApiService mApiService = new DummyMeetingApiService();
        List<Meeting> mMeetings = mApiService.getMeetings();
        if (mMeetings.size() == 0) {
            LIST_MAIL.add("max@gmail.com");
            LIST_MAIL.add("laure@gmail.com");
            Meeting meetingTest1 = new Meeting(0, mRooms[0], "My",
                    LIST_MAIL, "01/12/2020");
            Meeting meetingTest2 = new Meeting(10000000, mRooms[1], "Meeting",
                    LIST_MAIL, "12/12/2020");
            Meeting meetingTest3 = new Meeting(20000000, mRooms[2], "App",
                    LIST_MAIL, "25/12/2020");
            mApiService.getMeetings().add(meetingTest1);
            mApiService.getMeetings().add(meetingTest2);
            mApiService.getMeetings().add(meetingTest3);
        }
        mMeetings = mApiService.getMeetings();
        return mMeetings;
    }
}



