package com.remijouannet.get10;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.remijouannet.get10.R;

import java.awt.font.TextAttribute;
import java.util.HashMap;

/*
    Copyright (C) 2015 Rémi Jouannet <remijouannet@gmail.com>
    This file is part of get10.
    get10 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */

public class get10 extends Application {
    public static final String TAG = get10.class.getSimpleName();

    public static enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public get10() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            int trackingid;
            int app_tracker = getResources().getIdentifier("app_tracker", "xml", getPackageName());
            int app_tracker_test = getResources().getIdentifier("app_tracker_test", "xml", getPackageName());

            if (app_tracker == 0){
                trackingid = app_tracker_test;
            }else {
                trackingid = app_tracker;
            }

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(trackingid);
            mTrackers.put(trackerId, t);
        }


        return mTrackers.get(trackerId);
    }
}
