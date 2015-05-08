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

package com.remijouannet.get10.graphic2D;

import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class Tools {
    private static String TAG = Tools.class.getSimpleName();
    private static Random rand = new Random();
    private static double degrees;


    public static float getAngle(float p1X, float p1Y, float p2X, float p2Y) {
        degrees = Math.toDegrees((Math.atan2(p1X - p2X, p1Y - p2Y)));
        degrees = ((degrees < 0) ? -(degrees - 180) : degrees) + 90;
        return (float) ((degrees > 360) ? degrees -= 360 : degrees);
    }

    public static float getDistance(float p1X, float p1Y, float p2X, float p2Y) {
        return (float) Math.sqrt((p2X - p1X) * (p2X - p1X) + (p2Y - p1Y) * (p2Y - p1Y));
    }

    public static float getRandFloat(float min, float max) {
        return min + rand.nextFloat() * (max - min);
    }

    public static int getRandInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static int ptToPixel(int pt, DisplayMetrics metrics){
        return (int) ( (pt * metrics.densityDpi) / 72 );
    }

    public static int dpToPixel(int dp, DisplayMetrics metrics){
        return (int) ( dp * (metrics.densityDpi / 160.0f) );
    }
    public static int getIntFromColor(float[] color) {
        return 0xFF000000 | ((Math.round(255 * color[0]) << 16) & 0x00FF0000)
                | ((Math.round(255 * color[1]) << 8) & 0x0000FF00)
                | (Math.round(255 * color[2]) & 0x000000FF);
    }

    public static int fps(int fps) {
        return (1000 / fps);
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static float[] randomColor(){
        switch (Tools.getRandInt(0, 10)){
            case 0:
                return new float[]{52.0f/255.0f, 152.0f/255.0f, 219.0f/255.0f,1.0f};
            case 1:
                return new float[]{39.0f/255.0f, 174.0f/255.0f, 96.0f/255.0f,1.0f};
            case 2:
                return new float[]{155.0f/255.0f, 89/255.0f, 182/255.0f,1.0f};
            case 3:
                return new float[]{52.0f/255.0f, 73/255.0f, 94/255.0f,1.0f};
            case 4:
                return new float[]{241/255.0f, 196/255.0f, 15/255.0f,1.0f};
            case 5:
                return new float[]{231/255.0f, 76/255.0f, 60/255.0f,1.0f};
            case 6:
                return new float[]{230/255.0f, 126/255.0f, 34/255.0f,1.0f};
            case 7:
                return new float[]{26/255.0f, 188/255.0f, 156/255.0f,1.0f};
            case 8:
                return new float[]{155/255.0f, 89/255.0f, 182/255.0f,1.0f};
            case 9:
                return new float[]{135/255.0f, 211/255.0f, 124/255.0f,1.0f};
            case 10:
                return new float[]{211/255.0f, 84/255.0f, 0/255.0f,1.0f};
            default:
                return new float[]{100.0f / 255.0f, 128.0f / 255.0f, 185.0f / 255.0f, 1.0f};
        }
    }


}
