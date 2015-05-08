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

package com.remijouannet.get10;

import android.content.Context;
import android.content.SharedPreferences;


public class Data {
    private Context context;
    public static SharedPreferences highScore;
    public static SharedPreferences death;
    public static SharedPreferences unlock;
    public static SharedPreferences config;

    public Data(Context context){
        this.context = context;
        highScore = context.getSharedPreferences(context.getString(R.string.shared_highscore), this.context.MODE_PRIVATE);
        death = context.getSharedPreferences(context.getString(R.string.shared_death), this.context.MODE_PRIVATE);
        unlock = context.getSharedPreferences(context.getString(R.string.shared_unlock), this.context.MODE_PRIVATE);
        config = context.getSharedPreferences(context.getString(R.string.shared_config), this.context.MODE_PRIVATE);
    }

    public static void setHighScore(int id, int score){
        if (score > getHighScore(id)){
            SharedPreferences.Editor editor = highScore.edit();
            editor.putInt(String.valueOf(id), score);
            editor.apply();
        }
    }

    public static void setDeath(int id){
        SharedPreferences.Editor editor = death.edit();
        editor.putInt(String.valueOf(id), getDeath(id) + 1);
        editor.apply();
    }

    public static void setUnlock(int id, boolean unlock){
        SharedPreferences.Editor editor = Data.unlock.edit();
        editor.putBoolean(String.valueOf(id), unlock);
        editor.apply();
    }

    public static void setConfig(String key, boolean value){
        SharedPreferences.Editor editor = Data.config.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static int getHighScore(int id){
       return highScore.getInt(String.valueOf(id), 0);
    }

    public static int getDeath(int id){
        return death.getInt(String.valueOf(id), 0);
    }

    public static boolean getUnlock(int id){
        return unlock.getBoolean(String.valueOf(id), false);
    }

    public static boolean getConfig(String key){ return config.getBoolean(key, true); }
}
