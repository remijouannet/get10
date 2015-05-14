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
import com.remijouannet.get10.graphic2D.helper.GraphicHelper;
import com.remijouannet.get10.graphic2D.texture.Animation;
import com.remijouannet.get10.graphic2D.texture.LoadTexture;
import com.remijouannet.get10.graphic2D.texture.TextureRegion;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;

public class BirdGS2 implements GameObject{
    private final static String TAG = BirdGS2.class.getSimpleName();
    public Shape bird;
    public Shape birdHit;
    Animation loopBird;
    LoadTexture textureBird;
    GLRenderer glRenderer;
    GameScene2 gameScene2;

    float gravity;
    float velocity = 0;

    public BirdGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        //Log.d(TAG, String.valueOf(Settings.originalColorBird[2]));
        GraphicHelper.drawTexture(mtrxProjectionAndView, bird, bird.getBuffer());
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {

    }

    @Override
    public void touchEvent(MotionEvent event) {
        //gravity for bird
        velocity = Settings.touchGravity;
    }

    @Override
    public void init() {
        bird = new Shape();
        birdHit = new Shape();
        textureBird = new LoadTexture(glRenderer);

        if (Settings.speedBird > 0){
            textureBird.bitmap(BitmapHelper.mirrorBitmap(BitmapHelper.getAssetsBitmap(glRenderer.context.getAssets(),
                    Settings.bird, glRenderer.screenWidth)));
        }else {
            textureBird.bitmap(BitmapHelper.getAssetsBitmap(glRenderer.context.getAssets(),
                    Settings.bird, glRenderer.screenWidth));
        }

        create();
    }

    @Override
    public void create() {
        loopBird = new Animation(Tools.fps(7), glRenderer, new TextureRegion[]{
                new TextureRegion(textureBird, new float[]{
                        0.0f, 1.0f/2.87f,
                        0.0f, 0.0f,
                        1.0f/2.0f, 0.0f,
                        1.0f/2.0f, 1.0f/2.87f
                }),
                new TextureRegion(textureBird, new float[]{
                        1.0f/2.0f, 1.0f/2.87f,
                        1.0f/2.0f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f/2.87f
                })});

        bird.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 1.5f,
                Settings.sizeBird * 1.7f, Settings.sizeBird, loopBird
        );

        birdHit.circle.create(Settings.sizeBird / 2.0f,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 1.5f,
                Settings.color);

        gravity = Tools.getDistance(bird.rect.centerX,
                bird.rect.centerY,
                gameScene2.background.littleCircle.circle.centerX,
                gameScene2.background.littleCircle.circle.centerY);

        bird.rect.backup();
        birdHit.circle.backup();
    }

    @Override
    public void gameOver() {
    }

    @Override
    public void reset() {
        bird.rect.reset();
        birdHit.circle.reset();

        gravity = Tools.getDistance(bird.rect.centerX,
                bird.rect.centerY,
                gameScene2.background.littleCircle.circle.centerX,
                gameScene2.background.littleCircle.circle.centerY);
    }

    @Override
    public void move() {
        velocity += Settings.fallGravity;
        gravity += velocity;

        if (gravity < Settings.littleCircleRadius + bird.rect.rHeight/2.2f) {
            gravity = Settings.littleCircleRadius + bird.rect.rHeight/2.2f;
        } else if (gravity > Settings.bigCircleRadius - bird.rect.rHeight/2.2f) {
            gravity = Settings.bigCircleRadius - bird.rect.rHeight/2.2f;
        }

        bird.rect.turnTurnAround(gameScene2.background.littleCircle.circle.centerX,
                gameScene2.background.littleCircle.circle.centerY,
                Settings.speedBird, gravity);

        birdHit.circle.follow(bird.rect.centerX, bird.rect.centerY);
    }
}
