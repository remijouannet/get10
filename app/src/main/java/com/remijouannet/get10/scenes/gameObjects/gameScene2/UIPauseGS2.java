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

package com.remijouannet.get10.scenes.gameObjects.gameScene2;

import android.util.Log;
import android.view.MotionEvent;

import com.remijouannet.get10.Data;
import com.remijouannet.get10.R;
import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.helper.BitmapHelper;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.helper.GraphicHelper;
import com.remijouannet.get10.graphic2D.texture.LoadTexture;
import com.remijouannet.get10.graphic2D.texture.Message;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class UIPauseGS2 implements GameObject{
    private final static String TAG = UIPauseGS2.class.getSimpleName();
    public Shape restart;
    LoadTexture restartTexture;

    public Shape close;
    LoadTexture closeTexture;

    public Message highScore;


    GLRenderer glRenderer;
    GameScene2 gameScene2;

    public UIPauseGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        GraphicHelper.drawTexture(mtrxProjectionAndView, restart, restart.getBuffer());
        GraphicHelper.drawTexture(mtrxProjectionAndView, close, close.getBuffer());
        highScore.draw(mtrxProjectionAndView,
                glRenderer.context.getResources().getString(R.string.highscore) + " : " + Data.getHighScore(Settings.currentGameMode),
                glRenderer.screenWidth /9.0f, glRenderer.screenHeight /1.1f, Tools.dpToPixel(40, FirstActivity.metrics));

    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {
    }

    @Override
    public void touchEvent(MotionEvent event) {
    }

    @Override
    public void init() {
        restartTexture = new LoadTexture(glRenderer);
        restart = new Shape();

        closeTexture = new LoadTexture(glRenderer);
        close = new Shape();

        highScore = gameScene2.messageLato;

        create();
    }

    @Override
    public void create() {
        //RESTART
        restartTexture.bitmap(BitmapHelper.getAssetsBitmap(
                glRenderer.context.getAssets(), "UI/power_restart_black.png", glRenderer.screenWidth));
        //restartTexture.recycle();

        restart.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2.0f,
                Settings.littleCircleRadius * 2, Settings.littleCircleRadius * 2, restartTexture.texture()
        );

        //CLOSE
        closeTexture.bitmap(BitmapHelper.getAssetsBitmap(
                glRenderer.context.getAssets(), "UI/close_black.png", glRenderer.screenWidth));
        //closeTexture.recycle();

        close.rect.create(glRenderer.screenWidth / 9.0f, glRenderer.screenHeight / 15.0f,
                Settings.sizeCloseButton, Settings.sizeCloseButton, closeTexture.texture()
        );
    }

    @Override
    public void reset() {

    }

    @Override
    public void gameOver() {
    }

    @Override
    public void move() {
        restart.rect.turn(-0.5f);
    }
}
