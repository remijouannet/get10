package com.remijouannet.get10.Scenes.GameObjects.GameScene1;

import android.view.MotionEvent;

import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Graphic2D.GameObject;
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

public class BackgroundGS1 implements GameObject{
    public Shape background;
    public Shape bigCircle;
    public Shape littleCircle;
    GLRenderer glRenderer;
    GameScene1 gameScene1;

    public BackgroundGS1(GLRenderer glRenderer, GameScene1 gameScene1) {
        this.gameScene1 = gameScene1;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        background.rect.draw(mtrxProjectionAndView);
        bigCircle.circle.draw(mtrxProjectionAndView);
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView){
        littleCircle.circle.draw(mtrxProjectionAndView);
    }

    @Override
    public void init() {
        background = new Shape();
        bigCircle = new Shape();
        littleCircle = new Shape();
        create();
    }

    @Override
    public void create(){
        background.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2,
                glRenderer.screenWidth, glRenderer.screenHeight,
                Settings.color
        );

        bigCircle.circle.create(Settings.bigCircleRadius,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                Settings.colorWhite);

        littleCircle.circle.create(Settings.littleCircleRadius * 1.02f,
                glRenderer.screenWidth / 2,
                glRenderer.screenHeight / 2,
                Settings.color
        );
    }

    @Override
    public void reset(){
        background.rect.color = Settings.color;
        littleCircle.circle.color = Settings.color;
    }

    @Override
    public void gameOver() {
        background.rect.color = Settings.gameOverColor;
        littleCircle.circle.color = Settings.gameOverColor;
    }

    @Override
    public void touchEvent(MotionEvent event) {

    }

    @Override
    public void move() {

    }
}
