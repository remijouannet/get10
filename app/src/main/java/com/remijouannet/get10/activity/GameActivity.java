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


package com.remijouannet.get10.activity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.remijouannet.get10.GameSurfaceView;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Settings;
import com.remijouannet.get10.get10;


public class GameActivity extends Activity {
    private static final String TAG = GameActivity.class.getSimpleName();
    private GLSurfaceView glSurfaceView;
    private static RelativeLayout layout;

    private static InterstitialAd interstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        fullscreen();

        setContentView(R.layout.game_activity);
        layout = (RelativeLayout) findViewById(R.id.game_layout);

        loadInterstitial();

        Tracker t = ((get10) getApplication()).getTracker(
                get10.TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);
        t.setScreenName("GameActivity");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        RelativeLayout.LayoutParams glParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        glSurfaceView = new GameSurfaceView(this);
        //glSurfaceView.getPreserveEGLContextOnPause();
        layout.addView(glSurfaceView, glParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        if (Tools.getRandInt(0, 2) == 0){
            displayInterstitial();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GameActivity.this, FirstActivity.class);
        startActivity(i);
        finish();
    }

    public static void displayInterstitial() {
        if (Settings.ads && interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void loadInterstitial(){
        //the real ad_unit_id isn't push to repo
        int ad_unit_id;

        int banner_ad_unit_id = getResources().getIdentifier("banner_ad_unit_id", "string", getPackageName());
        int banner_ad_unit_id_test = getResources().getIdentifier("banner_ad_unit_id_test", "string", getPackageName());

        if (banner_ad_unit_id == 0){
            ad_unit_id = banner_ad_unit_id_test;
        }else {
            ad_unit_id = banner_ad_unit_id;
        }

        if (Settings.ads) {
            interstitial = new InterstitialAd(GameActivity.this);
            interstitial.setAdUnitId(getString(ad_unit_id));
            AdRequest adRequest;
            if(AdRequest.DEVICE_ID_EMULATOR.equals("4B1393DA3E29398C05AB92059B15F775")){
                adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("4B1393DA3E29398C05AB92059B15F775")
                    .build();
            }else {
                adRequest = new AdRequest.Builder().build();
            }
            interstitial.loadAd(adRequest);
        }
    }

    public void fullscreen(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 19){
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }else {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}