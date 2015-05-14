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

import com.remijouannet.get10.graphic2D.helper.BufferHelper;
import com.remijouannet.get10.graphic2D.helper.GraphicHelper;

import java.lang.reflect.Array;



public class Triangle {
    public float[] vertices;
    public float[] color;
    public short[] indices;
    public float centerX;
    public float centerY;

    public boolean exist = false;
    boolean[] change = new boolean[]{false, false};

    public int drawingType = GLES20.GL_TRIANGLES;

    private BufferHelper buffer;

    public void Create(float[] vertices, float[] color) {

        /* We have create the setVertices of our view.
        setVertices = new float[]
                {  500f, 100f, 0.0f,        // top left
                        500f, 0.0f, 0.0f,   // bottom left
                        600f, 0.0f, 0.0f,   // bottom right
                        600f, 100f, 0.0f,   // top right
                };
        getColor = new float[]{ 255, 0, 0, 1.0f };
        getIndices = new short[] {0, 1, 2, 0, 2, 3}; // loop in the android official tutorial opengles why different order.*/

        this.vertices = vertices;
        this.color = color;
        indices = new short[]{0, 1, 2};
        exist = true;
        centerX = (this.vertices[1] - this.vertices[4]) / 2;
        centerY = (this.vertices[6] - this.vertices[0]) / 2;
    }

    public float[] getVertices(){
        if (change[1]){
            buffer.setVertices(vertices);
        }
        change[0] = change[1] =false;
        return vertices;
    }

    public BufferHelper getBuffer(){
        if (buffer == null && buffer != null){
           this.buffer = new BufferHelper(indices, getVertices());
        }
        getVertices();
        return this.buffer;
    }

    public void draw(float[] mvpMatrix) {
        GraphicHelper.drawSolid(mvpMatrix, getVertices(), indices, color, drawingType, getBuffer());
    }


    public void followY(float fingerY) {
        vertices[1] = fingerY + centerX;
        vertices[4] = fingerY - centerX;
        vertices[7] = fingerY - centerX;
        if (Array.getLength(vertices) > 9)
            vertices[10] = fingerY + centerX;

        change[0] = change[1] = true;
    }

    public void followX(float fingerX) {
        vertices[0] = fingerX - centerY;
        vertices[3] = fingerX - centerY;
        vertices[6] = fingerX + centerY;
        if (Array.getLength(vertices) > 9)
            vertices[9] = fingerX + centerX;

        change[0] = change[1] = true;
    }

    public void follow(float fingerX, float fingerY) {
        followX(fingerX);
        followY(fingerY);
    }

    public void moveX(float move) {
        for (int i = 0, n = Array.getLength(vertices); i < n; i += 3) {
            vertices[i] += move;
        }
        change[0] = change[1] = true;
    }

    public void moveY(float move) {
        for (int i = 1, n = Array.getLength(vertices); i < n; i += 3) {
            vertices[i] += move;
        }
        change[0] = change[1] = true;
    }

}
