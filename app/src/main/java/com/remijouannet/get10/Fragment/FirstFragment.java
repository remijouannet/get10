package com.remijouannet.get10.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.remijouannet.get10.Activity.FirstActivity;
import com.remijouannet.get10.Activity.FirstActivityAnimation;
import com.remijouannet.get10.Data;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Settings;

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

public class FirstFragment extends Fragment {
    private static final String TAG = FirstFragment.class.getSimpleName();
    private static Button button;

    private static ImageView imageView;
    private static FirstActivityAnimation animView = null;

    private static ImageView dialogSound;
    private static ImageView dialogAds;
    private static ImageView dialogClose;

    private static View rootview;

    private Context context;

    public FirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        this.rootview = rootView;
        context = rootView.getContext();

        button = (Button) rootView.findViewById(R.id.button);
        button.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato/Lato-Regular.ttf"));

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
        params.bottomMargin = (int) (FirstActivity.size.y/3 - button.getTextSize());
        button.setLayoutParams(params);

        imageView = (ImageView) rootView.findViewById(R.id.settings);

        button.setVisibility(View.VISIBLE);

        imageView.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((FirstActivity)getActivity()).ChooseFragment();
                button.setVisibility(View.VISIBLE);
            }
        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    button.setVisibility(View.INVISIBLE);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    button.setVisibility(View.VISIBLE);
                return false;
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                imageView.setVisibility(View.VISIBLE);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    imageView.setVisibility(View.INVISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    imageView.setVisibility(View.VISIBLE);
                return false;
            }
        });


        animView = (FirstActivityAnimation) rootView.findViewById(R.id.anim_view);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        button.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        if (animView != null)
            animView.playAnimation();
        else
            animView = (FirstActivityAnimation) this.rootview.findViewById(R.id.anim_view);
    }

    @Override
    public void onPause() {
        super.onPause();
        button.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        if (animView != null)
            animView.stopAnimation();
    }

    private void showDialog(){
        final Dialog alertDialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        alertDialog.setContentView(R.layout.settings_layout);

        dialogSound = (ImageView)alertDialog.findViewById(R.id.sound);
        dialogAds = (ImageView)alertDialog.findViewById(R.id.ads);
        dialogClose = (ImageView)alertDialog.findViewById(R.id.close);

        if (Data.getConfig(context.getString(R.string.key_sound))){
            dialogSound.setTag(R.drawable.sound);
            dialogSound.setImageResource(R.drawable.sound);
        }else {
            dialogSound.setTag(R.drawable.sound_close);
            dialogSound.setImageResource(R.drawable.sound_close);
        }

        if (Data.getConfig(context.getString(R.string.key_ads))){
            dialogAds.setTag(R.drawable.ads);
            dialogAds.setImageResource(R.drawable.ads);
        }else {
            dialogAds.setTag(R.drawable.ads_close);
            dialogAds.setImageResource(R.drawable.ads_close);
        }

        dialogSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int)dialogSound.getTag() == R.drawable.sound){
                    dialogSound.setTag(R.drawable.sound_close);
                    dialogSound.setImageResource(R.drawable.sound_close);
                    Settings.sound = false;
                    Data.setConfig(context.getString(R.string.key_sound), Settings.sound);
                }else if((int)dialogSound.getTag() == R.drawable.sound_close){
                    dialogSound.setTag(R.drawable.sound);
                    dialogSound.setImageResource(R.drawable.sound);
                    Settings.sound = true;
                    Data.setConfig(context.getString(R.string.key_sound), Settings.sound);
                }
            }
        });

        dialogAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((int)dialogAds.getTag() == R.drawable.ads){
                    dialogAds.setTag(R.drawable.ads_close);
                    dialogAds.setImageResource(R.drawable.ads_close);
                    Settings.ads = false;
                    Data.setConfig(context.getString(R.string.key_ads), Settings.ads);
                }else if((int)dialogAds.getTag() == R.drawable.ads_close){
                    dialogAds.setTag(R.drawable.ads);
                    dialogAds.setImageResource(R.drawable.ads);
                    Settings.ads = true;
                    Data.setConfig(context.getString(R.string.key_ads), Settings.ads);
                }
            }
        });


        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getActivity().getWindow().getDecorView().getSystemUiVisibility());
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}