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

import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.helper.BufferHelper;
import com.remijouannet.get10.graphic2D.helper.GraphicHelper;

import java.util.ArrayList;


public class SpriteBatch {
    // Program variables
    private final static String TAG = SpriteBatch.class.getSimpleName();
    private static final int COORDS_PER_VERTEX = 3;
    private static int numIndices = 0;
    private static float[] vertices;
    private static short[] indices;
    private static float[] uvs;
    private static float[] mvpMatrix = null;
    private static float[] color = null;
    private static int drawingType = 0;
    private static int GLTex = 0;
    private static ArrayList shapes = new ArrayList();

    private static boolean solid = false;
    private static boolean texture = false;

    private static BufferHelper batchBuffer = new BufferHelper(new short[86], new float[256], new float[256]);

    public static void begin() {
        numIndices = 0;
        mvpMatrix = null;
        color = null;
        GLTex = 0;
        solid = false;
        texture = false;
        vertices = new float[0];
        indices = null;
        uvs = null;
        drawingType = 0;
        shapes.clear();
    }

    public static void end() {
        if (vertices.length != 0) {
            if (solid){
                batchBuffer.setVertices(vertices);
                batchBuffer.setIndices(indices);
                GraphicHelper.drawSolid(mvpMatrix, vertices,
                        indices, color, drawingType, batchBuffer);
            }else if (texture){
                batchBuffer.setVertices(vertices);
                batchBuffer.setIndices(indices);
                batchBuffer.setUvs(uvs);
                GraphicHelper.drawTexture(mvpMatrix, vertices, indices,
                        uvs, GLTex, drawingType, batchBuffer);
            }
        }else {
           Shape[] finalShapes = new Shape[shapes.size()];
            for (int i = 0; i < finalShapes.length; i++){
                finalShapes[i] = (Shape) shapes.get(i);
            }
            addShape(mvpMatrix, finalShapes);
            end();
        }
    }

    static float[] color_;
    static float[] uvs_;
    static float[] vertices_;
    static short[] indices_;
    static int GLTex_;
    static int drawingType_;
    public static void addShape(float[] mvpMatrix, Shape[] shapes){
        vertices = new float[numberVertices(shapes)];
        indices = new short[numberIndices(shapes)];
        uvs = new float[numberUvs(shapes)];

        int w = 0;
        int x = 0;
        int z = 0;
        for (int i =0; i < shapes.length; i++){
            if (shapes[i] == null){
                return;
            }
            color_ = shapes[i].getColor();
            uvs_ = shapes[i].getUvs();
            vertices_ = shapes[i].getVertices();
            indices_ = shapes[i].getIndices();
            GLTex_ = shapes[i].getGLTex();
            drawingType_ = shapes[i].drawingType();

            /*if ((solid && getColor != color_) ||
                    (texture && getGLTex != GLTex_)){
                end();
            }*/

            if (color_ != null){
                color = color_;
                solid = true;
                texture = false;
            }else if (uvs_ != null){
                for (int e = 0; e < uvs_.length; e++) {
                    uvs[w] = uvs_[e];
                    w++;
                }
                GLTex = GLTex_;
                solid = false;
                texture = true;
            }

            for (int e = 0; e < vertices_.length; e++) {
                vertices[x] = vertices_[e];
                x++;
            }
            for (int e = 0; e < indices_.length; e++) {
                indices[z] = (short) (numIndices + (int) indices_[e]);
                z++;
            }

            numIndices = numIndices + vertices_.length / COORDS_PER_VERTEX;
            SpriteBatch.mvpMatrix = mvpMatrix;
            drawingType = drawingType_;
        }
    }

    public static void addShape(float[] mvpMatrix, Shape shape){
        shapes.add(shape);
        SpriteBatch.mvpMatrix = mvpMatrix;
    }

    static int num = 0;
    private static int numberVertices(Shape[] shapes){
        num =0;
        for (Shape shape : shapes){
            if (shape == null){
                return num;
            }
            num += shape.getVertices().length;
        }
        return num;
    }

    private static int numberIndices(Shape[] shapes){
        num =0;
        for (Shape shape : shapes){
            if (shape == null){
                return num;
            }
            num += shape.getIndices().length;
        }
        return num;
    }

    private static int numberUvs(Shape[] shapes){
        num =0;
        for (Shape shape : shapes){
            if (shape == null || shape.getUvs() == null){
                return num;
            }
            num += shape.getUvs().length;
        }
        return num;
    }

}



