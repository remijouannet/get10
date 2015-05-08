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

package com.remijouannet.get10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.remijouannet.get10.graphic2D.GLRenderer;


public class GameSurfaceView extends GLSurfaceView {
    private static final String TAG = GameSurfaceView.class.getSimpleName();
    private final GLRenderer glRenderer;


    public GameSurfaceView(Context context) {
        super(context);
        setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        // create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        glRenderer = new GLRenderer(context, this);
        setRenderer(glRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onPause() {
        glRenderer.onPause();
        super.destroyDrawingCache();
        super.onPause();
    }
    @Override
    public void onResume() {
        glRenderer.onResume();
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        glRenderer.touchSimpleTap(event);
        return true;
    }
}