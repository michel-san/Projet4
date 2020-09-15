package p4.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import p4.R;
import p4.di.DI;
import p4.model.Meeting;
import p4.model.Room;
import p4.service.DummyMeetingApiService;
import p4.service.DummyRoomGenerator;
import p4.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.subjectLyt)
    TextInputLayout subjectInput;
    @BindView(R.id.nameRoom)
    TextView nameRoom;
    @BindView(R.id.add_mails_button)
    Button ADD;
    @BindView(R.id.create)
    Button save;
    @BindView(R.id.participant_edit_txt)
    EditText mMailEditText;

    private Meeting mNewMeeting;
    private MeetingApiService mApiService;
    private TextView mDateTxt;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView mHoursTxt;
    Spinner roomSpinner;
    Room[] mRoomList;
    ArrayAdapter<String> AdapterRoomSpinner;

    private ParticipantAdapter participantAdapter;
    private TextInputLayout participantInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNewMeeting = new Meeting();
        mNewMeeting.setTime(new Date().getTime());
        mNewMeeting.setParticipant(new ArrayList<>());
        mApiService = DI.getMeetingApiService();

        mApiService = new DummyMeetingApiService();

        initViewActivity();
    }

    private void initViewActivity() {

        //Assign variable
        mDateTxt = findViewById(R.id.date_txt);
//        String dateFormatted = (String) /change because redundant
        String dateFormatted = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date(mNewMeeting.getTime()));
        mDateTxt.setText(dateFormatted);
        mNewMeeting.setDate(dateFormatted);//call  meeting create by date
        mDateTxt.setOnClickListener(v -> {

            //Initialize calendar
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(mNewMeeting.getTime()));

            //Initialize year , month , day
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            //Initialize date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddMeetingActivity.this,
                    android.R.style.ThemeOverlay_Material_Light,
                    mDateSetListener,
                    year, month, day);

            //Show dialog
            datePickerDialog.show();
        });
        mDateSetListener = (view, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(mNewMeeting.getTime()));
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            mNewMeeting.setTime(cal.getTimeInMillis());
            String dateFormatted2 = (String)
                    DateFormat.format("dd/MM/yyyy", cal);
            Log.d("TAG", "onDateSet: DD/MM/YYYY" + dateFormatted2);
            mDateTxt.setText(dateFormatted2);
        };

        //Assign variable
//        mHoursTxt = (TextView) findViewById(R.id.hours_txt); /change because redundant
        mHoursTxt = findViewById(R.id.hours_txt);

//        String hourFormatted = (String) /change because redundant
        String hourFormatted = new SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(new Date(mNewMeeting.getTime()));
        mHoursTxt.setText(hourFormatted);

//        mHoursTxt.setOnClickListener(new View.OnClickListener() {  /change because redundant
        mHoursTxt.setOnClickListener(v -> {

            //Initialize calendar
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(mNewMeeting.getTime()));
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            //Initialize time picker dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddMeetingActivity.this,
//                    new TimePickerDialog.OnTimeSetListener() { /change because redundant
                    (view, hour1, minute1) -> {
                        cal.set(Calendar.HOUR_OF_DAY, hour1);
                        cal.set(Calendar.MINUTE, minute1);
                        mNewMeeting.setTime(cal.getTimeInMillis());

                        //Set selected time on text view
                        mHoursTxt.setText(DateFormat.format("HH:mm", cal));
                    }, hour, minute, true
            );
            //Show dialog
            timePickerDialog.show();
        });

        roomSpinner = findViewById(R.id.room);
        mRoomList = DummyRoomGenerator.getRooms();
        ArrayList<String> roomsName = new ArrayList<>();
        for (Room room : mRoomList) {
            roomsName.add(room.getName());
        }
        AdapterRoomSpinner = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, roomsName);
        AdapterRoomSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(AdapterRoomSpinner);
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                String nameRoom = adapterView.getItemAtPosition(position).toString();
                Room room;
                for (int i = 0; i < DummyRoomGenerator.getRooms().length; i++) {
                    if (nameRoom.equals(DummyRoomGenerator.getRooms()[i].getName())) {
                        room = DummyRoomGenerator.getRooms()[i];
                        mNewMeeting.setRoom(room);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        participantInput = findViewById(R.id.participantLyt);
        ADD = findViewById(R.id.add_mails_button);
        ADD.setOnClickListener(v -> {
                    String mail = participantInput.getEditText().getText().toString();
                    if (participantInput != null && validateEmail(mail)) {
                        mNewMeeting.getParticipants().add(mail);
                        participantAdapter.notifyDataSetChanged();
                        participantInput.getEditText().setText(null);
                    }
                }
        );

        // We recover our RecyclerView via its id
        RecyclerView recyclerView = findViewById(R.id.RecyclerView_list_mails);
        //We want a RecyclerView that uses a LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //We give our adapter to our RecyclerView
        participantAdapter = new ParticipantAdapter(mNewMeeting.getParticipants());
        recyclerView.setAdapter(participantAdapter);
        // We separate each line of our list by a line
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        subjectInput = findViewById(R.id.subjectLyt);
        save = findViewById(R.id.create);
        save.setOnClickListener(v -> {
            mNewMeeting.setSubject(subjectInput.getEditText().getText().toString());
            if (isCorrectlyFiled(mNewMeeting)) {
                mApiService.createMeeting(mNewMeeting);
                finish();
            } else {
                Toast.makeText(this, "Please complete ALL FIELDS",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            participantInput.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            participantInput.setError("Please ENTER a VALID email address");
            return false;
        } else {
            participantInput.setError(null);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isCorrectlyFiled(Meeting meeting) {
        if (meeting.getSubject() == null || meeting.getSubject().isEmpty()) {
            subjectInput.setError("Field can't be empty");
            return false;
        } else if (meeting.getSubject().length() > 20) {
            subjectInput.setError("This field must contain less than 20 characters");
            return false;

           /////not relevant because the elements are preselected/////
//        } else if (meeting.getTime() <= 0) {
//            Toast.makeText(this, "Please select a Hour and Date",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (meeting.getRoom() == null || meeting.getRoom().getName() == null) {
//            nameRoom.setError("Please select a Room");
//            return false;

        } else if (meeting.getParticipants() == null || meeting.getParticipants().size() < 2) {
            Toast.makeText(this, "minimum 2 participants are required",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Used to navigate to this activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
