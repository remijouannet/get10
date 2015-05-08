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

package com.remijouannet.get10.graphic2D;


import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.graphic2D.Helper.GraphicHelper;
import com.remijouannet.get10.graphic2D.Texture.LoadTexture;
import com.remijouannet.get10.scenes.GameWorld;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRenderer implements Renderer {

    private static final String TAG = GLRenderer.class.getSimpleName();

    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    public final float[] mtrxProjectionAndView = new float[16];

    public int screenWidth;
    public int screenHeight;
    public float screenRatio;

    public static Context context;
    public GLSurfaceView glSurfaceView;
    public long lastTime;
    public long deltaTime;
    public long upTime = 0;
    private long eventTime = 51;

    long now;
    long FPSLimit = Tools.fps(60);
    public GameWorld gameWorld;


    public boolean Drawing = false;

    public GLRenderer(Context context, GLSurfaceView glSurfaceView) {
        this.context = context;
        this.glSurfaceView = glSurfaceView;
        this.lastTime = System.currentTimeMillis();
    }


    public void onPause() {
        Drawing = false;
        Tools.sleep(200);
        LoadTexture.clean();
        GraphicHelper.clean();
        System.gc();
    }

    public void onResume() {
        Drawing = true;
        lastTime = System.currentTimeMillis();
    }


    public void Return() {
        onPause();
        Intent i = new Intent(context, FirstActivity.class);
        context.startActivity(i);
    }

    public void processTouchEvent(MotionEvent event) {
        gameWorld.touchEvent(event);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        now = System.currentTimeMillis();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        if (Drawing) {
            try {
                gameWorld.drawmove(mtrxProjectionAndView);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //gameWorld.drawmove(mtrxProjectionAndView);
        }
        deltaTime = System.currentTimeMillis() - now;
        Tools.sleep(FPSLimit - (deltaTime));
        deltaTime = System.currentTimeMillis() - now;
        upTime += deltaTime;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        screenRatio = (float) width / (float) height;

        GLES20.glViewport(0, 0, screenWidth, screenHeight);

        for (int i = 0; i < 16; i++) {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        Matrix.orthoM(mtrxProjection, 0, 0f, screenWidth, 0.0f, screenHeight, 0, 50);
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

        GraphicHelper.createPrograms(SettingsGraphic.fileVs, SettingsGraphic.fileFs);
        gameWorld = new GameWorld(this);
        Drawing = true;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f,0.0f,0.0f, 0.0f);
    }

    public void SetMaxFPS(long Frame) {
        try {
            FPSLimit = 1000 / Frame;
        } catch (Exception ex) {
            FPSLimit = 1000;
        }
    }

    public void touchSimpleTap(MotionEvent event) {
        MotionEvent CustomEvent = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(),
                MotionEvent.ACTION_DOWN, event.getX(), (glSurfaceView.getHeight() - event.getY()), 0);
        try {
            if ((System.currentTimeMillis() - eventTime) > 50) {
                processTouchEvent(CustomEvent);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        } finally {
            eventTime = System.currentTimeMillis();
        }
        if ((System.currentTimeMillis() - eventTime) > 50) {
            processTouchEvent(CustomEvent);
        }
        eventTime = System.currentTimeMillis();
    }

    public void touchFollow(MotionEvent event) {
        MotionEvent CustomEvent = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(),
                MotionEvent.ACTION_DOWN, event.getX(), (glSurfaceView.getHeight() - event.getY()), 0);
        try {
            processTouchEvent(CustomEvent);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}