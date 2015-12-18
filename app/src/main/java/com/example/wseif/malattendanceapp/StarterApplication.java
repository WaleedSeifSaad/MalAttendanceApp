package com.example.wseif.malattendanceapp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by wseif on 12/17/2015.
 */
public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this);

        //ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        //ParseACL.setDefaultACL(defaultACL, true);
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        // testObject.saveInBackground();
    }
}