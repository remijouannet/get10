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

package com.remijouannet.get10.graphic2D.texture;



public class TextureRegion {
    private static final String TAG = TextureRegion.class.getSimpleName();
    private float uvs[];
    private int GLTex;
    private int textureWidth;
    private int textureHeight;
    private Texture texture = null;

    public TextureRegion(LoadTexture loadTexture, float[] uvCoord){
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = uvCoord;
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public TextureRegion(LoadTexture loadTexture, float x, float y, float width, float height)  {
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = new float[]{
                x / textureWidth, (y / textureHeight + ( height / textureHeight)),
                x / textureWidth, y / textureHeight,
                (x / textureWidth + ( width / textureWidth)) , y / textureHeight,
                (x / textureWidth + ( width / textureWidth)) ,(y / textureHeight + ( height / textureHeight))

        };
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public TextureRegion(LoadTexture loadTexture, float x, float y, float size)  {
        this.GLTex = loadTexture.texture().getGLTex();
        this.textureHeight = loadTexture.texture().getHeight();
        this.textureWidth = loadTexture.texture().getWidth();
        this.uvs = new float[]{
                (x - size/2)/ textureWidth, (y + size/2)/ textureHeight,
                (x - size/2)/ textureWidth, (y - size/2)/ textureHeight,
                (x + size/2)/ textureWidth,(y - size/2)/ textureHeight,
                (x + size/2)/ textureWidth,(y + size/2)/ textureHeight
        };
        this.texture = new Texture(this.uvs, this.GLTex, this.textureWidth, this.textureHeight);
    }

    public Texture texture(){
        return this.texture;
    }
}
