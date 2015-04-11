package com.remijouannet.get10.Graphic2D.Shape;

import android.opengl.GLES20;

import com.remijouannet.get10.Graphic2D.Helper.GraphicHelper;
import com.remijouannet.get10.Graphic2D.Helper.BufferHelper;
import com.remijouannet.get10.Graphic2D.Texture.Animation;
import com.remijouannet.get10.Graphic2D.Texture.Texture;
import com.remijouannet.get10.Graphic2D.Tools;

import java.lang.reflect.Array;

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

public class Rect {
    public float[] vertices;
    public short[] indices;
    public float[] uvs;
    public float centerX;
    public float centerY;
    public float rWidth;
    public float rHeight;

    Texture texture = null;
    Animation animation = null;
    public float[] color = null;

    public float angleTurnAround = 90;
    float angle2Turn = 0;
    float[] angle2;
    public float radTurn = 0;
    public float radius = 0;
    boolean[] change = new boolean[]{false, false};
    public boolean exist = false;

    public int drawingType = GLES20.GL_TRIANGLES;

    private BufferHelper buffer = null;

    ShapeBackup backup = new ShapeBackup();

    public void create(float x, float y, float l, float h, Texture texture) {
        this.texture = texture;
        centerX = x;
        centerY = y;
        rHeight = h;
        rWidth = l;
        uvs = texture.getUvs();
        create();
    }

    public void create(float x, float y, float l, float h, Animation animation) {
        this.animation = animation;
        centerX = x;
        centerY = y;
        rHeight = h;
        rWidth = l;
        uvs = this.animation.texture().getUvs();
        create();
    }

    public void create(float x, float y, float l, float h, float[] color) {
        this.color = color;
        centerX = x;
        centerY = y;
        rHeight = h;
        rWidth = l;
        create();
    }

    public void create() {
        /* We have create the setVertices of our view.
        setVertices = new float[]
                {  500f, 100f, 0.0f,        // top left
                        500f, 0.0f, 0.0f,   // bottom left
                        600f, 0.0f, 0.0f,   // bottom right
                        600f, 100f, 0.0f,   // top right
                };
        getColor = new float[]{ 255, 0, 0, 1.0f };
        getIndices = new short[] {0, 1, 2, 0, 2, 3}; // loop in the android official tutorial opengles why different order.*/

        indices = new short[]{0, 1, 2, 0, 2, 3};
        exist = true;
        vertices =  new float[]{
                centerX - rWidth /2, centerY - rHeight /2, 0.0f,
                centerX - rWidth /2, centerY + rHeight /2, 0.0f,
                centerX + rWidth /2, centerY + rHeight /2, 0.0f,
                centerX + rWidth /2, centerY - rHeight /2, 0.0f
                };

        radius = Tools.getDistance(centerX, centerY, vertices[0], vertices[1]);

        angle2 = new float[]{Tools.getAngle(vertices[9], vertices[10], centerX, centerY),
                Tools.getAngle(vertices[6], vertices[7], centerX, centerY),
                Tools.getAngle(vertices[0], vertices[1], centerX, centerY),
                Tools.getAngle(vertices[3], vertices[4], centerX, centerY)};
        backup();
    }

    public void backup(){
        backup.centerX = centerX;
        backup.centerY = centerY;
        backup.rHeight = rHeight;
        backup.rWidth = rWidth;
        if (color != null){
            backup.color = color;
        }
    }

    public void reset(){
        centerX = backup.centerX;
        centerY = backup.centerY;
        rHeight = backup.rHeight;
        rWidth = backup.rWidth;
        if (color != null) {
            color = backup.color;
        }
        radius = 0;
        angleTurnAround = 90;
        angle2Turn = 0;
        radTurn = 0;
        angle2 = null;
        create();
        change[0] = change[1] = true;
    }

    public float[] getUvs(){
        float[] uvsNew;
        if (texture != null){
            uvsNew = texture.getUvs();
        }else if (animation != null){
                uvsNew = animation.texture().getUvs();
        }else {
            return null;
        }

        if (uvsNew != uvs && uvsNew != null && buffer != null){
            uvs = uvsNew;
            this.buffer.setUvs(uvs);
        }
        return uvs;
    }

    public int getGLTEX(){
        if (texture != null){
            return texture.getGLTex();
        }else if (animation != null){
            return animation.texture().getGLTex();
        }
        return 0;
    }

    public float[] getVertices(){
        if (change[0]){
            translateSprite();
        }
        if (change[1] && buffer != null){
            this.buffer.setVertices(vertices);
        }
        change[0] =  change[1] = false;
        return vertices;
    }

    public BufferHelper getBuffer(){
        if (buffer == null){
            if (color != null){
                this.buffer = new BufferHelper(indices, getVertices());
            }else {
                this.buffer = new BufferHelper(indices, getVertices(), getUvs());
            }
        }
        getVertices();
        getUvs();
        return this.buffer;
    }

    public void draw(float[] mvpMatrix) {
        if (color != null){
            GraphicHelper.drawSolid(mvpMatrix, getVertices(), indices, color, drawingType, getBuffer());
        } else {
            GraphicHelper.drawTexture(mvpMatrix, getVertices(), indices, getUvs(),
                    getGLTEX(), drawingType, getBuffer());
        }
    }

    private void translateSprite() {
        /*setVertices = new float[]
                {(float) ((Math.cos(Math.toRadians(angle2[0])) * radius) + centerX), (float) ((Math.sin(Math.toRadians(angle2[0])) * radius) + centerY), 0.0f,        // top left
                        (float) ((Math.cos(Math.toRadians(angle2[1])) * radius) + centerX), (float) ((Math.sin(Math.toRadians(angle2[1])) * radius) + centerY), 0.0f,   // bottom left
                        (float) ((Math.cos(Math.toRadians(angle2[2])) * radius) + centerX), (float) ((Math.sin(Math.toRadians(angle2[2])) * radius) + centerY), 0.0f,   // bottom right
                        (float) ((Math.cos(Math.toRadians(angle2[3])) * radius) + centerX), (float) ((Math.sin(Math.toRadians(angle2[3])) * radius) + centerY), 0.0f,   // top right
                };*/
        vertices[0] = (float) ((Math.cos(Math.toRadians(angle2[0])) * radius) + centerX);
        vertices[1] = (float) ((Math.sin(Math.toRadians(angle2[0])) * radius) + centerY);
        vertices[3] = (float) ((Math.cos(Math.toRadians(angle2[1])) * radius) + centerX);
        vertices[4] = (float) ((Math.sin(Math.toRadians(angle2[1])) * radius) + centerY);
        vertices[6] = (float) ((Math.cos(Math.toRadians(angle2[2])) * radius) + centerX);
        vertices[7] = (float) ((Math.sin(Math.toRadians(angle2[2])) * radius) + centerY);
        vertices[9] = (float) ((Math.cos(Math.toRadians(angle2[3])) * radius) + centerX);
        vertices[10] = (float) ((Math.sin(Math.toRadians(angle2[3])) * radius) + centerY);
    }

    public void changeSize(float l, float h){
        rHeight = (h == 0)? rHeight : rHeight - h;
        rWidth = (l == 0)? rWidth : rWidth - l;

        vertices[0] = centerX - rWidth /2;
        vertices[1] = centerY - rHeight /2;
        vertices[3] = centerX - rWidth /2;
        vertices[4] = centerY + rHeight /2;
        vertices[6] = centerX + rWidth /2;
        vertices[7] = centerY + rHeight /2;
        vertices[9] = centerX + rWidth /2;
        vertices[10] = centerY - rHeight /2;

        radius = Tools.getDistance(centerX, centerY, vertices[0], vertices[1]);

        angle2[0] = Tools.getAngle(vertices[9], vertices[10], centerX, centerY);
        angle2[1] = Tools.getAngle(vertices[6], vertices[7], centerX, centerY);
        angle2[2] = Tools.getAngle(vertices[0], vertices[1], centerX, centerY);
        angle2[3] = Tools.getAngle(vertices[3], vertices[4], centerX, centerY);

        turn(angle2Turn);
    }

    public void customSize(float l, float h){
        rHeight = (h == 0)? rHeight :h;
        rWidth = (l == 0)? rWidth :l;

        vertices[0] = centerX - rWidth /2;
        vertices[1] = centerY - rHeight /2;
        vertices[3] = centerX - rWidth /2;
        vertices[4] = centerY + rHeight /2;
        vertices[6] = centerX + rWidth /2;
        vertices[7] = centerY + rHeight /2;
        vertices[9] = centerX + rWidth /2;
        vertices[10] = centerY - rHeight /2;

        radius = Tools.getDistance(centerX, centerY, vertices[0], vertices[1]);

        angle2[0] = Tools.getAngle(vertices[9], vertices[10], centerX, centerY);
        angle2[1] = Tools.getAngle(vertices[6], vertices[7], centerX, centerY);
        angle2[2] = Tools.getAngle(vertices[0], vertices[1], centerX, centerY);
        angle2[3] = Tools.getAngle(vertices[3], vertices[4], centerX, centerY);

        turn(angle2Turn);
    }


    public void followY(float fingerY) {
        centerY = fingerY;

        change[0] = change[1] = true;
    }

    public void followX(float fingerX) {
        centerX = fingerX;

        change[0] = change[1] = true;
    }

    public void follow(float fingerX, float fingerY) {
        followX(fingerX);
        followY(fingerY);
    }

    public void moveX(float move) {
        centerX += move;

        change[0] = change[1] = true;
    }

    public void moveY(float move) {
        centerY += move;

        change[0] = change[1] = true;
    }

    public void turnTurnAround(float x, float y, float turn, float rad){
        turnAround(x, y, turn, rad);
        turn(turn);
    }

    public void turnAround(float x, float y, float turn, float rad) {
        radTurn = (rad != 0)?rad:radTurn;
        angleTurnAround += turn;
        if (angleTurnAround > 360 ) {
            angleTurnAround -= 360;
        }else if (angleTurnAround < -360){
            angleTurnAround -= -360;
        }

        centerX = (float) ((Math.cos(Math.toRadians(angleTurnAround)) * radTurn) + x);
        centerY = (float) ((Math.sin(Math.toRadians(angleTurnAround)) * radTurn) + y);

        change[0] = change[1] = true;
    }

    public void turn(float turn) {
        angle2Turn += turn;

        if (angle2Turn > 360 ) {
            angle2Turn -= 360;
        }else if (angle2Turn < -360){
            angle2Turn -= -360;
        }
        for (int e = 0; e < Array.getLength(angle2); e++) {
            angle2[e] += turn;
            if (angle2[e] > 360 ) {
                angle2[e] -= 360;
            }else if (angle2[e] < -360){
                angle2[e] -= -360;
            }
        }

        change[0] = change[1] = true;
    }
}
