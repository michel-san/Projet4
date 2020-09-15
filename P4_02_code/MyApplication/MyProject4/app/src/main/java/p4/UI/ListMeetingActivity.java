package p4.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import p4.R;
import p4.model.Room;
import p4.service.DummyMeetingApiService;
import p4.service.DummyRoomGenerator;
import p4.service.MeetingApiService;

public class ListMeetingActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    // For the filters
    private String DATE;
    private Room mRoom;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Room[] mRoomList;
    ArrayAdapter<String> AdapterRoomSpinner;
    private MeetingApiService mApiService;
    private  MeetingFragment mMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mDateSetListener = generateDatePickerDialog();
        mApiService = new DummyMeetingApiService();
        configureMainFragment();
    }

    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.no_filter:
                Toast.makeText(this, "All Meetings", Toast.LENGTH_LONG).show();
                mMeetingFragment.mMeetings = mApiService.getMeetings();
                mMeetingFragment.dataChanged();
                return true;

            case R.id.filter_date:
                mDateSetListener = generateDatePickerDialog();
                configureDialogCalendar();
                return true;

            case R.id.filter_room:
                configureSpinnerRoom();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Display MeetingFragment
     */
    private void configureMainFragment() {
        mMeetingFragment = (MeetingFragment) getSupportFragmentManager().
                findFragmentById(R.id.list_meetings);
        if (mMeetingFragment == null && findViewById(R.id.activity_details_container) == null) {
            mMeetingFragment = MeetingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.activity_main_container, mMeetingFragment).commit();
        } else if (mMeetingFragment == null && findViewById(R.id.activity_details_container) != null) {
            mMeetingFragment = MeetingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.activity_main_container, mMeetingFragment).commit();
        }
    }

    /**
     * Display a calendar
     */
    private void configureDialogCalendar() {
        //Initialize calendar
        Calendar cal = Calendar.getInstance();

        //Initialize year , month , day
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Initialize date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                ListMeetingActivity.this,
                this,
                mDateSetListener,
                year, month, day);
datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());//test
        //Show dialog
        datePickerDialog.show();

    }

    /**
     * Give an DatePickerDialog for the filter Date
     *
     * @return DatePickerDialog.OnDateSetListener
     */
    private DatePickerDialog.OnDateSetListener generateDatePickerDialog() {
//  DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                // Change because redundant
        return (view, year, month, day) -> {
            String dayString = String.valueOf(day);
            String monthString = String.valueOf(month + 1);

            if (day < 10) {
                dayString = "0" + day;
            }
            if (month + 1 < 10) {
                monthString = "0" + (month + 1);
            }
            DATE = dayString + "/" + monthString + "/" + year;
            mMeetingFragment.mMeetings = mApiService.filterDate(DATE);
            mMeetingFragment.dataChanged();
        };
    }
    /**
     * Configure the Spinner to display an AlertBuilder of Rooms
     */
    private void configureSpinnerRoom() {
        AlertDialog.Builder mPopUp = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.spinner_filter_room, null);
        Spinner spinner = v.findViewById(R.id.spinner_filterByRoom);
        mRoomList = DummyRoomGenerator.getRooms();
        ArrayList<String> roomsName = new ArrayList<>();
        for (Room room : mRoomList) {
            roomsName.add(room.getName());
        }
        AdapterRoomSpinner = new ArrayAdapter<>(getApplication(),
                android.R.layout.simple_spinner_item, roomsName);
        spinner.setAdapter(AdapterRoomSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String nameRoom = adapterView.getItemAtPosition(position).toString();

                for (int i = 0; i < DummyRoomGenerator.getRooms().length;
                     i++) {
                    if (nameRoom.equals(DummyRoomGenerator.getRooms()[i].getName())) {

                        mRoom = DummyRoomGenerator.getRooms()[i];
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mPopUp.setTitle("Select a room").setView(v)
                .setPositiveButton("Filter",
                        (dialog, which) -> {
                            mMeetingFragment.mMeetings = mApiService.
                                    filterRooms(mRoom);
                            mMeetingFragment.dataChanged();
                        })
                .setNegativeButton("Cancel",
                        (dialog, which) -> {
                        });
        mPopUp.create().show();
    }
}