package p4.service;

import p4.R;
import p4.model.Room;


public abstract class DummyRoomGenerator {

    private static Room[] LIST_ROOMS = {

            new Room( "Room A", R.drawable.ic_meeting_room_1),
            new Room( "Room B", R.drawable.ic_meeting_room_2),
            new Room( "Room C", R.drawable.ic_meeting_room_3),
            new Room( "Room D", R.drawable.ic_meeting_room_4),
            new Room( "Room E", R.drawable.ic_meeting_room_5),
            new Room( "Room F", R.drawable.ic_meeting_room_6),
            new Room( "Room G", R.drawable.ic_meeting_room_7),
            new Room( "Room H", R.drawable.ic_meeting_room_8),
            new Room( "Room I", R.drawable.ic_meeting_room_9),
            new Room( "Room J", R.drawable.ic_meeting_room_10),
    };

    public static Room[] getRooms() {
        return LIST_ROOMS;
    }
}
