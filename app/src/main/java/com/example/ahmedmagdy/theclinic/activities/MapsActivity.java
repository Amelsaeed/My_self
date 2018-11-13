package com.example.ahmedmagdy.theclinic.activities;

import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.ahmedmagdy.theclinic.R;
import com.example.ahmedmagdy.theclinic.classes.Mapinfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener,GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap;
    private ChildEventListener mChildEventListener;
    private DatabaseReference databaseMap;
    Marker marker;
    List<Mapinfo> venueList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;
        databaseMap= FirebaseDatabase.getInstance().getReference("mapdb");
        venueList = new ArrayList<>();
        databaseMap.push().setValue(marker);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        databaseMap.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){

                    //   Mapinfo user =s.getValue(Mapinfo.class);

                    String DoctorName = s.child("cmname").getValue(String.class);
                    Double latitude =  Double.parseDouble(s.child("cmlatitude").getValue(String.class));
                    Double longitude =  Double.parseDouble(s.child("cmlongitude").getValue(String.class));
                    Mapinfo user=new Mapinfo(DoctorName,latitude,longitude);
                    venueList.add(user);
                    for (int i = 0; i < venueList.size(); i++)
                    {
                        LatLng location = new LatLng(user.latitude,user.longitude);
                        if (mMap != null) {
                            marker = mMap.addMarker(new MarkerOptions().position(location).title(user.name));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}