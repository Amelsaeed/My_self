package com.example.ahmedmagdy.theclinic.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.activities.DoctorProfileActivity;
import com.example.ahmedmagdy.theclinic.classes.DoctorFirebaseClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AHMED MAGDY on 10/21/2018.
 */

public class DoctorAdapter extends ArrayAdapter<DoctorFirebaseClass> implements Filterable {
    private Activity context;
    List<DoctorFirebaseClass> doctorList;
    private List<DoctorFirebaseClass> mSearchList;

    private String a1;

    public DoctorAdapter(Activity context, List<DoctorFirebaseClass> doctorList) {
        super(context, R.layout.list_layout_doctors, doctorList);
        this.context = context;
        this.doctorList = doctorList;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.list_layout_doctors, null, true);

        final TextView adoctorname = (TextView) listViewItem.findViewById(R.id.doctor_name);
        final TextView adoctorspecialty = (TextView) listViewItem.findViewById(R.id.doctor_specialty);
        final TextView adoctorcity = (TextView) listViewItem.findViewById(R.id.doctor_city);


        final ImageView adoctorphoto = (ImageView) listViewItem.findViewById(R.id.doctor_photo);

        DoctorFirebaseClass doctorclass = doctorList.get(position);
        //asize = trampList.size();


        adoctorphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorFirebaseClass doctorclass = doctorList.get(position);
                Intent uIntent = new Intent(context, DoctorProfileActivity.class);
                uIntent.putExtra("DoctorID", doctorclass.getcId());
                /** uIntent.putExtra("DoctorName", doctorclass.getcName());
                uIntent.putExtra("DoctorCity", doctorclass.getcCity());
                uIntent.putExtra("DoctorSpecialty", doctorclass.getcSpecialty());
                uIntent.putExtra("DoctorUri", doctorclass.getcUri());**/

               // uIntent.putExtra("userid",  hometramp.getUserId());
                uIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(uIntent);
                // context.finish();
            }
        });



        adoctorname.setText(doctorclass.getcName());
        adoctorspecialty.setText(doctorclass.getcSpecialty());
        adoctorcity.setText(doctorclass.getcCity());

        a1=doctorclass.getcUri();
        if(a1 != null) {

            /** RequestOptions requestOptions = new RequestOptions();
             requestOptions = requestOptions.transforms(new RoundedCorners(16));**/
            //requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

            Glide.with(context)
                    .load(a1)
                    .apply(RequestOptions.circleCropTransform())
                    // .apply(requestOptions)
                    .into(adoctorphoto);
        }else{
            Glide.with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/the-clinic-66fa1.appspot.com/o/doctor_logo_m.jpg?alt=media&token=d3108b95-4e16-4549-99b6-f0fa466e0d11")
                    .apply(RequestOptions.circleCropTransform())
                    // .apply(requestOptions)
                    .into(adoctorphoto);
        }

        return listViewItem;
    }
    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults resultsFilter = new FilterResults();
                final List<DoctorFirebaseClass> resultsList = new ArrayList<>();
                if (mSearchList == null)
                    mSearchList = doctorList;
                if (constraint != null) {
                    if (mSearchList != null && mSearchList.size() > 0) {
                        for (final DoctorFirebaseClass tramp : mSearchList) {
                            if (tramp.getcName().toLowerCase()
                                    .contains(constraint.toString()))
                                resultsList.add(tramp);
                        }
                    }
                    resultsFilter.values = resultsList;
                }
                return resultsFilter;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                doctorList = (ArrayList<DoctorFirebaseClass>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
