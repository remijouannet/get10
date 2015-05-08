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

package com.remijouannet.get10.scenes.GameObjects.GameScene2;

import android.view.MotionEvent;

import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.Texture.Message;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.GameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class UIRunGS2 implements GameObject{
    private static final String TAG = UIRunGS2.class.getSimpleName();
    public Message scoreMessage;

    GLRenderer glRenderer;
    GameScene2 gameScene2;

    int Score = 0;

    public UIRunGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
        Score = gameScene2.score;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        if (gameScene2.score != Score) {
            Score = gameScene2.score;
        }

        //TODO:That's fucking ugly
        if (String.valueOf(Score).length() > 1) {
            scoreMessage.draw(mtrxProjectionAndView, String.valueOf(Score),
                    (glRenderer.screenWidth / 2) - scoreMessage.cellWidth / 4.5f,
                    glRenderer.screenHeight / 1.1f);
        } else {
            scoreMessage.draw(mtrxProjectionAndView, String.valueOf(Score), glRenderer.screenWidth / 2, glRenderer.screenHeight / 1.1f);
        }
    }


    @Override
    public void draw2(float[] mtrxProjectionAndView) {

    }

    @Override
    public void init() {
        scoreMessage = new Message(glRenderer, GLRenderer.context.getAssets(), "fonts/Lato/Lato-Bold.ttf",
                Tools.ptToPixel(35, FirstActivity.metrics), 2, 2, Tools.getIntFromColor(Settings.colorWhite));
        create();
    }

    @Override
    public void create() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void gameOver() {

    }

    @Override
    public void touchEvent(MotionEvent event) {

    }

    @Override
    public void move() {

    }
}
