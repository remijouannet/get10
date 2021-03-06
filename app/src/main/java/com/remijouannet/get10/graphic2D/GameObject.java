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

import android.view.MotionEvent;


public interface GameObject {
    public void draw(float[] mtrxProjectionAndView);
    public void draw2(float[] mtrxProjectionAndView);
    public void init();
    public void create();
    public void reset();
    public void gameOver();
    public void touchEvent(MotionEvent event);
    public void move();
}

