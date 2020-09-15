package p4.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.greenrobot.eventbus.EventBus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import p4.R;
import p4.events.DeleteMeetingEvent;
import p4.model.Meeting;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter
        <MyMeetingRecyclerViewAdapter.ViewHolder> {

    public List<Meeting> mMeetings;
    private MeetingFragment fragment;

    public MyMeetingRecyclerViewAdapter(MeetingFragment fragment) {

        this.fragment = fragment;
        this.mMeetings = fragment.mMeetings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Meeting meeting = fragment.mMeetings.get(position);
        String date;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        date = format.format(new Date(meeting.getTime()));
        holder.mMeetingName.setText(String.format("%s - %s - %s", meeting.getRoom().getName()
                , meeting.getSubject(), date));
        holder.mMails.setText(meeting.getParticipants().toString());
        holder.mMeetingAvatar.setImageResource(meeting.getRoom().getColor());

        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault()
                .post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return fragment.mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mMeetingAvatar;
        @BindView(R.id.item_list_name)
        public TextView mMeetingName;
        @BindView(R.id.item_list_mails)
        public TextView mMails;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view); }
    }
}
