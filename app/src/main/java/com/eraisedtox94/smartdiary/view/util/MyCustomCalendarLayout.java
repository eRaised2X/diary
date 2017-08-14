package com.eraisedtox94.smartdiary.view.util;

import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.presenter.adapters.MyCalendarAdapter;

import android.widget.TextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.GridView;
import android.content.Context;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Calendar;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by spraful on 30-Jun-17.
 */

public class MyCustomCalendarLayout extends LinearLayout
{

    private static final int NoOfItemsInGrid = 42;
    private LinearLayout layoutCalendarDays;
    private ImageView ivPrev;
    private ImageView ivNext;
    private TextView txtDate;
    private GridView gridView;
    HashSet<Date> events = new HashSet<>();

    private Calendar todaysDate = Calendar.getInstance();

    public MyCustomCalendarLayout(Context context)
    {
        super(context);
    }

    public MyCustomCalendarLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public MyCustomCalendarLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);

        initialize();
        setListeners();
        updateCalendar(events);
    }

    private void initialize()
    {
        layoutCalendarDays = (LinearLayout)findViewById(R.id.ll_calender_days);
        ivPrev = (ImageView)findViewById(R.id.ivCalendarPrev);
        ivNext = (ImageView)findViewById(R.id.ivCalendarNext);
        txtDate = (TextView)findViewById(R.id.tvCalendarCurrentDate);
        gridView = (GridView)findViewById(R.id.calendar_grid);
    }



    private void setListeners()
    {
        ivNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                todaysDate.add(Calendar.MONTH, 1);
                updateCalendar(events);
            }
        });

        ivPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                todaysDate.add(Calendar.MONTH, -1);
                updateCalendar(events);
            }
        });

        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
            }
        });*/
    }


    public void updateCalendar(HashSet<Date> events)
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) todaysDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < NoOfItemsInGrid)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update gridView

        gridView.setAdapter(new MyCalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat("MMM YY");
        txtDate.setText(sdf.format(todaysDate.getTime()));

    }

    /*public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler
    {
        void onDayLongPress(Date date);
    }*/
}