package com.remijouannet.get10.graphic2D.Helper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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

public class BufferHelper {
    private final String TAG = BufferHelper.class.getSimpleName();
    public FloatBuffer verticesBuffer;
    public FloatBuffer uvsBuffer;
    public ShortBuffer indicesBuffer;

    public BufferHelper(short[] shortArray, float[] floatArray0, float[] floatArray1){
        indicesBuffer = ShortArray_ToBuffer(shortArray, indicesBuffer);
        verticesBuffer = FloatArrayToBuffer(floatArray0, verticesBuffer);
        uvsBuffer = FloatArrayToBuffer(floatArray1, uvsBuffer);
    }

    public BufferHelper(short[] shortArray, float[] floatArray0){
        indicesBuffer = ShortArray_ToBuffer(shortArray, indicesBuffer);
        verticesBuffer = FloatArrayToBuffer(floatArray0, verticesBuffer);
    }

    public void setVertices(float[] array){
        verticesBuffer.clear();
        verticesBuffer.put(array);
        verticesBuffer.flip();
    }

    public void setUvs(float[] array){
        uvsBuffer.clear();
        uvsBuffer.put(array);
        uvsBuffer.flip();
    }

    public void setIndices(short[] array){
        indicesBuffer.clear();
        indicesBuffer.put(array);
        indicesBuffer.flip();
    }

    public FloatBuffer FloatArrayToBuffer(float[] array, FloatBuffer floatBuffer){
        ByteBuffer bb1 = ByteBuffer.allocateDirect(array.length * 4);
        bb1.order(ByteOrder.nativeOrder());
        floatBuffer = bb1.asFloatBuffer();
        floatBuffer.put(array);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public ShortBuffer ShortArray_ToBuffer(short[] array, ShortBuffer shortBuffer){
        ByteBuffer bb2 = ByteBuffer.allocateDirect(array.length * 2);
        bb2.order(ByteOrder.nativeOrder());
        shortBuffer = bb2.asShortBuffer();
        shortBuffer.put(array);
        shortBuffer.position(0);
        return shortBuffer;
    }

    public void clear(){
        uvsBuffer.clear();
        verticesBuffer.clear();
        indicesBuffer.clear();
    }
}
