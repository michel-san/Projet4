package p4.meeting_list;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import p4.R;
import p4.UI.ListMeetingActivity;
import p4.utils.DeleteViewAction;
import p4.utils.DeleteViewActionRecyclerView_list_mails;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static p4.utils.RecyclerViewItemCountAssertion.withItemCount;

/**
 * INSTRUMENTED test, which will execute on an Android device.
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)

public class MeetingListTest {
    // This is fixed
    private static int ITEMS_COUNT = 3;
    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // We have the 2 Meetings add in the SetUp
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }
    /**
     * Delete meeting == -Meetings
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2,
                        new DeleteViewAction()));
        // Then : the number of element is 1
        onView(allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT - 1));
    }
    @Test
    public void myMeetingList_clickToolbarAction_shouldDisplayFilterByDate() {
        // Given : We check that we have 2 Meetings
        onView(allOf(withId(R.id.list_meetings), isDisplayed()));
        // When : We click on the toolbar and put a Date
        onView(withId(R.id.filter)).perform(click());
        onView(ViewMatchers.withText("FilterByDate")).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.
                setDate(2020, 12, 12));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : We have the meetings with this date
        onView(allOf(withId(R.id.list_meetings), isDisplayed())).check(withItemCount(1));
    }
    @Test
    public void myMeetingList_clickToolbarAction_shouldDisplayFilterByRoom() {
        // Given : We check that we have 2 Meetings
        onView(allOf(withId(R.id.list_meetings), isDisplayed()));
        // When : We click on the toolbar and choose a room
        onView(withId(R.id.filter)).perform(click());
        onView(withText("FilterRoom")).perform(click());
        // click on the spinner
        onView(withId(R.id.spinner_filterByRoom)).perform(click());
        // Then : selected room B in the list
        onData(anything()).inRoot(isPlatformPopup()).atPosition(1).perform(click());
        onView(allOf(withId(R.id.spinner_filterByRoom), isDisplayed()));
        onView(ViewMatchers.withText("FILTER")).perform(click());
        // Then : The list has 1 meeting
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(1));
    }
    @Test
    public void myMeetingList_clickAction_shouldDisplayAddActivity() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
    }
    @Test
    public void myMeetingList_clickAddMeetingButton_clickButtonMails_addMails() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // Given : We put a String for the mail
        onView(ViewMatchers.withId(R.id.participantLyt)).perform(click());
        onView(ViewMatchers.withId(R.id.participant_edit_txt))
                .perform(replaceText("AdresseMail@test.fr"));
        // When : We click on the AddMail Button
        onView(ViewMatchers.withId(R.id.add_mails_button))
                .perform(click());
        // Then : The mails list is not empty
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails))
                .check(withItemCount(1));
    }
    @Test
    public void myMeetingList_clickAddMeetingButton_clickDeleteMails_deleteMails() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // Given  : We put 2 Strings and Click on the AddMail Button
        onView(ViewMatchers.withId(R.id.participantLyt)).perform(click());
        onView(ViewMatchers.withId(R.id.participant_edit_txt))
                .perform(replaceText("AdresseMail@test.fr"));
        onView(ViewMatchers.withId(R.id.add_mails_button)).perform(click());
        onView(ViewMatchers.withId(R.id.participantLyt)).perform(click());
        onView(ViewMatchers.withId(R.id.participant_edit_txt))
                .perform(replaceText("AdresseMail2@test.fr"));
        onView(ViewMatchers.withId(R.id.add_mails_button)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).check(withItemCount(2));
        // When : We click on the delete Button for the mail
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, new DeleteViewActionRecyclerView_list_mails()));
        // Then : The mails list is not empty
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).check(withItemCount(1));
    }
    @Test
    public void myMeetingList_clickAddMeetingButton_clickHour_showDialogPicker() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // When : We click on the hour button  and choose 10
        onView(ViewMatchers.withId(R.id.hours_txt)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(10, 00));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : The text hour is good
        onView(withId(R.id.hours_txt)).check(matches(AllOf.allOf(withText("10:00"),
                isDisplayed())));
    }
    @Test
    public void myMeetingList_clickAddReunionButton_clickDate_showDialogPicker() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // When : We click on the date button and choose a date
        onView(ViewMatchers.withId(R.id.date_txt)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions
                .setDate(2020, 12, 01));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : The text date is good
        onView(withId(R.id.date_txt)).check(matches(AllOf.allOf(withText("01/12/2020"),
                isDisplayed())));
    }
    @Test
    public void myMeetingList_clickAddMeetingButton_clickSpinnerRoom_showSpinner() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // When : We click on the spinner Room and choose a room
        onView(ViewMatchers.withId(R.id.room)).check(matches(isClickable()));
        onView(ViewMatchers.withId(R.id.room)).perform(click());
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Room H"))).perform(click());
        // Then : The spinner has like text the good room
        onView(withId(R.id.room)).check(matches(withSpinnerText(containsString("Room H"))));
    }

    @Test
    public void myMeetingList_clickAddMeetingButton_createNewMeeting_clickButtonSAVE() {
        // When : we click on the fab button
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        // Then : Go to the Add Meeting Fragment
        onView(ViewMatchers.withId(R.id.add_meeting_fragment)).check(matches(isDisplayed()));
        // When : We complete and click on the SAVE button
        onView(ViewMatchers.withId(R.id.subject_edit_text))
                .perform(replaceText("Meeting Test"));
        onView(ViewMatchers.withId(R.id.date_txt)).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions
                .setDate(2020, 6, 30));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : The text date is good
        onView(withId(R.id.date_txt)).check(matches(AllOf.allOf(withText("30/06/2020"),
                isDisplayed())));
        onView(ViewMatchers.withId(R.id.hours_txt)).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(PickerActions.setTime(10, 00));
        onView(withId(android.R.id.button1)).perform(click());
        // Then : The text hour is good
        onView(withId(R.id.hours_txt)).check(matches(AllOf.allOf(withText("10:00"),
                isDisplayed())));
        onView(ViewMatchers.withId(R.id.room)).perform(click());
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Room J"))).perform(click());
        onView(withId(R.id.room)).check(matches(withSpinnerText(containsString("Room J"))));
        // Given  : We put 2 Strings and Click on the AddMail Button
        onView(ViewMatchers.withId(R.id.participantLyt)).perform(click());
        onView(ViewMatchers.withId(R.id.participant_edit_txt))
                .perform(replaceText("AdresseMail@test.com"));
        onView(ViewMatchers.withId(R.id.add_mails_button)).perform(click());
        onView(ViewMatchers.withId(R.id.participantLyt)).perform(click());
        onView(ViewMatchers.withId(R.id.participant_edit_txt))
                .perform(replaceText("AdresseMail2@test.com"));
        onView(ViewMatchers.withId(R.id.add_mails_button)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).perform(click());
        onView(ViewMatchers.withId(R.id.RecyclerView_list_mails)).check(withItemCount(2));
        pressBack();
        onView(ViewMatchers.withId(R.id.create)).perform(click());
        // Then : The meeting is added on the MeetingList
        onView(Matchers.allOf(withId(R.id.list_meetings), isDisplayed()))
                .check(withItemCount(ITEMS_COUNT ));
    }
}