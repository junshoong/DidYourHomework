package net.harvey.didyourhomework;

//DB control
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int PREV = 1;
    private static final int THIS = 2;
    private static final int NEXT = 3;
    private static final boolean LIST_VIEW = false;
    private boolean isListView = LIST_VIEW;
    private WeekView mWeekView;
    private Calendar week = Calendar.getInstance();
    private ArrayList<WeekViewEvent> getEvents = new ArrayList<WeekViewEvent>();

    private ListView listView;
    private CustomAdapter adapter;


    WeekView.MonthChangeListener mMonthChangeListener = new WeekView.MonthChangeListener() {

        @Override
        public ArrayList<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
            // Populate the week view with some events.
            ArrayList<WeekViewEvent> events = getMonthEvent(newYear, newMonth);
//
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 13);
//            startTime.set(Calendar.MINUTE, 00);
//            startTime.set(Calendar.MONTH, newMonth - 1);
//            startTime.set(Calendar.YEAR, newYear);
//            Calendar endTime = (Calendar) startTime.clone();
//            endTime.add(Calendar.HOUR, 1);
//            endTime.set(Calendar.MONTH, newMonth - 1);
//            WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
//            event.setColor(getResources().getColor(R.color.event_color_01));
//            events.add(event);
            return events;
        }
    };
    private boolean isInitWeekView;
    private boolean isInitListView;



    private ArrayList<WeekViewEvent> getMonthEvent(int newYear, int newMonth) {
//        getEvents.clear();
//        if (newYear == 2015 && newMonth == 7) {
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 13);
//            startTime.set(Calendar.MINUTE, 00);
//            startTime.set(Calendar.DAY_OF_MONTH, 6);
//            startTime.set(Calendar.MONTH, newMonth - 1);
//            startTime.set(Calendar.YEAR, newYear);
//            Calendar endTime = (Calendar) startTime.clone();
//            endTime.add(Calendar.HOUR, 1);
//            endTime.set(Calendar.MONTH, newMonth - 1);
//            WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
//            getEvents.add(event);
//        }
//        if (newYear == 2015 && newMonth == 8) {
//            Calendar startTime = Calendar.getInstance();
//            startTime.set(Calendar.HOUR_OF_DAY, 13);
//            startTime.set(Calendar.MINUTE, 00);
//            startTime.set(Calendar.DAY_OF_MONTH, 5);
//            startTime.set(Calendar.MONTH, newMonth - 1);
//            startTime.set(Calendar.YEAR, newYear);
//            Calendar endTime = (Calendar) startTime.clone();
//            endTime.add(Calendar.HOUR, 2);
//            endTime.set(Calendar.MONTH, newMonth - 1);
//            WeekViewEvent event = new WeekViewEvent(2, "이벤트2", startTime, endTime);
//            getEvents.add(event);
//        }
        return getEvents;
    }

    private WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
        @Override
        public void onEventClick(WeekViewEvent event, RectF eventRect) {
            Toast.makeText(MainActivity.this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        }
    };
    private WeekView.EventLongPressListener mEventLongPressListener = new WeekView.EventLongPressListener() {
        @Override
        public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
            Toast.makeText(MainActivity.this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DBManager dbManager = new DBManager(getApplicationContext(), "Event.db", null, 1);

        setContentView(R.layout.activity_main);

        mWeekView = (WeekView) findViewById(R.id.weekView);
        listView = (ListView) findViewById(R.id.list_view);

        initView();
    }

    private void initView() {

        toggleView();

        if(!isListView) {
            if(isInitWeekView){
                return;
            }
            isInitWeekView = true;
            // Get a reference for the week view in the layout.

            mWeekView.setStartTime(9);
            mWeekView.setEndTime(18);

            // Set an action when any event is clicked.
            mWeekView.setOnEventClickListener(mEventClickListener);
            // The week view has infinite scrolling horizontally. We have to provide the events of a
            // month every time the month changes on the week view.
            mWeekView.setMonthChangeListener(mMonthChangeListener);
            // Set long press listener for events.
            mWeekView.setEventLongPressListener(mEventLongPressListener);
            changeWeek(0);
//            setupDateTimeInterpreter(false);

        } else {
            if(isInitListView){
                return;
            }
            isInitListView = true;

            adapter = new CustomAdapter(this, R.layout.item_worklist);

            listView.setAdapter(adapter);

            adapter.addAll(getMonthEvent(2015, 7));
            adapter.addAll(getMonthEvent(2015, 8));
        }
    }

    private void toggleView() {
        if(!isListView){
            mWeekView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            mWeekView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//    private void setupDateTimeInterpreter(final boolean shortDate) {
//        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
//            @Override
//            public String interpretDate(Calendar date) {
//                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
//                String weekday = weekdayNameFormat.format(date.getTime());
//                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());
//
//                // All android api level do not have a standard way of getting the first letter of
//                // the week day name. Hence we get the first char programmatically.
//                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
//                if (shortDate)
//                    weekday = String.valueOf(weekday.charAt(0));
//                return weekday.toUpperCase() + format.format(date.getTime());
//            }
//
//            @Override
//            public String interpretTime(int hour) {
//                return hour > 12 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : (hour == 12 ? "12 PM" : hour + " AM"));
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        setupDateTimeInterpreter(id == R.id.action_today);
        switch (id) {
            case R.id.action_today:
                changeWeek(THIS);
                return true;
            case R.id.action_next_week:
                changeWeek(NEXT);
                return true;
            case R.id.action_prev_week:
                changeWeek(PREV);
                return true;
            case R.id.change_view:
                isListView=!isListView;
                toggleMenu();
                initView();
                return true;
            case R.id.add_event:
                addEvent();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvent() {
        Intent intent = new Intent(this,AddEventActivity.class);
        startActivityForResult(intent, 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            switch (requestCode){
                case 99:

                    break;
            }
        }
    }

    private void toggleMenu() {
        if(!isListView){
            findViewById(R.id.action_prev_week).setVisibility(View.VISIBLE);
            findViewById(R.id.action_today).setVisibility(View.VISIBLE);
            findViewById(R.id.action_next_week).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.action_prev_week).setVisibility(View.GONE);
            findViewById(R.id.action_today).setVisibility(View.GONE);
            findViewById(R.id.action_next_week).setVisibility(View.GONE);
        }
    }






    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    private void changeWeek(int key) {
        switch (key) {
            case PREV:
                week.add(week.DAY_OF_WEEK, -7);
                break;
            case THIS:
                week = Calendar.getInstance();
                break;
            case NEXT:
                week.add(week.DAY_OF_WEEK, 7);
                break;
        }
        week.set(week.DAY_OF_WEEK, Calendar.MONDAY);
        mWeekView.goToDate(week);
    }

    private class CustomAdapter extends ArrayAdapter<WeekViewEvent> {
        private int viewResource;

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
            viewResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //view
            View view = LayoutInflater.from(getContext()).inflate(viewResource, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.event_view_name);
            //data
            WeekViewEvent event = getItem(position);

//            checkBox.setVisibility(option.isGroupName() ? View.GONE : View.VISIBLE);
            textView.setText(event.getName());

            return view;
        }
    }



}