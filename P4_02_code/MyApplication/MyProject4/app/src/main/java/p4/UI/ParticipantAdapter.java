package p4.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import p4.R;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter
        .ListMailsViewHolder> {

    List<String> mListMails;

    public static class ListMailsViewHolder extends RecyclerView.ViewHolder {
        TextView mail;
        ImageButton deleteButton;

        public ListMailsViewHolder(View itemView) {
            super(itemView);
            mail = itemView.findViewById(R.id.fom_mail);
            deleteButton = itemView.findViewById(R.id.fom_delete_button);
        }
    }
    public ParticipantAdapter(List<String> mListMails ) {
        this.mListMails = mListMails;
    }

    @Override
    public ListMailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mail, parent, false);
//        ListMailsViewHolder listMailsViewHolder = new ListMailsViewHolder(view);
        // change because redundant
        return new ListMailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListMailsViewHolder holder, int position) {
        String participant = mListMails.get(position);
        holder.mail.setText(participant);
        holder.deleteButton.setOnClickListener(v -> {
            mListMails.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
    // another method but more data intensive
    //EventBus.getDefault().post((new DeleteMailsEvent(listMails)));
        });
    }
    @Override
    public int getItemCount() {
        return mListMails.size();
    }
}
