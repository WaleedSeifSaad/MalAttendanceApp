package com.example.wseif.malattendanceapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class CheckInActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker gps = new GPSTracker(CheckInActivity.this);

        if (gps.canGetLocation()) {
            double deviceLat = gps.getLatitude(); // returns latitude
            double deviceLng = gps.getLongitude(); // returns longitude

            LatLng userLocation = new LatLng(deviceLat, deviceLng);

            mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").snippet("Latitude : "+userLocation.latitude+" , Longitude : " + userLocation.longitude));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,8));
            //mMap.animateCamera(CameraUpdateFactory.zoomBy(2));
            Toast.makeText(getApplicationContext(), "You aren't in correct area. Please go to The Greek Campus - Cairo Downtown.", Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
    }
}
