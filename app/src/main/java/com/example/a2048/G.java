package com.example.a2048;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class G extends Application {

    public static int score;
    public static int best;
    public static MainActivity activity;
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        best = preferences.getInt("BEST", 0);
    }

    public static void updateScore() {
        if (score > best) {
            best = score;
        }
        if (activity != null) {
            activity.updateScore();
        }
    }
}
