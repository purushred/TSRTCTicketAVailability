package com.smart.telanganartc;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Purushotham on 06-05-2015.
 */
public class TSRTCApplication extends Application {
    public void onCreate() {
        Parse.initialize(this, "wMkX8kk7027ip2E874WlieIVmkQWDUL04YBZuRmR", "IEiD2hQvH8epvqOpJOJW2hCXx8wB4L2zMwCFGJSd");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.e("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
