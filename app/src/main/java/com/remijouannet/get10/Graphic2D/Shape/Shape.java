package com.remijouannet.get10.Graphic2D.Shape;

import com.remijouannet.get10.Graphic2D.Helper.BufferHelper;

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

public class Shape {
    private static final String TAG = Shape.class.getSimpleName();

    public Triangle triangle = new Triangle();
    public Rect rect = new Rect();
    public Circle circle = new Circle();
    public Square square = new Square();
    public CustomPolygon customPolygon = new CustomPolygon();

    public void Draw(float[] mtrxProjectionAndView){
        if (triangle.exist) {
            triangle.draw(mtrxProjectionAndView);
        } else if (rect.exist) {
            rect.draw(mtrxProjectionAndView);
        } else if (circle.exist) {
            circle.draw(mtrxProjectionAndView);
        } else if (square.exist) {
            square.draw(mtrxProjectionAndView);
        } else if (customPolygon.exist) {
            customPolygon.draw(mtrxProjectionAndView);
        }
    }

    public float[] getVertices() {
        if (triangle.exist) {
            return triangle.getVertices();
        } else if (rect.exist) {
            return rect.getVertices();
        } else if (circle.exist) {
            return circle.getVertices();
        } else if (square.exist) {
            return square.getVertices();
        } else if (customPolygon.exist) {
            return customPolygon.getVertices();
        }else {
            return null;
        }
    }

    public short[] getIndices() {
        if (triangle.exist) {
            return triangle.indices;
        } else if (rect.exist) {
            return rect.indices;
        } else if (circle.exist) {
            return circle.indices;
        } else if (square.exist) {
            return square.indices;
        }else if (customPolygon.exist) {
            return customPolygon.indices;
        } else {
            return null;
        }
    }

    public float[] getColor() {
        if (triangle.exist) {
            return triangle.color;
        }  else if (rect.exist) {
            return rect.color;
        } else if (circle.exist) {
            return circle.color;
        } else if (square.exist) {
            return square.color;
        }else if (customPolygon.exist) {
            return customPolygon.color;
        } else {
            return null;
        }
    }

    public int drawingType() {
        if (triangle.exist) {
            return triangle.drawingType;
        }  else if (rect.exist) {
            return rect.drawingType;
        } else if (circle.exist) {
            return circle.drawingType;
        } else if (square.exist) {
            return square.drawingType;
        }else if (customPolygon.exist) {
            return customPolygon.drawingType;
        } else {
            return 0;
        }
    }

    public BufferHelper getBuffer() {
        if (triangle.exist) {
            return triangle.getBuffer();
        }  else if (rect.exist) {
            return rect.getBuffer();
        } else if (circle.exist) {
            return circle.getBuffer();
        } else if (square.exist) {
            return square.getBuffer();
        }else if (customPolygon.exist) {
            return customPolygon.getBuffer();
        } else {
            return null;
        }
    }


    public int getGLTex() {
        if (rect.exist) {
            return rect.getGLTEX();
        }else if (customPolygon.exist){
            return customPolygon.getGLTex();
        } if (circle.exist){
            return circle.getGLTex();
        } else {
            return 0;
        }
    }

    public float[] getUvs() {
        if (rect.exist) {
            return rect.getUvs();
        }else if (customPolygon.exist){
            return customPolygon.getUvs();
        } if (circle.exist){
            return circle.getUvs();
        }else {
            return null;
        }
    }

}
