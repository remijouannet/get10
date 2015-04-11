package com.remijouannet.get10.Scenes.GameObjects.GameScene1;

import android.view.MotionEvent;

import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Graphic2D.Helper.GraphicHelper;
import com.remijouannet.get10.Graphic2D.Helper.BitmapHelper;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Graphic2D.GameObject;
import com.remijouannet.get10.Graphic2D.Texture.Animation;
import com.remijouannet.get10.Graphic2D.Texture.LoadTexture;
import com.remijouannet.get10.Graphic2D.Texture.TextureRegion;
import com.remijouannet.get10.Graphic2D.Tools;
import com.remijouannet.get10.Scenes.GameScene.GameScene1;
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

public class BirdGS1 implements GameObject{
    private final static String TAG = BirdGS1.class.getSimpleName();
    public Shape bird;
    public Shape birdHit;
    Animation loopBird;
    GLRenderer glRenderer;
    GameScene1 gameScene1;
    LoadTexture textureBird;

    float gravity;
    float velocity = 0;

    public BirdGS1(GLRenderer glRenderer, GameScene1 gameScene1) {
        this.gameScene1 = gameScene1;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        //Log.d(TAG, String.valueOf(Settings.originalColorBird[2]));
        GraphicHelper.drawTexture(mtrxProjectionAndView, bird, bird.getBuffer(),
                Settings.originalColorBird, Settings.color);
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
                    "bird/texture_manon_256x256.png", glRenderer.screenWidth)));
        }else {
            textureBird.bitmap(BitmapHelper.getAssetsBitmap(glRenderer.context.getAssets(),
                    "bird/texture_manon_256x256.png", glRenderer.screenWidth));
        }

        create();
    }

    @Override
    public void create(){
        //textureBird.recycle();

        /*
        loopBird = new Animation(Tools.fps(7), glRenderer, new TextureRegion[]{
                new TextureRegion(textureBird, new float[]{
                        0.0f, 1.0f/4.1f,
                        0.0f, 0.0f,
                        1.0f/3, 0.0f,
                        1.0f/3, 1.0f/4.1f
                }),
                new TextureRegion(textureBird, new float[]{
                        1.0f/1.5f, 1.0f/4.1f,
                        1.0f/1.5f, 0.0f,
                        1.0f, 0.0f,
                        1.0f, 1.0f/4.1f
                })});*/

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
                gameScene1.background.littleCircle.circle.centerX,
                gameScene1.background.littleCircle.circle.centerY);

        bird.rect.backup();
        birdHit.circle.backup();
    }

    @Override
    public void gameOver() {
        //loopBird.changeColor(Tools.getIntFromColor(Settings.getColor),
        //            Tools.getIntFromColor(Settings.gameOverColor));
    }

    @Override
    public void reset(){
        bird.rect.reset();
        birdHit.circle.reset();

        gravity = Tools.getDistance(bird.rect.centerX,
                bird.rect.centerY,
                gameScene1.background.littleCircle.circle.centerX,
                gameScene1.background.littleCircle.circle.centerY);
    }

    @Override
    public void move(){
        velocity += Settings.fallGravity;
        gravity += velocity;

        if (gravity < Settings.littleCircleRadius + bird.rect.rHeight/2.2f) {
            gravity = Settings.littleCircleRadius + bird.rect.rHeight/2.2f;
        } else if (gravity > Settings.bigCircleRadius - bird.rect.rHeight/2.2f) {
            gravity = Settings.bigCircleRadius - bird.rect.rHeight/2.2f;
        }

        bird.rect.turnTurnAround(gameScene1.background.littleCircle.circle.centerX,
                gameScene1.background.littleCircle.circle.centerY,
                Settings.speedBird, gravity);

        birdHit.circle.turnAround(gameScene1.background.littleCircle.circle.centerX,
                gameScene1.background.littleCircle.circle.centerY,
                Settings.speedBird, gravity);
    }
}
