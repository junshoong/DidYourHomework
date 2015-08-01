package net.harvey.didyourhomework;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by junsu on 15. 8. 1.
 */
public class DatePickerDialogFragment extends DialogFragment {

    public DatePickerDialogFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datepicker, container, false);
        getDialog().setTitle("Hi?");
        return view;
    }
}
