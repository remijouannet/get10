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

package com.remijouannet.get10.scenes;

import android.view.MotionEvent;

import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameScene;
import com.remijouannet.get10.scenes.gameScene.GameScene1;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;

public class GameWorld {

    GLRenderer glRenderer;
    public GameScene gameScene;

    public GameWorld(GLRenderer glRenderer) {
        this.glRenderer = glRenderer;
        this.glRenderer.SetMaxFPS(Settings.fpslimit);
        this.gameScene = getGameScene(Settings.gameScene);
        this.gameScene.init();
    }

    public void drawmove(float[] mtrxProjectionAndView) {
        gameScene.drawMove(mtrxProjectionAndView);
    }

    public void drawstatic(float[] mtrxProjectionAndView) {
        gameScene.drawStatic(mtrxProjectionAndView);
    }

    public void touchEvent(MotionEvent event) {
        gameScene.touchEvent(event);
    }

    public void reset(){
        gameScene.reset();
    }

    public GameScene getGameScene(int id){
        if (id == 1){
            return new GameScene1(this.glRenderer, this);
        }else if (id == 2){
            return new GameScene2(this.glRenderer, this);
        }else {
            return new GameScene1(this.glRenderer, this);
        }
    }
}
