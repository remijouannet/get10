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

import android.util.Log;
import android.view.MotionEvent;

import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.graphic2D.Shape.Shape;
import com.remijouannet.get10.graphic2D.Helper.BitmapHelper;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.Helper.GraphicHelper;
import com.remijouannet.get10.graphic2D.Texture.LoadTexture;
import com.remijouannet.get10.graphic2D.Texture.Message;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.GameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class UIPauseGS2 implements GameObject{
    private final static String TAG = UIPauseGS2.class.getSimpleName();
    public Shape restart;
    public Shape close;
    public Shape gameOver;
    LoadTexture restartTexture;
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
        gameOver.Draw(mtrxProjectionAndView);
        GraphicHelper.drawTexture(mtrxProjectionAndView, restart, restart.getBuffer(),
                Settings.originalColorBird, Settings.colorWhite);
        GraphicHelper.drawTexture(mtrxProjectionAndView, close, close.getBuffer(),
                Settings.originalColorBird, Settings.colorWhite);
        highScore = new Message(glRenderer, GLRenderer.context.getAssets(), "fonts/Lato/Lato-Bold.ttf",
                Tools.ptToPixel(18, FirstActivity.metrics), 2, 2, Tools.getIntFromColor(Settings.colorWhite));

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
        restartTexture.bitmap(BitmapHelper.getAssetsBitmap(
                glRenderer.context.getAssets(), "UI/power_restart.png", glRenderer.screenWidth));
        //restartTexture.recycle();

        closeTexture = new LoadTexture(glRenderer);
        close = new Shape();
        closeTexture.bitmap(BitmapHelper.getAssetsBitmap(
                glRenderer.context.getAssets(), "UI/close.png", glRenderer.screenWidth));
        //closeTexture.recycle();

        highScore = new Message(glRenderer, GLRenderer.context.getAssets(), "fonts/Lato/Lato-Bold.ttf",
                Tools.ptToPixel(18, FirstActivity.metrics), 2, 2, Tools.getIntFromColor(Settings.colorWhite));

        gameOver = new Shape();
        create();
    }

    @Override
    public void create() {
        //RESTART
        restart.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2.0f,
                Settings.littleCircleRadius * 2, Settings.littleCircleRadius * 2, restartTexture.texture()
        );

        //CLOSE
        close.rect.create(glRenderer.screenWidth / 10.0f, glRenderer.screenHeight / 10.0f,
                Settings.sizeCloseButton, Settings.sizeCloseButton, closeTexture.texture()
        );

        //Game Over
        Log.d(TAG, glRenderer.screenWidth + ";" + glRenderer.screenHeight);
        gameOver.rect.create(glRenderer.screenWidth/2, glRenderer.screenHeight/2 ,
                glRenderer.screenWidth, glRenderer.screenHeight,
                new float[]{
                Settings.gameOverColor[0], Settings.gameOverColor[1], Settings.gameOverColor[2], 0.5f});
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
