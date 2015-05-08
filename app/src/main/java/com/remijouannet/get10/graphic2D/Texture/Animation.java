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


package com.remijouannet.get10.graphic2D.Texture;

import android.graphics.Bitmap;

import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.Tools;


public class Animation {
    private int loop = 0;
    public LoadTexture[] textures = null;
    public TextureRegion[] textureRegions = null;
    GLRenderer glRenderer;

    int frame = 0;
    int fps = 0;
    int frameLimit = Tools.fps(8);

    public Animation(int framelimit, GLRenderer glRenderer, float[] uvCoord, String[] filenames) {
        loop = filenames.length;
        this.glRenderer = glRenderer;
        frameLimit = framelimit;
        textures = new LoadTexture[loop];
        for (int i = 0; i < filenames.length;i++){
            textures[i] = new LoadTexture(glRenderer);
            textures[i].png(this.glRenderer.context.getAssets(), uvCoord, filenames[i]);
            //textures[i].recycle();
        }
    }

    public Animation(int frameLimit, GLRenderer glRenderer, float[] uvCoord, Bitmap[] bitmaps) {
        loop = bitmaps.length;
        this.glRenderer = glRenderer;
        this.frameLimit = frameLimit;
        textures = new LoadTexture[loop];
        for (int i = 0; i < bitmaps.length;i++){
            textures[i] = new LoadTexture(glRenderer);
            textures[i].bitmap(uvCoord, bitmaps[i]);
            //textures[i].recycle();
        }
    }

    public Animation(int frameLimit, GLRenderer glRenderer, float[] uvCoord, LoadTexture[] textures) {
        loop = textures.length;
        this.glRenderer = glRenderer;
        this.frameLimit = frameLimit;
        this.textures = textures;
    }

    public Animation(int frameLimit, GLRenderer glRenderer, TextureRegion[] textures) {
        loop = textures.length;
        textureRegions = textures;
        this.glRenderer = glRenderer;
        this.frameLimit = frameLimit;
    }

    public Texture texture(){
        if (fps > frameLimit) {
            frame = (frame == loop -1)?0: frame +1;
            fps = 0;
        }
        fps += glRenderer.deltaTime;
        if (textureRegions != null){
            return textureRegions[frame].texture();
        }else if (textures != null){
            return textures[frame].texture();
        }else {
            return null;
        }
    }
}
