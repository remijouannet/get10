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
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.GameObject;
import com.remijouannet.get10.graphic2D.SpriteBatch;
import com.remijouannet.get10.graphic2D.texture.Texture;
import com.remijouannet.get10.graphic2D.Tools;
import com.remijouannet.get10.scenes.gameScene.GameScene2;
import com.remijouannet.get10.Settings;


public class ColumnsGS2 implements GameObject{
    private static final String TAG = ColumnsGS2.class.getSimpleName();
    public Shape[] columns;
    public Shape[] columnsWay;
    public Shape[] columnsGhost;
    public Shape startLine;

    public Texture[] textureColumns;
    public Texture textureBackground;

    float[] moveColumnsWay;
    float[] verticesColumnsGhost;
    float[] verticesColumnsWay;

    GLRenderer glRenderer;
    GameScene2 gameScene2;

    public ColumnsGS2(GLRenderer glRenderer, GameScene2 gameScene2) {
        this.gameScene2 = gameScene2;
        this.glRenderer = glRenderer;
    }

    @Override
    public void draw(float[] mtrxProjectionAndView) {
        startLine.draw(mtrxProjectionAndView);

        SpriteBatch.begin();
        SpriteBatch.addShape(mtrxProjectionAndView, columns);
        SpriteBatch.end();
    }

    @Override
    public void draw2(float[] mtrxProjectionAndView) {

    }

    @Override
    public void init() {
        columnsGhost = new Shape[Settings.numberColumns];
        columnsWay = new Shape[Settings.numberColumns];
        columns = new Shape[Settings.numberColumns * 2];
        moveColumnsWay = new float[Settings.numberColumns];
        startLine = new Shape();
        textureColumns = new Texture[columns.length];
        textureBackground = gameScene2.background.textureBackground.texture();
        create();
    }

    @Override
    public void create() {
        float rad = Settings.littleCircleRadius + ((Settings.bigCircleRadius - Settings.littleCircleRadius) / 2);
        //////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < columns.length; i += 2) {
            int e = i / 2;
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
            columns[i + 1] = new Shape();

            textureColumns[i] = new Texture(textureBackground);
            textureColumns[i + 1] = new Texture(textureBackground);

            verticesColumnsGhost = columnsGhost[e].getVertices();
            verticesColumnsWay = columnsWay[e].getVertices();

            textureColumns[i].getUvs(new float[]{
                    (verticesColumnsGhost[0] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[1] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[0] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[1] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[9] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[10] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[9] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[10] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
            });


            textureColumns[i + 1].getUvs(new float[]{
                    (verticesColumnsWay[3] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[4] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[3] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[4] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[6] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[7] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[6] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[7] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
            });


            columns[i].customPolygon.create(new float[]{
                    verticesColumnsGhost[0], verticesColumnsGhost[1], 0.0f,
                    verticesColumnsWay[0], verticesColumnsWay[1], 0.0f,
                    verticesColumnsWay[9], verticesColumnsWay[10], 0.0f,
                    verticesColumnsGhost[9], verticesColumnsGhost[10], 0.0f,
            }, columnsGhost[e].getIndices(), textureColumns[i]);

            columns[i + 1].customPolygon.create(new float[]{
                    verticesColumnsWay[3], verticesColumnsWay[4], 0.0f,
                    verticesColumnsGhost[3], verticesColumnsGhost[4], 0.0f,
                    verticesColumnsGhost[6], verticesColumnsGhost[7], 0.0f,
                    verticesColumnsWay[6], verticesColumnsWay[7], 0.0f,
            }, columnsGhost[e].getIndices(), textureColumns[i + 1]);

        }
        //////////////////////////////////////////////////////////////////////////
        startLine.rect.create(glRenderer.screenWidth / 2, glRenderer.screenHeight / 2.0f,
                Settings.width / 60, Settings.bigCircleRadius * 2,
                Settings.colorStartLine);
    }

    @Override
    public void gameOver() {

    }

    @Override
    public void touchEvent(MotionEvent event) {

    }

    @Override
    public void reset() {
        create();
    }

    @Override
    public void move() {
        for (int i = 0; i < columns.length; i+=2) {
            int e = i / 2;
            columnsGhost[e].rect.turnTurnAround(gameScene2.background.littleCircle.circle.centerX,
                    gameScene2.background.littleCircle.circle.centerY,
                    Settings.speedColumns, 0);

            if (columnsWay[e].rect.radTurn < Settings.littleCircleRadius + columnsWay[e].rect.rHeight/1.3f){
                moveColumnsWay[e] = Settings.speedColumnsWay;
            }

            if (columnsWay[e].rect.radTurn > Settings.bigCircleRadius - columnsWay[e].rect.rHeight/1.3f){
                moveColumnsWay[e] = -Settings.speedColumnsWay;
            }

            columnsWay[e].rect.turnTurnAround(gameScene2.background.littleCircle.circle.centerX,
                    gameScene2.background.littleCircle.circle.centerY,
                    Settings.speedColumns,
                    columnsWay[e].rect.radTurn + moveColumnsWay[e]);

            verticesColumnsGhost = columnsGhost[e].getVertices();
            verticesColumnsWay = columnsWay[e].getVertices();

            columns[i].customPolygon.setVertices(new float[]{
                    verticesColumnsGhost[0], verticesColumnsGhost[1], 0.0f,
                    verticesColumnsWay[0], verticesColumnsWay[1], 0.0f,
                    verticesColumnsWay[9], verticesColumnsWay[10], 0.0f,
                    verticesColumnsGhost[9], verticesColumnsGhost[10], 0.0f,
            });

            columns[i+1].customPolygon.setVertices(new float[]{
                    verticesColumnsWay[3], verticesColumnsWay[4], 0.0f,
                    verticesColumnsGhost[3], verticesColumnsGhost[4], 0.0f,
                    verticesColumnsGhost[6], verticesColumnsGhost[7], 0.0f,
                    verticesColumnsWay[6], verticesColumnsWay[7], 0.0f,
            });

            columns[i].customPolygon.uvs = new float[]{
                    (verticesColumnsGhost[0] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[1] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[0] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[1] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[9] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[10] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[9] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[10] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
            };

            columns[i + 1].customPolygon.uvs = new float[]{
                    (verticesColumnsWay[3] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[4] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[3] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[4] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsGhost[6] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsGhost[7] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
                    (verticesColumnsWay[6] * textureColumns[i].getWidth() / glRenderer.screenWidth) / textureColumns[i].getWidth(),
                    1.0f - (verticesColumnsWay[7] * textureColumns[i].getHeight() / glRenderer.screenHeight) / textureColumns[i].getHeight(),
            };
        }
    }
}
