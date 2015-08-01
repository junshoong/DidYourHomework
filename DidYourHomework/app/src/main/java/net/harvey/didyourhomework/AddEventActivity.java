package net.harvey.didyourhomework;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

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


        //DatePicker
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
                dialogFragment.show(fm, "frag_dialog_test");
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
                dialogFragment.show(fm, "frag_dialog_test");
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
                dialogFragment.show(fm, "frag_dialog_test");
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
                dialogFragment.show(fm, "frag_dialog_test");
            }
        });

    }
}
