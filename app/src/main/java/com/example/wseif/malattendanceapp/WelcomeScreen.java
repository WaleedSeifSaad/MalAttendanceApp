package com.example.wseif.malattendanceapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.*;
import java.util.Date;
import java.util.List;

public class WelcomeScreen extends AppCompatActivity {

    TextView textViewWelcome;
    TextView textViewLastStatusValue;
    TextView textViewLastStatusTime;
    Button buttonCheckIn;
    Button buttonCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            initilizeData(currentUser);
        } else {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    private void initilizeData(final ParseUser parseUser) {

        updateUserData(parseUser);

        buttonCheckIn = (Button) findViewById(R.id.buttonCheckIn);
        buttonCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser.get("Status").toString().equals(getResources().getString(R.string.CheckIn))) {
                    Toast.makeText(getApplicationContext(), "Your current status is already " + getResources().getString(R.string.CheckIn) + ".", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRole");
                    query.whereEqualTo("LocationName", "The Greek Campus - Cairo Downtown");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {

                                //Log.d("score", "Retrieved " + list.size() + " scores");

                                ParseGeoPoint parseGeoPoint = (ParseGeoPoint) list.get(0).get("Area");
                                Date checkInDate = (Date) list.get(0).get("checkinTime");
                                Date checkOutDate = (Date) list.get(0).get("checkoutTime");

                                GPSTracker gps = new GPSTracker(WelcomeScreen.this);

                                // gps enabled} // return boolean true/false
                                if (gps.canGetLocation()) {
                                    double deviceLat = gps.getLatitude(); // returns latitude
                                    double deviceLng = gps.getLongitude(); // returns longitude
                                    Date dateNow = new Date();

                                    if (java.lang.Math.abs(deviceLat - parseGeoPoint.getLatitude()) > 0.001 || java.lang.Math.abs(deviceLng - parseGeoPoint.getLongitude()) > 0.001) {
                                        Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
                                        startActivity(intent);
                                    } else if (dateNow.getHours() <= checkInDate.getHours()-2) {
                                        Toast.makeText(getApplicationContext(), "You can't check in before " + checkInDate + ".", Toast.LENGTH_SHORT).show();
                                    } else {
                                        parseUser.put("Status", getResources().getString(R.string.CheckIn));
                                        parseUser.saveInBackground();

                                        ParseObject userActivity = new ParseObject("UserActivity");
                                        userActivity.put("createdBy", ParseUser.getCurrentUser());
                                        userActivity.put("Status", getResources().getString(R.string.CheckIn));
                                        userActivity.saveInBackground();

                                        //textViewLastStatusValue.setText(parseUser.get("Status").toString());
                                        updateUserData(parseUser);
                                    }
                                } else {
                                    gps.showSettingsAlert();
                                }
                            } else {
                                //Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        buttonCheckOut = (Button) findViewById(R.id.buttonCheckOut);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser.get("Status").toString().equals(getResources().getString(R.string.CheckOut))) {
                    Toast.makeText(getApplicationContext(), "Your current status is already " + getResources().getString(R.string.CheckOut) + ".", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRole");
                    query.whereEqualTo("LocationName", "The Greek Campus - Cairo Downtown");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {

                                //Log.d("score", "Retrieved " + list.size() + " scores");

                                ParseGeoPoint parseGeoPoint = (ParseGeoPoint) list.get(0).get("Area");
                                Date checkInDate = (Date) list.get(0).get("checkinTime");
                                Date checkOutDate = (Date) list.get(0).get("checkoutTime");

                                GPSTracker gps = new GPSTracker(WelcomeScreen.this);

                                // gps enabled} // return boolean true/false
                                if (gps.canGetLocation()) {
                                    double deviceLat = gps.getLatitude(); // returns latitude
                                    double deviceLng = gps.getLongitude(); // returns longitude
                                    Date dateNow = new Date();
                                    if (java.lang.Math.abs(deviceLat - parseGeoPoint.getLatitude()) > 0.001 || java.lang.Math.abs(deviceLng - parseGeoPoint.getLongitude()) > 0.001) {
                                        Intent intent = new Intent(getApplicationContext(), CheckInActivity.class);
                                        startActivity(intent);
                                    } else if (dateNow.getHours() > checkOutDate.getHours()-2) {
                                        Toast.makeText(getApplicationContext(), "You can't check in before " + checkInDate + ".", Toast.LENGTH_SHORT).show();
                                    } else {
                                        parseUser.put("Status", getResources().getString(R.string.CheckOut));
                                        parseUser.saveInBackground();

                                        ParseObject userActivity = new ParseObject("UserActivity");
                                        userActivity.put("createdBy", ParseUser.getCurrentUser());
                                        userActivity.put("Status", getResources().getString(R.string.CheckOut));
                                        userActivity.saveInBackground();

                                        //textViewLastStatusValue.setText(parseUser.get("Status").toString());
                                        updateUserData(parseUser);
                                    }
                                } else {
                                    gps.showSettingsAlert();
                                }
                            } else {
                                //Log.d("score", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUserData(final ParseUser parseUser) {
        textViewWelcome = (TextView)findViewById(R.id.textViewWelcome);
        textViewWelcome.setText(getResources().getString(R.string.Welcome) + " " + parseUser.getUsername());

        textViewLastStatusValue = (TextView)findViewById(R.id.textViewLastStatusValue);
        textViewLastStatusValue.setText(parseUser.get("Status").toString());

        if(parseUser.get("Status").toString().equals(getResources().getString(R.string.CheckOut)))
            textViewLastStatusValue.setTextColor(Color.rgb(255, 0, 0));
        else
            textViewLastStatusValue.setTextColor(Color.rgb(0, 0, 255));

        textViewLastStatusTime = (TextView)findViewById(R.id.textViewLastStatusTime);
        textViewLastStatusTime.setText(parseUser.getUpdatedAt().toString());
    }
}
