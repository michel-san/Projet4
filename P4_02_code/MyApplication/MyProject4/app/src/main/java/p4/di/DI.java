package p4.di;

import p4.service.DummyMeetingApiService;
import p4.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */

public class DI {
    private static MeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     */
    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}.
     * Useful for tests, so we ensure the context is clean.
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }

}
