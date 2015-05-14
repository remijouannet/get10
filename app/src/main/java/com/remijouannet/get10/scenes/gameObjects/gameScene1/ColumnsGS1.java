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

package com.remijouannet.get10.scenes.gameObjects.gameScene1;

import android.view.MotionEvent;

import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.SpriteBatch;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.gameScene.GameScene1;
import com.remijouannet.get10.Settings;


public class ColumnsGS1 implements GameObject{
    private static final String TAG = ColumnsGS1.class.getSimpleName();

    public Shape[] columns;
    public Shape[] columnsWay;
    public Shape[] columnsGhost;
    public Shape startLine;

    float[] moveColumnsWay;
    float[] verticesColumnsGhost;
    float[] verticesColumnsWay;

    GLRenderer glRenderer;
    GameScene1 gameScene1;

    public ColumnsGS1(GLRenderer glRenderer, GameScene1 gameScene1) {
        this.gameScene1 = gameScene1;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        startLine.rect.draw(mtrxProjectionAndView);

        SpriteBatch.begin();
        SpriteBatch.addShape(mtrxProjectionAndView, columns);
        SpriteBatch.end();
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView){

    }

    @Override
    public void init() {
        columnsGhost = new Shape[Settings.numberColumns];
        columnsWay = new Shape[Settings.numberColumns];
        columns = new Shape[Settings.numberColumns *2];
        moveColumnsWay = new float[Settings.numberColumns];
        startLine = new Shape();
        create();
    }

    @Override
    public void create(){
        float rad = Settings.littleCircleRadius + ((Settings.bigCircleRadius - Settings.littleCircleRadius)/2);
        //////////////////////////////////////////////////////////////////////////
        for (int i =0; i < columns.length; i+=2){
            int e = i/2;
            columnsGhost[e] = new Shape();

            columnsGhost[e].rect.create(glRenderer.screenWidth / 2,
                    glRenderer.screenHeight / 2 + Settings.littleCircleRadius + ((Settings.bigCircleRadius - Settings.littleCircleRadius) / 2),
                    Settings.sizeColumns,
                    (Settings.bigCircleRadius - Settings.littleCircleRadius)*1.01f
                    , Settings.color);
            columnsGhost[e].rect.turnTurnAround(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2,
                    ((360 / Settings.numberColumns) * e) - 60, rad);

            columnsWay[e] = new Shape();
            moveColumnsWay[e] = Settings.speedColumnsWay;
            columnsWay[e].rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 1.5f,
                    Settings.sizeColumns, Settings.sizeColumnsWay, Settings.colorWhite);
            columnsWay[e].rect.turnTurnAround(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2,
                    ((360 / Settings.numberColumns) * e) - 60,
                    Tools.getRandFloat(Settings.littleCircleRadius + columnsWay[e].rect.rHeight,
                            Settings.bigCircleRadius - columnsWay[e].rect.rHeight));

            columns[i] = new Shape();
            columns[i+1] = new Shape();

            verticesColumnsGhost = columnsGhost[e].getVertices();
            verticesColumnsWay = columnsWay[e].getVertices();

            columns[i].customPolygon.create(new float[]{
                    verticesColumnsGhost[3], verticesColumnsGhost[4], verticesColumnsGhost[5],
                    verticesColumnsWay[3], verticesColumnsWay[4], verticesColumnsWay[5],
                    verticesColumnsWay[6], verticesColumnsWay[7], verticesColumnsWay[8],
                    verticesColumnsGhost[6], verticesColumnsGhost[7], verticesColumnsGhost[8],
            }, columnsGhost[e].getIndices(), Settings.color);

            columns[i+1].customPolygon.create(new float[]{
                    verticesColumnsWay[0], verticesColumnsWay[1], verticesColumnsWay[2],
                    verticesColumnsGhost[0], verticesColumnsGhost[1], verticesColumnsGhost[2],
                    verticesColumnsGhost[9], verticesColumnsGhost[10], verticesColumnsGhost[11],
                    verticesColumnsWay[9], verticesColumnsWay[10], verticesColumnsWay[11],
            }, columnsGhost[e].getIndices(), Settings.color);
        }
        //////////////////////////////////////////////////////////////////////////
        startLine.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2.0f,
                Settings.width / 60, Settings.bigCircleRadius * 2,
                Settings.colorStartLine);
    }

    @Override
    public void gameOver() {
        for (Shape column : columns){
            column.customPolygon.color = Settings.gameOverColor;
        }
    }

    @Override
    public void touchEvent(MotionEvent event) {

    }

    @Override
    public void reset(){
        create();
    }

    @Override
    public void move(){
        for (int i =0; i < columns.length; i+=2){
            int e = i/2;
            columnsGhost[e].rect.turnTurnAround(gameScene1.background.littleCircle.circle.centerX,
                   gameScene1.background.littleCircle.circle.centerY,
                    Settings.speedColumns, 0);

            if (columnsWay[e].rect.radTurn < Settings.littleCircleRadius + columnsWay[e].rect.rHeight/1.3f){
                moveColumnsWay[e] = Settings.speedColumnsWay;
            }

            if (columnsWay[e].rect.radTurn > Settings.bigCircleRadius - columnsWay[e].rect.rHeight/1.3f){
                moveColumnsWay[e] = -Settings.speedColumnsWay;
            }

            columnsWay[e].rect.turnTurnAround(gameScene1.background.littleCircle.circle.centerX,
                    gameScene1.background.littleCircle.circle.centerY,
                    Settings.speedColumns,
                    columnsWay[e].rect.radTurn + moveColumnsWay[e]);

            verticesColumnsGhost = columnsGhost[e].getVertices();
            verticesColumnsWay = columnsWay[e].getVertices();

            columns[i].customPolygon.setVertices(new float[]{
                    verticesColumnsGhost[3], verticesColumnsGhost[4], verticesColumnsGhost[5],
                    verticesColumnsWay[3], verticesColumnsWay[4], verticesColumnsWay[5],
                    verticesColumnsWay[6], verticesColumnsWay[7], verticesColumnsWay[8],
                    verticesColumnsGhost[6], verticesColumnsGhost[7], verticesColumnsGhost[8],
            });
            columns[i+1].customPolygon.setVertices(new float[]{
                    verticesColumnsWay[0], verticesColumnsWay[1], verticesColumnsWay[2],
                    verticesColumnsGhost[0], verticesColumnsGhost[1], verticesColumnsGhost[2],
                    verticesColumnsGhost[9], verticesColumnsGhost[10], verticesColumnsGhost[11],
                    verticesColumnsWay[9], verticesColumnsWay[10], verticesColumnsWay[11],
            });
        }
    }
}
