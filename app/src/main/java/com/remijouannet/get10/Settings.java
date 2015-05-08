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


import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.gameMode.GameMode;
import com.remijouannet.get10.graphic2D.Tools;

public class Settings {
    public static float height;
    public static float width;
    public static long fpslimit = 80;
    public static float[] gameOverColor = new float[]{130.0f / 255.0f, 130.0f / 255.0f, 130.0f / 255.0f, 1.0f};
    public static float[] originalColorBird = new float[]{0.0f, 0.0f,180.0f/255.0f, 1.0f};
    public static float[] originalColorRestart = originalColorBird;
    public static float[] colorWhite = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    public static float[] colorStartLine = new float[]{0.0f, 0.0f, 0.0f, 0.1f};
    public static String font = "fonts/Lato/Lato-Black.ttf";
    public static boolean sound = true;
    public static boolean ads = true;


    public static float[] color = Tools.randomColor();

    public static float bigCircleRadius = 3.4f;
    public static float littleCircleRadius = 18;
    public static float sizeCloseButton = 9;
    public static float sizeBird = 40;
    public static float touchGravity = 235.0f;
    public static float fallGravity = -2000.0f;
    public static float speedBird = -1.0f;
    public static float speedColumns = 0.2f;
    public static float speedColumnsWay = 0.2f;
    public static float sizeColumnsWay = 4.5f;
    public static float sizeColumns = 40;
    public static int numberColumns = 4;
    public static int gameScene = 1;
    public static String background = "background/default.png";

    public static int currentGameMode;
    public static int scoreToUnlock;


    public Settings(float width, float height, GameMode gameMode) {
        sound = Data.getConfig(FirstActivity.context.getString(R.string.key_sound));
        ads = Data.getConfig(FirstActivity.context.getString(R.string.key_ads));

        currentGameMode = gameMode.id;
        scoreToUnlock = gameMode.scoreToUnlock;

        Settings.width = width;
        Settings.height = height;

        color = gameMode.color;

        speedBird = gameMode.speedBird;
        speedColumns = gameMode.speedColumns;
        speedColumnsWay = Settings.width / gameMode.speedColumnsWay;

        bigCircleRadius = Settings.width / gameMode.bigCircleRadius;
        littleCircleRadius = Settings.width / gameMode.littleCircleRadius;

        sizeBird = Settings.width / gameMode.sizeBird;
        touchGravity = Settings.width / gameMode.touchGravity;
        fallGravity = Settings.width / gameMode.fallGravity;

        sizeColumns = Settings.width / gameMode.sizeColumns;
        sizeColumnsWay = (bigCircleRadius + littleCircleRadius)/gameMode.sizeColumnsWay;
        numberColumns = gameMode.numberColumns;

        gameScene = gameMode.gameScene;
        background = gameMode.background;

        sizeCloseButton = Settings.width / gameMode.sizeCloseButton;
    }
}
