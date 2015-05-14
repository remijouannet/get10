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
import com.remijouannet.get10.graphic2D.texture.Animation;
import com.remijouannet.get10.graphic2D.texture.Texture;


public class CustomPolygon {
    private static final String TAG = CustomPolygon.class.getSimpleName();
    public float[] vertices;
    public short[] indices;
    public float[] uvs;

    Texture texture = null;
    Animation animation = null;
    public float[] color = null;

    public boolean exist = false;
    boolean[] change = new boolean[]{false, false};

    public int drawingType = GLES20.GL_TRIANGLES;
    private BufferHelper buffer;
    ShapeBackup backup = new ShapeBackup();

    public void create(float[] vertices, short[] indices, float[] color) {
        this.vertices = vertices;
        this.color = color;
        this.indices = indices;
        exist = true;
    }

    public void create(float[] vertices, short[] indices, Texture texture) {
        this.texture = texture;
        this.vertices = vertices;
        this.indices = indices;
        uvs = texture.getUvs();
        exist = true;
    }

    public void create(float[] vertices, short[] indices, Animation animation) {
        this.animation = animation;
        this.vertices = vertices;
        this.indices = indices;
        exist = true;
        uvs = animation.texture().getUvs();

    }

    public void backup(){
        backup.vertices = vertices;
        backup.color = color;
    }

    public void reset(){
        vertices = backup.vertices;
        color = backup.color;
        create(vertices, indices, color);
        change[0] = change[1] = true;
    }

    public BufferHelper getBuffer(){
        if (buffer == null){
            this.buffer = new BufferHelper(indices, getVertices());
        }
        getVertices();
        return this.buffer;
    }

    public float[] getVertices(){
        if (change[1] && buffer != null){
            buffer.setVertices(vertices);
        }
        change[0] = change[1] = false;
        return vertices;
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

    public int getGLTex(){
        if (texture != null){
            return texture.getGLTex();
        }else if (animation != null) {
            return animation.texture().getGLTex();
        }
        return 0;
    }

    public void draw(float[] mvpMatrix) {
        GraphicHelper.drawSolid(mvpMatrix, getVertices(), indices, color, drawingType, getBuffer());
    }

    public void setVertices(float[] vertices){
        this.vertices = vertices;
        change[0] = change[1] = true;
    }
}
