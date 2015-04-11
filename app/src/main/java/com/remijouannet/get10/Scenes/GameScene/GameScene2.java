package com.remijouannet.get10.Scenes.GameScene;


import android.view.MotionEvent;

import com.remijouannet.get10.Data;
import com.remijouannet.get10.Graphic2D.GameScene;
import com.remijouannet.get10.Scenes.Collision.CollisionGS2;
import com.remijouannet.get10.Scenes.GameObjects.GameScene2.BackgroundGS2;
import com.remijouannet.get10.Scenes.GameObjects.GameScene2.BirdGS2;
import com.remijouannet.get10.Scenes.GameObjects.GameScene2.ColumnsGS2;
import com.remijouannet.get10.Graphic2D.GLRenderer;
import com.remijouannet.get10.Scenes.GameObjects.GameScene2.UIPauseGS2;
import com.remijouannet.get10.Scenes.GameObjects.GameScene2.UIRunGS2;
import com.remijouannet.get10.Scenes.GameWorld;
import com.remijouannet.get10.Scenes.SceneState;
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

public class GameScene2 implements GameScene{
    private final static String TAG = GameScene2.class.getSimpleName();
    GLRenderer glRenderer;
    public GameWorld gameWorld;
    public BackgroundGS2 background;
    public ColumnsGS2 columns;
    public BirdGS2 bird;
    public UIPauseGS2 uiPause;
    public UIRunGS2 uiRun;
    public String STATE = SceneState.PAUSE;
    public CollisionGS2 collisionGS2;
    public int score = 0;
    public boolean invinsible = false;

    public GameScene2(GLRenderer glRenderer, GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.glRenderer = glRenderer;
    }

    @Override
    public void init() {
        background = new BackgroundGS2(glRenderer, this);
        columns = new ColumnsGS2(glRenderer, this);
        bird = new BirdGS2(glRenderer, this);
        collisionGS2 = new CollisionGS2(glRenderer, this);
        uiPause = new UIPauseGS2(glRenderer, this);
        uiRun = new UIRunGS2(glRenderer, this);

        background.init();
        columns.init();
        bird.init();
        uiPause.init();
        uiRun.init();

        STATE = SceneState.PAUSE;
    }

    @Override
    public void drawStatic(float[] mtrxProjectionAndView) {
        background.draw(mtrxProjectionAndView);
        columns.draw(mtrxProjectionAndView);
        bird.draw(mtrxProjectionAndView);
        background.draw2(mtrxProjectionAndView);
        uiRun.draw(mtrxProjectionAndView);
    }

    @Override
    public void drawMove(float[] mtrxProjectionAndView) {
        move();
        collision();
        if (!STATE.equals(SceneState.STOPDRAW)){
            background.draw(mtrxProjectionAndView);
            columns.draw(mtrxProjectionAndView);
            bird.draw(mtrxProjectionAndView);
            background.draw2(mtrxProjectionAndView);
        }
        if (!STATE.equals(SceneState.STOP)){
            uiRun.draw(mtrxProjectionAndView);
        }else if (STATE.equals(SceneState.STOP)){
            uiPause.draw(mtrxProjectionAndView);
        }
    }

    @Override
    public void touchEvent(MotionEvent event) {
        bird.touchEvent(event);
        if (STATE.equals(SceneState.STOP)){
            if (collisionGS2.collisionRestartTouch(event)){
                STATE = SceneState.STOPDRAW;
                reset();
                return;
            }
            if (collisionGS2.collisionCloseTouch(event)){
                glRenderer.Return();
            }
        }else if (STATE.equals(SceneState.PAUSE)
                && !STATE.equals(SceneState.STOPDRAW)) {
            STATE = SceneState.MOVE;
        }
    }

    @Override
    public void move() {
        if (STATE.equals(SceneState.MOVE)) {
            bird.move();
            columns.move();
        }else if (STATE.equals(SceneState.STOP)){
            uiPause.move();
        }
    }

    @Override
    public void collision() {
        if (STATE.equals(SceneState.MOVE)){
            if (collisionGS2.collisionBirdColumns() && !invinsible){
                gameOver();
            }
            if (collisionGS2.collisionScore()){
                score += 1;
            }
        }
    }

    @Override
    public void gameOver() {
        Data.setHighScore(Settings.currentGameMode, score);
        Data.setDeath(Settings.currentGameMode);
        STATE = SceneState.STOP;
        score = 0;
        collisionGS2 = new CollisionGS2(glRenderer, this);
    }

    @Override
    public void reset(){
        bird.reset();
        columns.reset();
        background.reset();
        STATE = SceneState.PAUSE;
    }
}
