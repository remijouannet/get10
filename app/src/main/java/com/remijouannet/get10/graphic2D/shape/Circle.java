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


package com.remijouannet.get10.graphic2D.shape;


import android.opengl.GLES20;

import com.remijouannet.get10.graphic2D.helper.GraphicHelper;
import com.remijouannet.get10.graphic2D.helper.BufferHelper;
import com.remijouannet.get10.graphic2D.texture.Animation;
import com.remijouannet.get10.graphic2D.texture.Texture;
import com.remijouannet.get10.graphic2D.Tools;

public class Circle {
    private final static String TAG = Circle.class.getSimpleName();
    public float[] vertices;
    public short[] indices;

    public float[] uvs;
    public float radius;
    public float centerX;
    public float centerY;

    float angleTurnAround = 90;
    public float radTurn = 0;

    Texture texture = null;
    Animation animation = null;

    public float[] color;

    boolean[] change = new boolean[]{false, false};

    public boolean exist = false;

    public int drawingType = GLES20.GL_TRIANGLES;
    private BufferHelper buffer;

    ShapeBackup backup = new ShapeBackup();

    public void create(float r, float x, float y, Texture texture) {
        radius = r;
        centerX = x;
        centerY = y;
        this.texture = texture;
        uvs = texture.getUvsCircle();
        create();
    }

    public void create(float r, float x, float y, Animation animation) {
        radius = r;
        centerX = x;
        centerY = y;
        this.animation = animation;
        uvs = animation.texture().getUvsCircle();
        create();
    }

    public void create(float r, float x, float y, float[] color) {
        radius = r;
        centerX = x;
        centerY = y;
        this.color = color;
        create();
    }


    private void create() {
        vertices = new float[1083];
        indices = new short[(vertices.length / 3)];
        exist = true;

        vertices[0] = centerX;
        vertices[1] = centerY;
        vertices[2] = 0.0f;
        for (int i = 3; i < vertices.length; i += 3) {
            vertices[i] = (float) ((Math.cos(i * Math.PI / (vertices.length/6)) * radius) + centerX);
            vertices[i + 1] = (float) ((Math.sin(i * Math.PI / (vertices.length/6)) * radius) + centerY);
            vertices[i + 2] = 0.0f;
        }

        int i=0;
        for (int e = 0; e < indices.length-1; e+=3) {
            indices[e] = (short) 0;
            indices[e+1] = (short) (i+1);
            indices[e+2] = (short) (i+2);
            i++;
        }
        indices[indices.length-1] = (short)1;
        backup();
    }

    public void backup(){
        backup.color = color;
        backup.radius = radius;
        backup.centerX = centerX;
        backup.centerY = centerY;
    }

    public void reset(){
        color = backup.color;
        radius = backup.radius;
        centerX = backup.centerX;
        centerY = backup.centerY;
        angleTurnAround = 90;
        radTurn = 0;
        create(radius, centerX, centerY, color);
        change[0] = change[1] = true;
    }

    public float[] getUvs(){
        float[] uvsNew;
        if (texture != null){
            uvsNew = texture.getUvsCircle();
        }else if (animation != null){
            uvsNew = animation.texture().getUvsCircle();
        }else {
            return null;
        }

        if (uvsNew != uvs && uvsNew != null && buffer != null){
            uvs = uvsNew;
            this.buffer.setUvs(uvs);
        }
        return uvs;
    }

    public int getGLTex(){
        if (texture != null){
            return texture.getGLTex();
        }else if (animation != null) {
            return animation.texture().getGLTex();
        }
        return 0;
    }

    public float[] getVertices(){
        if (change[0]){
            translateSprite();
        }

        if (change[1] && buffer != null){
            buffer.setVertices(vertices);
        }
        change[0] = change[1] = false;
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
                    getGLTex(), drawingType, getBuffer());
        }
    }

    private void translateSprite() {
        vertices[0] = centerX;
        vertices[1] = centerY;
        vertices[2] = 0.0f;
        for (int i = 3; i < vertices.length; i += 3) {
            vertices[i] = (float) ((Math.cos(i * Math.PI / (vertices.length/6)) * radius) + centerX);
            vertices[i + 1] = (float) ((Math.sin(i * Math.PI / (vertices.length/6)) * radius) + centerY);
            vertices[i + 2] = 0.0f;
        }

        change[0] = change[1] = true;
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
        centerX = centerX + move;
        change[0] = change[1] = true;
    }

    public void moveY(float move) {
        centerY = centerY + move;
        change[0] = change[1] = true;
    }

    public void setRadius(float rad) {
        radius = radius + rad;
        change[0] = change[1] = true;
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

    private float[] uvCirle(float[] uv){
        float uvHeight = Tools.getDistance(uv[0] * texture.getWidth(), uv[1] * texture.getHeight(),
                uv[2] * texture.getWidth(), uv[3] * texture.getHeight());
        float uvWidth = Tools.getDistance(uv[2] * texture.getWidth(), uv[3] * texture.getHeight(),
                uv[4] * texture.getWidth(), uv[5] * texture.getHeight());
        float x = uv[2]* texture.getWidth() + uvWidth/2;
        float y = uv[3]* texture.getHeight() + uvHeight/2;
        float r = ((uvHeight > uvWidth)?uvWidth:uvHeight)/2;

        uv = new float[361*2];
        uv[0] = x/ texture.getWidth();
        uv[1] = y/ texture.getHeight();
        for (int i = 2; i < uv.length; i += 2) {
            uv[i] = (float) ((Math.cos(-i * Math.PI / (uv.length/6)) * r) + x)/ texture.getWidth();
            uv[i + 1] = (float) ((Math.sin(-i * Math.PI / (uv.length/6)) * r) + y)/ texture.getHeight();
        }
        return uv;
    }

}