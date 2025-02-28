package com.example.mymovieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovieapp.R;
import com.example.mymovieapp.utils.DateSystem;

import java.util.List;

public class DatePickingAdapter extends RecyclerView.Adapter<DatePickingAdapter.ViewHolder> {
    private Context context;
    private DateSystem date = new DateSystem();
    private List<Info> Items = new java.util.ArrayList<>();
    private final String[] days = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private final String[] days_long = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
            "SATURDAY"};
    private boolean selected = false;
    private onClickItemListener listener;

    public DatePickingAdapter(Context context, onClickItemListener listener) {
        this.context = context;
        this.listener = listener;

        int today = date.getDay();
        int today_dayOfWeek = date.getDayOfWeekInt();

        Info info = new Info();
        info.date = String.valueOf(today);
        info.dayOfWeek = days[(today_dayOfWeek) % 7];
        Items.add(info);

        for (int i = 1; i < 7; i++) {
            Info inf = new Info();
            inf.date = String.valueOf(date.addDay(1));
            inf.dayOfWeek = days[(today_dayOfWeek + i) % 7];
            Items.add(inf);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.date_pick_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Info item = Items.get(position);
        holder.date.setText(item.date);
        holder.dayOfWeek.setText(item.dayOfWeek);
        holder.itemView.setOnClickListener(v -> {
            if (!selected) {
                v.setSelected(true);
                selected = true;
                String Date = days_long[(date.getDayOfWeekInt() + position) % 7] + "," + item.date;
                listener.onItemClick(Date);
            } else {
                if (v.isSelected()) {
                    v.setSelected(false);
                    selected = false;
                    listener.onItemClickRestored();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView dayOfWeek;

        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textViewDate);
            dayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }

    public class Info {
        private String date;
        private String dayOfWeek;
    }

    public interface onClickItemListener {
        void onItemClick(String Date);

        void onItemClickRestored();
    }
}
