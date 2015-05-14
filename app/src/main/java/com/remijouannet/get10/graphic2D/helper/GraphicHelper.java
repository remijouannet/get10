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


package com.remijouannet.get10.graphic2D.helper;

import android.opengl.GLES20;

import com.remijouannet.get10.graphic2D.shape.Shape;
import com.remijouannet.get10.graphic2D.GLRenderer;
import com.remijouannet.get10.graphic2D.Tools;


public class GraphicHelper {

    // Program variables
    private final static String TAG = GraphicHelper.class.getSimpleName();
    public static int shaderProgram = -1;
    private static final int COORDS_PER_VERTEX = 3;
    private static int vertexShader;
    private static int fragmentShader;

    private static String vertexShaderString;
    private static String fragmentShaderString;
    //////////////////////////////////////////////////////
    private static int positionHandle;
    private static int colorHandle;
    private static int mvpMatrixHandle;
    private static int fragColor;
    private static int texCoordLoc;
    private static int samplerLoc;
    private static int colorOld;
    private static int colorNew;

    public static int loadShader(int type, String shaderCode) {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        // return the shader
        return shader;
    }

    public static void createPrograms(String fileVs, String fileFs) {
        try {
            vertexShaderString = Tools.convertStreamToString(
                    GLRenderer.context.getAssets().open(fileVs));
            fragmentShaderString = Tools.convertStreamToString(
                    GLRenderer.context.getAssets().open(fileFs));
        } catch (Exception e) {
            e.printStackTrace();
        }


        // prepare shaders and OpenGL program SOLID
        vertexShader = loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderString);
        fragmentShader = loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderString);

        shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(shaderProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(shaderProgram);                  // create OpenGL program executables

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        //Vertex Shader
        positionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        texCoordLoc = GLES20.glGetAttribLocation(shaderProgram, "a_texCoord");
        //Fragment Shader
        samplerLoc = GLES20.glGetUniformLocation(shaderProgram, "s_texture");
        colorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");
        fragColor = GLES20.glGetUniformLocation(shaderProgram, "FragColor");
        colorOld = GLES20.glGetUniformLocation(shaderProgram, "colorOld");
        colorNew = GLES20.glGetUniformLocation(shaderProgram, "colorNew");
    }

    public static void clean(){
        if (shaderProgram != -1 ){
            GLES20.glDeleteProgram(shaderProgram);
        }
        GLES20.glDisable(GLES20.GL_BLEND);
    }

    public static void drawSolid(float[] mvpMatrix, Shape shape, BufferHelper buffer){
        drawSolid(mvpMatrix, shape.getVertices(), shape.getIndices(), shape.getColor(), shape.drawingType(), buffer);
    }

    public static void drawSolid(float[] mvpMatrix, float[] vertices, short[] indices,
                                 float[] color, int drawingType, BufferHelper buffer){
        GLES20.glUseProgram(shaderProgram);
        drawCommon(mvpMatrix, buffer, 0);
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawElements(drawingType, indices.length,
                GLES20.GL_UNSIGNED_SHORT, buffer.indicesBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    public static void drawTexture(float[] mvpMatrix, Shape shape, BufferHelper buffer){
        drawTexture(mvpMatrix, shape.getVertices(), shape.getIndices(),
                shape.getUvs(), shape.getGLTex(), shape.drawingType(), buffer);
    }
    public static void drawTexture(float[] mvpMatrix, float[] vertices, short[] indices,
                                   float[] uvs, int GLTex, int drawingType, BufferHelper buffer){

        GLES20.glUseProgram(shaderProgram);
        drawCommon(mvpMatrix, buffer, 1);
        GLES20.glEnableVertexAttribArray(texCoordLoc);
        GLES20.glVertexAttribPointer(texCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, buffer.uvsBuffer);
        GLES20.glUniform1i(samplerLoc, GLTex - GLES20.GL_TEXTURE0);
        GLES20.glDrawElements(drawingType, indices.length,
                GLES20.GL_UNSIGNED_SHORT, buffer.indicesBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordLoc);
    }

    public static void drawTexture(float[] mvpMatrix, Shape shape, BufferHelper buffer, float[] colorOld, float[] colorNew){
        drawTexture(mvpMatrix, shape.getVertices(), shape.getIndices(),
                shape.getUvs(), shape.getGLTex(),
                shape.drawingType(), buffer, colorOld, colorNew);
    }

    public static void drawTexture(float[] mvpMatrix, float[] vertices, short[] indices,
                                   float[] uvs, int GLTex, int drawingType, BufferHelper buffer, float[] colorOld, float[] colorNew){

        GLES20.glUseProgram(shaderProgram);
        GLES20.glUniform4fv(GraphicHelper.colorOld, 1, colorOld, 0);
        GLES20.glUniform4fv(GraphicHelper.colorNew, 1, colorNew, 0);
        drawCommon(mvpMatrix, buffer, 2);
        GLES20.glEnableVertexAttribArray(texCoordLoc);
        GLES20.glVertexAttribPointer(texCoordLoc, 2, GLES20.GL_FLOAT,
                false,
                0, buffer.uvsBuffer);
        GLES20.glUniform1i(samplerLoc, GLTex - GLES20.GL_TEXTURE0);
        GLES20.glDrawElements(drawingType, indices.length,
                GLES20.GL_UNSIGNED_SHORT, buffer.indicesBuffer);
        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(texCoordLoc);
    }

    public static void drawCommon(float[] mvpMatrix, BufferHelper buffer, int fragColor){
        GLES20.glUniform1i(GraphicHelper.fragColor, fragColor);
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(
                positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, buffer.verticesBuffer);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
    }
}