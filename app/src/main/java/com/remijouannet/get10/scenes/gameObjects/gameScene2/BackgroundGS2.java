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

import android.view.MotionEvent;

import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.helper.BitmapHelper;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.texture.LoadTexture;
import com.remijouannet.get10.graphic2D.texture.TextureRegion;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class BackgroundGS2 implements GameObject{
    private static final String TAG = BackgroundGS2.class.getSimpleName();
    public Shape background;
    public Shape bigCircle;
    public Shape littleCircle;
    public LoadTexture textureBackground;

    GLRenderer glRenderer;
    GameScene2 gameScene2;

    public BackgroundGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        background.draw(mtrxProjectionAndView);
        bigCircle.draw(mtrxProjectionAndView);
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {
        littleCircle.draw(mtrxProjectionAndView);
    }

    @Override
    public void init() {
        background = new Shape();
        bigCircle = new Shape();
        littleCircle = new Shape();
        textureBackground = new LoadTexture(glRenderer);

        textureBackground.bitmap(BitmapHelper.cropBitmap(
                BitmapHelper.getAssetsBitmap(
                        glRenderer.context.getAssets(), Settings.background),
                glRenderer.screenRatio));
        //textureBackground.recycle();

        create();
    }

    @Override
    public void create() {
        background.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2,
                glRenderer.screenWidth, glRenderer.screenHeight,
                textureBackground.texture()
        );

        bigCircle.circle.create(Settings.bigCircleRadius,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                new float[]{Settings.colorWhite[0],
                        Settings.colorWhite[1],
                        Settings.colorWhite[2],
                        0.6f});

        littleCircle.circle.create(Settings.littleCircleRadius * 1.02f,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                new TextureRegion(textureBackground,
                        textureBackground.texture().getWidth() / 2,
                        textureBackground.texture().getHeight() / 2,
                        (Settings.littleCircleRadius * textureBackground.texture().getHeight() / glRenderer.screenHeight) * 2).texture()
        );

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
