package com.example.andreea.eat_and_meet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Quontini on 07/02/2018.
 */

public class DatePickerFragment extends DialogFragment {

    private Calendar date;
    private DatePickerFragmentListener listener;

    public Dialog onCreateDialog(Bundle savedInstenceState){

        super.onCreateDialog(savedInstenceState);

        if(date==null){

            date = Calendar.getInstance();
            date.set(Calendar.YEAR, 2018);
            date.set(Calendar.MONTH, 1);
            date.set(Calendar.DAY_OF_MONTH, 1);

        }

        final DatePicker datePicker = new DatePicker(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(datePicker);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                date.set(Calendar.YEAR, datePicker.getYear());
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if (listener!=null){
                    listener.onDatePickerFragmentOkButton(DatePickerFragment.this, date);

                }
            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(listener!=null){
                    listener.onDatePickerFragmentCancelButton(DatePickerFragment.this);

                }
            }
        });

        return builder.create();
    }

    public Calendar getDate(){
        return date;
    }

    public void setDate(Calendar date){
        this.date=date;

    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener l){
        this.listener=l;
    }

    public interface DatePickerFragmentListener{

        public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date);
        public void onDatePickerFragmentCancelButton(DialogFragment dialog);

    }
}
