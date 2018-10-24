package com.example.ahmedmagdy.theclinic.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import com.example.ahmedmagdy.theclinic.activities.DoctorProfileActivity;
import com.example.ahmedmagdy.theclinic.classes.BookingClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kd.dynamic.calendar.generator.ImageGenerator;

import java.util.Calendar;
import java.util.List;

/**
 * Created by AHMED MAGDY on 10/23/2018.
 */

public class BookingAdapter extends ArrayAdapter<BookingClass> {


    private Activity context;
    List<BookingClass> bookingList;
    /**public String id;
    public String doctorID;

    public BookingAdapter(DoctorProfileActivity context , List<BookingClass> bookingList, String id, String doctorID) {
        super((Context) context, R.layout.list_layout_booking, bookingList);

        this.context = context;
        this.bookingList = bookingList;
        this.id = id;
        this.doctorID = doctorID;
    }**/

    public BookingAdapter( Activity context, List<BookingClass> bookingList) {
        super((Context) context, R.layout.list_layout_booking, bookingList);

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
                       String datedmy= Year+"_"+ (Month+1)+"_"+Day;
                        Toast.makeText(context, datedmy, Toast.LENGTH_LONG).show();
                       // Toast.makeText(context, id+doctorID, Toast.LENGTH_LONG).show();
//getbookdata();
                        //editTextcal.setText(Year+"_"+ ((Month/10)+1)+"_"+Day);
                        mCurrentDate.set(Year, ((Month+1)),Day);
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
/**
    private void getbookdata() {
        final DatabaseReference databaseBooking = FirebaseDatabase.getInstance().getReference("bookingdb").child(DoctorID);

        ////import data of country and tope
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    type = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ctype").getValue(String.class);
                    country = dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("ccountry").getValue(String.class);
                    maketable();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                }
            };
            databaseReg .addValueEventListener(postListener);
    }**/

}
