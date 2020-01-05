package com.example.nguyenhongphuc98.checkmein.UI.event;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;
import com.example.nguyenhongphuc98.checkmein.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSetTimeFragment extends Fragment {

    Button btnNextToContent;
    EditText etDate;
    EditText etTimeBegin;
    EditText etTimeEnd;

    final Calendar myCalendar = Calendar.getInstance();

    public ViewSetTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_view_set_time, container, false);

        btnNextToContent=view.findViewById(R.id.btnNextToContent);
        etDate=view.findViewById(R.id.etDate);
        etTimeBegin=view.findViewById(R.id.etTimeBegin);
        etTimeEnd=view.findViewById(R.id.etTimeEnd);


        //set date picker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }

        };

        etDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etDate.getRight() - etDate.getCompoundDrawables()[2].getBounds().width())) {
                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        return true;
                    }
                }
                return false;
            }
        });



        //set timepicker

        final TimePickerDialog.OnTimeSetListener onStartTimeListener = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM;
                int am_pm;

                etTimeBegin.setText(hourOfDay + " Giờ " + minute+ " Phút");
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

            }
        };

        etTimeBegin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new TimePickerDialog(getContext(), onStartTimeListener, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();

            }
        });

        final TimePickerDialog.OnTimeSetListener onEndTimeListener = new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM;
                int am_pm;

                etTimeEnd.setText(hourOfDay + " Giờ " + minute+ " Phút");
                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

            }
        };

        etTimeEnd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new TimePickerDialog(getContext(), onEndTimeListener, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();

            }
        });



        btnNextToContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataCenter.eventDay=etDate.getText().toString();
                DataCenter.eventBegin=etTimeBegin.getText().toString();
                DataCenter.eventEnd=etTimeEnd.getText().toString();

                ViewPager pager = (ViewPager) view.getParent();
                pager.setCurrentItem(3);
            }
        });



        return view;
    }


    private void updateLabelDate(){
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

}
