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
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.remijouannet.get10.Data;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Settings;

public class FirstActivity extends Activity {
    private static final String TAG = FirstActivity.class.getSimpleName(); /* define log tag*/
    private static Bundle Instance;

    public static Point size = new Point();
    public static double screenInches;
    public static double density;
    public static DisplayMetrics metrics;
    public static Context context;

    private FragmentManager fragmentManager;

    private static ImageView imageView1;

    private static int currentFragmentID = R.id.FirstFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Instance = savedInstanceState;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getSize(size);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenInches = Math.sqrt(Math.pow((double)metrics.widthPixels/(double)metrics.densityDpi,2)+
                Math.pow((double)metrics.heightPixels/(double)metrics.densityDpi,2));
        density = metrics.densityDpi;

        fullscreen();

        setContentView(R.layout.first_activity);

        context = getApplicationContext();
        new Data(getApplicationContext());
        fragmentManager = getFragmentManager();

        if (Build.VERSION.SDK_INT >= 21){
            Settings.sound = false;
        }

        SwitchFragment(getCurrentFragment());

        imageView1 = (ImageView) findViewById(R.id.close);
        imageView1.setVisibility(View.VISIBLE);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
                imageView1.setVisibility(View.VISIBLE);
            }
        });

        imageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    imageView1.setVisibility(View.INVISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    imageView1.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        imageView1.setVisibility(View.VISIBLE);
        fragmentManager.findFragmentById(currentFragmentID).onResume();
        fullscreen();
    }

    @Override
    protected void onPause() {
        super.onPause();
        imageView1.setVisibility(View.VISIBLE);
        fragmentManager.findFragmentById(currentFragmentID).onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void close(){
        if(!fragmentManager.findFragmentById(R.id.FirstFragment).isHidden()){
            onBackPressed();
        }else {
            FirstFragment();
        }
        imageView1.setVisibility(View.VISIBLE);
    }

    public void SwitchFragment(int oldFrag, int newFrag){
        fragmentManager.beginTransaction()
                .hide(fragmentManager.findFragmentById(oldFrag))
                .show(fragmentManager.findFragmentById(newFrag))
                .commit();
        setCurrentFragment(newFrag);
    }

    public void SwitchFragment(int newFrag){
        if (newFrag == R.id.FirstFragment){
            SwitchFragment (R.id.ChooseFragment, newFrag);
        }else if(newFrag == R.id.ChooseFragment){
            SwitchFragment (R.id.FirstFragment, newFrag);
        }
    }

    public void ChooseFragment(){
        SwitchFragment(R.id.FirstFragment, R.id.ChooseFragment);
    }

    public void FirstFragment(){
        SwitchFragment(R.id.ChooseFragment, R.id.FirstFragment);
    }

    public void setCurrentFragment(int fragment){
        this.currentFragmentID = fragment;
    }

    public int getCurrentFragment(){
        return this.currentFragmentID;
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
