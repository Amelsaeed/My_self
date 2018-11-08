package com.example.ahmedmagdy.theclinic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.BookingTimesClass;

import java.util.HashMap;
import java.util.List;

public class BookingExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<BookingTimesClass> mListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<BookingTimesClass, List<BookingTimesClass>> mListDataChild;

    public BookingExpandableListAdapter(Context _context, List<BookingTimesClass> _listDataHeader, HashMap<BookingTimesClass, List<BookingTimesClass>> _listDataChild) {
        this.mContext = _context;
        this.mListDataHeader = _listDataHeader;
        this.mListDataChild = _listDataChild;
    }

    @Override
    public int getGroupCount() {
        return mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mListDataChild.get(mListDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mListDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {  // i : group item position , i1 : child item position
        return mListDataChild.get(mListDataHeader.get(i))
                .get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        BookingTimesClass header = (BookingTimesClass) getGroup(i);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_header, viewGroup, false);
        }

        TextView bookingAddress = (TextView) convertView.findViewById(R.id.booking_ad_tv);
        TextView bookingTime = (TextView) convertView.findViewById(R.id.booking_time_tv);

        bookingTime.setText(header.getCtPeriod());
        bookingAddress.setText(header.getCtAddress());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {

        final BookingTimesClass currentChild = (BookingTimesClass) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.booking_list_item, viewGroup , false);
        }

        TextView patientName = (TextView) convertView.findViewById(R.id.patient_name_tv);
        TextView patientAge = (TextView) convertView.findViewById(R.id.patient_age_tv);
        ImageView patientPicture = (ImageView) convertView.findViewById(R.id.patient_image);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.patient_check_box) ;

        patientName.setText(currentChild.getCtname());
        patientAge.setText(currentChild.getCtage());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
