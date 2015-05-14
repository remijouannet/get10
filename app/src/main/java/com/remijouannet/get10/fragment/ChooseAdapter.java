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


package com.remijouannet.get10.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.remijouannet.get10.Data;
import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.gameMode.GameMode;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Settings;


public class ChooseAdapter extends ArrayAdapter<String> {
    private static final String TAG = ChooseAdapter.class.getSimpleName();
    private final Context context;
    private final String[] values;
    private final GameMode[] gameModes;
    private Typeface font;



    public ChooseAdapter(Context context, String[] values, GameMode[] gameModes) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
        this.gameModes = gameModes;
        this.font = Typeface.createFromAsset(context.getAssets(), "fonts/Lato/Lato-Regular.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        GameMode gameMode = gameModes[position];

        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView textViewScoreToUnlock = (TextView) rowView.findViewById(R.id.scoretounlock);
        TextView textViewLabel = (TextView) rowView.findViewById(R.id.label);
        TextView textViewScore = (TextView) rowView.findViewById(R.id.Score);
        TextView textViewDeath = (TextView) rowView.findViewById(R.id.Death);

        textViewScoreToUnlock.setTypeface(this.font);
        textViewLabel.setTypeface(this.font);
        textViewScore.setTypeface(this.font);
        textViewDeath.setTypeface(this.font);

        ImageView imageViewSkull = (ImageView) rowView.findViewById(R.id.skull);
        ImageView imageViewHighscore = (ImageView) rowView.findViewById(R.id.highscore);
        ImageView imageViewLock = (ImageView) rowView.findViewById(R.id.lock);

        if (!Data.getUnlock(gameMode.id)
                && gameMode.id != 0
                && Data.getConfig(FirstActivity.context.getString(R.string.key_unlock)) ){
            textViewScoreToUnlock.setText(String.valueOf(gameMode.scoreToUnlock));
            rowView.setBackgroundColor(Tools.getIntFromColor(Settings.gameOverColor));

            textViewLabel.setText(Integer.toString(gameMode.id));
            textViewLabel.setTextColor(Tools.getIntFromColor(Settings.colorWhite));
            textViewLabel.setAlpha(0.1f);

            textViewScore.setText(Integer.toString(0));
            textViewScore.setTextColor(Tools.getIntFromColor(Settings.colorWhite));
            textViewScore.setAlpha(0.1f);

            textViewDeath.setText(Integer.toString(0));
            textViewDeath.setTextColor(Tools.getIntFromColor(Settings.colorWhite));
            textViewDeath.setAlpha(0.1f);

            imageViewHighscore.setAlpha(0.1f);
            imageViewSkull.setAlpha(0.1f);
        }else {
            if (!Data.getUnlock(gameMode.id)
                    && Data.getConfig(FirstActivity.context.getString(R.string.key_unlock))){
                Data.setUnlock(gameMode.id, true);
            }

            rowView.setBackgroundColor(Tools.getIntFromColor(gameMode.color));

            textViewLabel.setText(Integer.toString(gameMode.id));
            textViewLabel.setTextColor(Tools.getIntFromColor(Settings.colorWhite));

            textViewScore.setText(Integer.toString(Data.getHighScore(gameMode.id)));
            textViewScore.setTextColor(Tools.getIntFromColor(Settings.colorWhite));

            textViewDeath.setText(Integer.toString(Data.getDeath(gameMode.id)));
            textViewDeath.setTextColor(Tools.getIntFromColor(Settings.colorWhite));

            imageViewLock.setVisibility(rowView.INVISIBLE);

            if (textViewLabel.getText().length() == 2){
                LinearLayout.LayoutParams currentMargin = (LinearLayout.LayoutParams) imageViewLock.getLayoutParams();
                LinearLayout.LayoutParams newMargin = new LinearLayout.LayoutParams(
                        currentMargin.width,
                        currentMargin.height);

                newMargin.setMargins(currentMargin.leftMargin - (int)Math.ceil((float)currentMargin.leftMargin/3.05f),
                        currentMargin.topMargin, 0, 0);

                imageViewLock.setLayoutParams(newMargin);
            }
        }

        return rowView;
    }
}
