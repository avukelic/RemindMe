package com.avukelic.remindme.view.reminder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.avukelic.remindme.R;
import com.avukelic.remindme.data.model.Reminder;
import com.avukelic.remindme.util.DateUtil;
import com.avukelic.remindme.util.GlideUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private List<Reminder> reminders = new ArrayList<>();
    private OnReminderClickListener listener;

    public void setReminders(List<Reminder> newReminders) {
        reminders.clear();
        reminders.addAll(newReminders);
        Collections.reverse(reminders);
        notifyDataSetChanged();
    }

    public void addNewReminder(Reminder reminder) {
        reminders.add(reminder);
        notifyItemInserted(reminders.size());
    }

    public void setListener(OnReminderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        holder.setData(reminders.get(position));
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public Reminder getReminder(int position) {
        return reminders.get(position);
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder {

        //region ButterKnife
        @BindView(R.id.taskTitle)
        AppCompatTextView task;
        @BindView(R.id.deadline)
        AppCompatTextView deadline;
        @BindView(R.id.priority_box)
        ImageView priorityBox;
        //endregion

        @OnClick
        void onItemClick() {
            listener.onReminderClick(getAdapterPosition());
        }

        ReminderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Reminder reminder) {
            task.setText(reminder.getTaskTitle());
            deadline.setText(DateUtil.parseDateFromLongToString(reminder.getDeadLine()));
            switch (reminder.getPriority()) {
                case LOW:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityLow);
                    break;
                case MEDIUM:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityMedium);
                    break;
                case HIGH:
                    GlideUtil.loadColor(itemView.getContext(), priorityBox, R.color.colorPriorityHigh);
                    break;
            }
        }
    }

    public interface OnReminderClickListener {
        void onReminderClick(int position);
    }
}