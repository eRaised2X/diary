package com.eraisedtox94.smartdiary.presenter.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by spraful on 03-08-2017.
 */

public class MyCalendarAdapter extends ArrayAdapter<Date>
{
    Context context;
    private HashSet<Date> eventDays;

    private LayoutInflater inflater;

    public MyCalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
    {
        super(context, R.layout.calendar_single_item, days);
        this.context = context;
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        Date today = new Date();

        if (view == null)
            view = inflater.inflate(R.layout.calendar_single_item, parent, false);

        /*
        // if this day has an event, specify event image
        view.setBackgroundResource(0);
        if (eventDays != null)
        {
            for (Date eventDate : eventDays)
            {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year)
                {
                    // mark this day for event
                    view.setBackgroundResource(R.drawable.icon_create_black);
                    break;
                }
            }
        }
        */

        // clear styling
        ((TextView)view).setTypeface(null, Typeface.NORMAL);
        ((TextView)view).setTextColor(Color.BLACK);

        if (month != today.getMonth() || year != today.getYear())
        {
            // if this day is outside current month, grey it out
            ((TextView)view).setTextColor(context.getResources().getColor(R.color.greyed_out));
        }
        else if (day == today.getDate())
        {
            ((TextView)view).setTypeface(null, Typeface.BOLD);
            ((TextView)view).setTextColor(context.getResources().getColor(R.color.today));
        }

        // set text
        ((TextView)view).setText(String.valueOf(date.getDate()));

        return view;
    }
}
