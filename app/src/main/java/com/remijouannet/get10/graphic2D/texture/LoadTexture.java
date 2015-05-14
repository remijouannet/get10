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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.remijouannet.get10.graphic2D.helper.BitmapHelper;
import com.remijouannet.get10.graphic2D.GLRenderer;

public class LoadTexture {
    private static final String TAG = LoadTexture.class.getSimpleName();
    public static int numberTexture =0;
    private Texture texture = null;
    private float[] uvs;
    private int GLTex;
    private int textureWidth;
    private int textureHeight;
    public Bitmap bitmapTexture = null;
    public int[] textureNames = new int[1];
    boolean replace = false;
    private GLRenderer glRenderer;

    public LoadTexture(GLRenderer glRenderer){
        this.glRenderer = glRenderer;
        numberTexture +=1;
        GLTex = GLES20.GL_TEXTURE0+ numberTexture;

    }

    public Texture texture(){
        return texture;
    }

    public void createTexture(Bitmap bitmap, float[] uvCoord){
        bitmapTexture = bitmap;
        textureHeight = bitmap.getHeight();
        textureWidth = bitmap.getWidth();

        // create our UV coordinates.
        uvs = uvCoord;

        texture = new Texture(uvs, GLTex, textureWidth, textureHeight);

        // Generate Textures, if more needed, alter these numbers.
        if (!replace){
            GLES20.glGenTextures(1, textureNames, 0);
        }
        //Log.d(TAG, String.valueOf(textureNames[0]) + ";" + numberTexture + ";" +  bitmap.getHeight() + ";" + bitmap.getWidth());
        // Bind texture to texturename
        GLES20.glActiveTexture(GLTex);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureNames[0]);
        // Set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_NEAREST);
        // Set wrapping mode
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        // Load the bitmap into the bound texture.
        if (replace){
            GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0 ,0 ,bitmap);
            //Log.d(TAG, "texSubImage2D");
        }else {
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            //Log.d(TAG, "texImage2D");
        }
        replace = true;
        
    }

    public void png(AssetManager assetManager, float[] uvCoord, String filename) {
        createTexture(BitmapHelper.getAssetsBitmap(assetManager, filename), uvCoord);
    }

    public void png(AssetManager assetManager, String filename) {
            createTexture(BitmapHelper.getAssetsBitmap(assetManager, filename), new float[]{
                    0.0f, 1.0f,
                    0.0f, 0.0f,
                    1.0f, 0.0f,
                    1.0f, 1.0f});
    }

    public void bitmap(float[] uvCoord, Bitmap bitmap) {
        createTexture(bitmap, uvCoord);
    }

    public void bitmap(Bitmap bitmap) {
        createTexture(bitmap, new float[]{
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f});
    }

    public void recycle(){
        try {
            BitmapHelper.recycle(bitmapTexture);
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
        }
    }

    public static void clean(){
        /*
        if (numberTexture != 0){
            int[] textureIds = new int[numberTexture];
            int i =0;
            while (i == numberTexture) {
                textureIds[i] = i;
                i++;
            }
            GLES20.glDeleteTextures(numberTexture, textureIds, 0);
         }*/
        numberTexture = 0;
    }
}
