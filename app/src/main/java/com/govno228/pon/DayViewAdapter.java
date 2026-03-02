package com.govno228.pon;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayViewAdapter extends ArrayAdapter {
    ArrayList<Date> dates;
    Context context;
    int resource;

    public DayViewAdapter(@NonNull Context context, int resource, ArrayList<Date> dates) {
        super(context, resource);
        this.context = context;
        this.dates = dates;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }

        Date date = getItem(position);

        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        TextView noteText = view.findViewById(R.id.noteText);

        title.setText(CalendarManager.getMonthAndDay(date));
        subtitle.setText(CalendarManager.getWeekDay(date));

        SharedPreferences prefs = context.getSharedPreferences("calendar_notes", Context.MODE_PRIVATE);
        String key = generateKey(date);
        String getNote = prefs.getString(key, "No notes");
        String note = "Note: " + getNote;

        noteText.setText(note);
        noteText.setVisibility(View.VISIBLE);

        return view;
    }

    private String generateKey(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        return year + "-" + month + "-" + day;
    }

    @Nullable
    @Override
    public Date getItem(int position) {
        return dates.get(position);
    }

    @Override
    public int getCount() {
        return dates.size();
    }
}
