package com.remijouannet.get10.Graphic2D;

import android.view.MotionEvent;

import com.remijouannet.get10.Graphic2D.Shape.Shape;
import com.remijouannet.get10.Settings;

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

public class Collision {
    private final static String TAG = Collision.class.getSimpleName();
    private static Shape touch = new Shape();

    public static boolean collision(Shape shape1, Shape shape2) {
        if (shape1.circle.exist && shape2.circle.exist) {
            return circleCircle(shape1.circle.radius, shape1.circle.centerX, shape1.circle.centerY,
                    shape2.circle.radius, shape2.circle.centerX, shape2.circle.centerY);
        }

        if (shape1.circle.exist) {
            return shapeCircle(shape1.circle.radius, shape1.circle.centerX, shape1.circle.centerY,
                    shape2.getVertices(), shape2.getIndices());
        }

        if (shape2.circle.exist) {
            return shapeCircle(shape2.circle.radius, shape2.circle.centerX, shape2.circle.centerY,
                    shape1.getVertices(), shape1.getIndices());
        }

        return triangleTriangle(shape1.getVertices(), shape1.getIndices(),
                shape2.getVertices(), shape2.getIndices());
    }

    public static boolean touchShape(Shape shape, MotionEvent event){
        if (touch.circle.exist){
            touch.circle.follow(event.getX(), event.getY());
        }else {
            touch.circle.create(1, event.getX(), event.getY(), Settings.color);
        }
        return collision(touch, shape);
    }

    private static boolean circleCircle(float r1, float x1, float y1, float r2, float x2, float y2) {
        return Tools.getDistance(x1, y1, x2, y2) < (r1 + r2);
    }

    static float scL1x1;
    static float scL1y1;
    static float scL1x2;
    static float scL1y2;
    private static boolean shapeCircle(float r, float x, float y, float[] vertices, short[] indices) {
        for (int i = 0; indices.length > i; i += 2) {
            scL1x1 = vertices[(indices[i] * 3)];
            scL1y1 = vertices[(indices[i] * 3) + 1];

            try {
                scL1x2 = vertices[(indices[i] * 3) + 3];
                scL1y2 = vertices[(indices[i] * 3) + 4];
            } catch (Exception ex) {
                scL1x2 = vertices[(indices[0] * 3)];
                scL1y2 = vertices[(indices[0] * 3) + 1];
            }

            if (distanceToSegment(scL1x1, scL1y1, scL1x2, scL1y2, x, y) <= r) {
                return true;
            }
        }

        return checkIfInside(vertices, indices, x, y);
    }


    private static boolean triangleTriangle(float[] vertices0, short[] indices0, float[] vertices1, short[] indices1) {
        for (int i = 0; vertices0.length > i; i += 3) {
            if (checkIfInside(vertices1, indices1, vertices0[i], vertices0[i + 1])){
                return true;
            }
        }
        for (int i = 0; vertices1.length > i; i += 3) {
            if (checkIfInside(vertices0, indices0, vertices1[i], vertices1[i + 1])){
                return true;
            }
        }
        return false;
    }


    static float ciiX1;
    static float ciiY1;
    static float ciiX2;
    static float ciiY2;
    static float ciiX3;
    static float ciiY3;
    static float ciiDenominator;
    static float ciiA;
    static float ciiB;
    static float ciiC;
    private static boolean checkIfInside(float[] vertices, short[] indices, float x, float y){
        for (int i = 0; indices.length > i; i += 3) {
            ciiX1 = indiceToPoint(vertices, indices[i])[0];
            ciiY1 = indiceToPoint(vertices, indices[i])[1];

            ciiX2 = indiceToPoint(vertices, indices[i + 1])[0];
            ciiY2 = indiceToPoint(vertices, indices[i + 1])[1];

            ciiX3 = indiceToPoint(vertices, indices[i + 2])[0];
            ciiY3 = indiceToPoint(vertices, indices[i + 2])[1];

            ciiDenominator = ((ciiY2 - ciiY3)*(ciiX1 - ciiX3)
                    + (ciiX3 - ciiX2)*(ciiY1 - ciiY3));
            ciiA = ((ciiY2 - ciiY3)*(x - ciiX3)
                    + (ciiX3 - ciiX2)*(y - ciiY3)) / ciiDenominator;
            ciiB = ((ciiY3 - ciiY1)*(x - ciiX3)
                    + (ciiX1 - ciiX3)*(y - ciiY3)) / ciiDenominator;
            ciiC = 1 - ciiA - ciiB;
            if(0 <= ciiA && ciiA <= 1 && 0 <= ciiB && ciiB <= 1
                    && 0 <= ciiC && ciiC <= 1){
                return true;
            }
        }
        return false;
    }

    static float[] resultIndiceToPoint = new float[2];
    private static float[] indiceToPoint(float[] vertices, int indices){
        int e =0;
        for (int i = 0; vertices.length > i; i += 3) {
            if (e == indices) {
                resultIndiceToPoint[0] = vertices[i];
                resultIndiceToPoint[1] = vertices[i+1];
                return resultIndiceToPoint;
            }
            e++;
        }
        resultIndiceToPoint[0] = 0;
        resultIndiceToPoint[1] = 0;
        return resultIndiceToPoint;
    }

    static float dtsSx;
    static float dtsSy;
    static float dtsUx;
    static float dtsUy;
    static float dtsDp;
    static float dtsSn2;
    static double dtsAh2;
    static float dtsUn2;
    private static double distanceToSegment(float sX1, float sY1, float sX2, float sY2, float px, float py) {

        if (sX1 == sX2 && sY1 == sY2) return distance(sX1, sY1, px, py);

        dtsSx = sX2 - sX1;
        dtsSy = sY2 - sY1;
        dtsUx = px - sX1;
        dtsUy = py - sY1;
        dtsDp = dtsSx * dtsUx + dtsSy * dtsUy;

        if (dtsDp < 0) return distance(sX1, sY1, px, py);

        dtsSn2 = dtsSx * dtsSx + dtsSy * dtsSy;

        if (dtsDp > dtsSn2) return distance(sX2, sY2, px, py);

        dtsAh2 = dtsDp * dtsDp / dtsSn2;
        dtsUn2 = dtsUx * dtsUx + dtsUy * dtsUy;

        return Math.sqrt(dtsUn2 - dtsAh2);
    }

    private static boolean isBetween(float a, float b, float c) {
        return b > a ? c > a && c < b : c > b && c < a;
    }

    public static boolean linesIntersect(double x1, double y1,
                                         double x2, double y2,
                                         double x3, double y3,
                                         double x4, double y4) {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    static double relativeCCWccw;
    public static int relativeCCW(double x1, double y1,
                                  double x2, double y2,
                                  double px, double py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        relativeCCWccw = px * y2 - py * x2;
        if (relativeCCWccw == 0.0) {
            // The point is colinear, classify based on which side of
            // the segment the point falls on.  We can calculate a
            // relative value using the projection of px,py onto the
            // segment - a negative value indicates the point projects
            // outside of the segment in the direction of the particular
            // endpoint used as the origin for the projection.
            relativeCCWccw = px * x2 + py * y2;
            if (relativeCCWccw > 0.0) {
                // Reverse the projection to be relative to the original x2,y2
                // x2 and y2 are simply negated.
                // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                //    from them (based on the original values)
                // Since we really want to get a positive answer when the
                //    point is "beyond (x2,y2)", then we want to calculate
                //    the inverse anyway - thus we leave x2 & y2 negated.
                px -= x2;
                py -= y2;
                relativeCCWccw = px * x2 + py * y2;
                if (relativeCCWccw < 0.0) {
                    relativeCCWccw = 0.0;
                }
            }
        }
        return (relativeCCWccw < 0.0) ? -1 : ((relativeCCWccw > 0.0) ? 1 : 0);
    }

    public static float distance(float P1X, float P1Y, float P2X, float P2Y) {
        return (float) Math.sqrt((P2X - P1X) * (P2X - P1X) + (P2Y - P1Y) * (P2Y - P1Y));
    }

}
