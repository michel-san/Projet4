package p4.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;

import p4.R;
import p4.di.DI;
import p4.events.DeleteMeetingEvent;
import p4.model.Meeting;
import p4.service.DummyMeetingApiService;
import p4.service.DummyMeetingGenerator;
import p4.service.MeetingApiService;

public class MeetingFragment extends Fragment  {

    public List<Meeting> mMeetings;
    private RecyclerView mRecyclerView;
    private MeetingApiService mApiService;
    private MyMeetingRecyclerViewAdapter mMeetingsAdapter;

    public MeetingFragment() {
        // Required empty public constructor
    }
    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        mApiService = new DummyMeetingApiService();
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }

    /**
     * Init the List of meetings
     */
    private void initList() {
        DummyMeetingGenerator.LIST_MEETINGS();
        mMeetings = mApiService.getMeetings();
        mMeetingsAdapter = new MyMeetingRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mMeetingsAdapter);
        mMeetingsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        initList();
        super.onResume();
    }

    /**
     * Fired if the user clicks on a delete button
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }
    /**
     * Method to notify that the Data changed
     */
    public void dataChanged() {
        mMeetingsAdapter.notifyDataSetChanged();
    }
}

