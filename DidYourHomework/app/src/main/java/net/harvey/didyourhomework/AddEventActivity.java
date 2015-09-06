    package net.harvey.didyourhomework;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.support.v4.app.FragmentManager;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TimePicker;

    /**
     * Created by junsu on 15. 7. 18.
     */
    public class AddEventActivity extends AppCompatActivity{
        private EditText eventName;
        private Button startDate;
        private Button startTime;
        private Button endDate;
        private Button endTime;
        private Spinner repeatSchedule;
        private EditText eventDetail;
        private FragmentManager fm = getSupportFragmentManager();
        private Button cancel;
        private Button save;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_event);
            final DBManager dbManager = new DBManager(getApplicationContext(), "Event.db", null, 1);

            eventName = (EditText) findViewById(R.id.event_name);
            startDate = (Button) findViewById(R.id.start_date);
            startTime = (Button) findViewById(R.id.start_time);
            endDate = (Button) findViewById(R.id.end_date);
            endTime = (Button) findViewById(R.id.end_time);
            eventDetail = (EditText) findViewById(R.id.event_detail);

            repeatSchedule = (Spinner) findViewById(R.id.repeat_schedule);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.repeat_list, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            repeatSchedule.setAdapter(adapter);


            cancel = (Button) findViewById(R.id.cancel);
            save = (Button) findViewById(R.id.save);


            //DatePicker
            startDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment dialogFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            startDate.setText(year + "-" + (month + 1) + "-" + day);
                        }
                    };
                    dialogFragment.show(fm, "datePicker");
                }
            });
            startTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment dialogFragment = new TimePickerFragment() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            startTime.setText(hourOfDay + " : " + minute);
                        }
                    };
                    dialogFragment.show(fm, "timePicker");
                }
            });
            endDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment dialogFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet (DatePicker view, int year, int month, int day) {
                            endDate.setText(year + "-" + (month+1) + "-" + day);
                        }
                    };
                    dialogFragment.show(fm, "datePicker");
                }
            });
            endTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment dialogFragment = new TimePickerFragment() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            endTime.setText(hourOfDay + " : " + minute);
                        }
                    };
                    dialogFragment.show(fm, "timePicker");
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = eventName.getText().toString();
                    String start = startDate.getText().toString() + startDate.getText().toString();
                    String end = endDate.getText().toString() + endDate.getText().toString();
                    String detail = eventDetail.getText().toString();

                    dbManager.insert("insert into EVENT_LIST values(null, '"
                                    + name + "','"
                                    + start + "','"
                                    + end + "','"
                                    + detail + "',null,null,null);");
                    finish();
                }
            });

        }
    }
