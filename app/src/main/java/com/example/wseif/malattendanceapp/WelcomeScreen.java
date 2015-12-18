package com.example.wseif.malattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class WelcomeScreen extends AppCompatActivity {

    TextView textViewWelcome;
    TextView textViewLastStatusValue;
    Button buttonCheckIn;
    Button buttonCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            InitilizeData(currentUser);
        } else {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    private void InitilizeData(final ParseUser parseUser){
        textViewWelcome = (TextView)findViewById(R.id.textViewWelcome);
        textViewWelcome.setText(getResources().getString(R.string.Welcome) + " " + parseUser.getUsername());

        textViewLastStatusValue = (TextView)findViewById(R.id.textViewLastStatusValue);
        textViewLastStatusValue.setText(parseUser.get("Status").toString());

        buttonCheckIn = (Button)findViewById(R.id.buttonCheckIn);
        buttonCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser.get("Status").toString().equals(getResources().getString(R.string.CheckIn))) {
                    Toast.makeText(getApplicationContext(), "Your current status is already " + getResources().getString(R.string.CheckIn) + ".", Toast.LENGTH_SHORT).show();
                }
                else {
                    parseUser.put("Status", getResources().getString(R.string.CheckIn));
                    parseUser.saveInBackground();
                    textViewLastStatusValue.setText(parseUser.get("Status").toString());
                }
            }
        });

        buttonCheckOut = (Button)findViewById(R.id.buttonCheckOut);
        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parseUser.get("Status").toString().equals(getResources().getString(R.string.CheckOut))) {
                    Toast.makeText(getApplicationContext(), "Your current status is already " + getResources().getString(R.string.CheckOut) + ".", Toast.LENGTH_SHORT).show();
                }
                else {
                    parseUser.put("Status", getResources().getString(R.string.CheckOut));
                    parseUser.saveInBackground();
                    textViewLastStatusValue.setText(parseUser.get("Status").toString());
            }
        }
    });

    }
}
