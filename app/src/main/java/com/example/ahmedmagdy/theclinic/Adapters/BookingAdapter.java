package com.example.ahmedmagdy.theclinic.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.activities.RegisterActivity;
import com.example.ahmedmagdy.theclinic.classes.BookingClass;
import com.example.ahmedmagdy.theclinic.classes.DoctorFirebaseClass;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;
import java.util.List;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */

public class BookingAdapter extends ArrayAdapter<BookingClass> {

    private Activity context;
    List<BookingClass> bookingList;

    public BookingAdapter(Activity context, List<BookingClass> bookingList) {
        super(context, R.layout.list_layout_booking, bookingList);
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_layout_booking, null, true);

        final TextView abookingtime = (TextView) listViewItem.findViewById(R.id.time_book);
        final TextView abookingaddress = (TextView) listViewItem.findViewById(R.id.Adress_book);


        final ImageView abookingphoto = (ImageView) listViewItem.findViewById(R.id.image_book);

        BookingClass bookingclass = bookingList.get(position);
        //asize = trampList.size();
        ///***********************calender***********************************************//
        ImageGenerator mImageGenerator = new ImageGenerator(context);

// Set the icon size to the generated in dip.
        mImageGenerator.setIconSize(50, 50);

// Set the size of the date and month font in dip.
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);

// Set the position of the date and month in dip.
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

// Set the color of the font to be generated
        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);

        abookingphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mCurrentDate = Calendar.getInstance();
                int year=mCurrentDate.get(Calendar.YEAR);
                int month=mCurrentDate.get(Calendar.MONTH);
                int day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mPickerDialog =  new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int Month, int Day) {
                       String gg= Year+"_"+ (Month+1)+"_"+Day;
                        Toast.makeText(context, gg, Toast.LENGTH_LONG).show();

                        //editTextcal.setText(Year+"_"+ ((Month/10)+1)+"_"+Day);
                        mCurrentDate.set(Year, ((Month/10)+1),Day);
                        //   mImageGenerator.generateDateImage(mCurrentDate, R.drawable.empty_calendar);
                    }
                }, year, month, day);
                mPickerDialog.show();
            }
        });
        ///***********************calender***********************************************//

        abookingtime.setText(bookingclass.getCbtime());
        abookingaddress.setText(bookingclass.getCbaddress());
        return listViewItem;
    }

}
