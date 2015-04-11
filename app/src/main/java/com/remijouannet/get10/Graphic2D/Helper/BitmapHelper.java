package com.remijouannet.get10.Graphic2D.Helper;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

class BitmapObject{
    Bitmap bitmap;
    String filename;
}

public class BitmapHelper {
    private final static String TAG = BitmapHelper.class.getSimpleName();
    private static ArrayList<BitmapObject> bitmaps = new ArrayList<>();

    private static BitmapObject checkBitmapObject(String filename){
        for (int i = 0; i < bitmaps.size(); i++){
            if (bitmaps.get(i).filename.equals(filename)){
                if (!bitmaps.get(i).bitmap.isRecycled()){
                    return bitmaps.get(i);
                }else {
                    recycle(bitmaps.get(i).bitmap);
                }
            }
        }
        return null;
    }

    public static Bitmap getAssetsBitmap(AssetManager assetManager, String filename, int imageMaxSize) {
        if (checkBitmapObject(filename) != null){
            return checkBitmapObject(filename).bitmap;
        }

        try {
            Bitmap bitmap;
            //Decode image size
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = true;
            InputStream fileStream = assetManager.open(filename);
            BitmapFactory.decodeStream(fileStream, null, bitmapOptions);
            fileStream.close();

            int scale = 1;
            if (bitmapOptions.outWidth > imageMaxSize) {
                scale = (int) Math.ceil((double)bitmapOptions.outWidth/(double)imageMaxSize);
            }

            //Decode with inSampleSize
            bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = scale;
            fileStream = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(fileStream, null, bitmapOptions);
            fileStream.close();

            BitmapObject bitmapObject = new BitmapObject();
            bitmapObject.filename = filename;
            bitmapObject.bitmap = bitmap;
            bitmaps.add(bitmapObject);

            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getAssetsBitmap(AssetManager assetManager, String filename) {
        if (checkBitmapObject(filename) != null){
            return checkBitmapObject(filename).bitmap;
        }

        try {
            InputStream fileStream = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(fileStream, null, null);
            fileStream.close();

            BitmapObject bitmapObject = new BitmapObject();
            bitmapObject.filename = filename;
            bitmapObject.bitmap = bitmap;
            bitmaps.add(bitmapObject);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap mirrorBitmap(Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return newBitmap;
    }


    public static Bitmap cropBitmap(Bitmap bitmap, float ratio){
        float bitmapRatio = (float)bitmap.getWidth()/(float)bitmap.getHeight();
        if (bitmapRatio != ratio){
            float finalHeight = bitmap.getWidth()/ratio;
            return cropBitmap(bitmap, bitmap.getWidth(), (int) finalHeight);
        }else {
            return bitmap;
        }
    }

    public static Bitmap cropBitmap(Bitmap bitmap, int width, int height){
        try {
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0,0, width, height);
            return newBitmap;
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            return bitmap;
        }
    }

    public static Bitmap cropBitmap(Bitmap bitmap,  int x, int y, int width, int height){
        try {
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
            return newBitmap;
        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
            return bitmap;
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height){
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return newBitmap;
    }

    public static Bitmap scaleBitmapWidth(Bitmap bitmap, int width){
        float ratio = bitmap.getWidth()/bitmap.getHeight();
        return scaleBitmap(bitmap, width, (width*bitmap.getHeight())/bitmap.getWidth());
    }

    public static Bitmap scaleBitmapHeight(Bitmap bitmap, int height){
        float ratio = bitmap.getWidth()/bitmap.getHeight();
        return scaleBitmap(bitmap, height, (height*bitmap.getWidth())/bitmap.getHeight());
    }

    public static Bitmap onlyColors(Bitmap bitmap, int[] colors) {
        if (bitmap == null) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        boolean change;
        for (int x = 0; x < pixels.length; ++x) {
            change = true;
            for (int color : colors) {
                if (pixels[x] == color) {
                    change = false;
                }
            }
            if (change) {
                pixels[x] = Color.TRANSPARENT;
            }
        }
        Bitmap newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    public static Bitmap changeColor(Bitmap bitmap, int oldColor, int newColor) {
        if (bitmap == null) {
            return bitmap;
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int x = 0; x < pixels.length; ++x) {
            pixels[x] = (pixels[x] == oldColor) ? newColor : pixels[x];
        }
        Bitmap newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    public static void recycle(Bitmap bitmap){
        for (int i = 0; i < bitmaps.size(); i++){
            if (bitmaps.get(i).bitmap.getGenerationId() == bitmap.getGenerationId()){
                bitmaps.get(i).bitmap.recycle();
                bitmaps.remove(i);
            }
        }
    }

    public static void recycle(){
        for (int i = 0; i < bitmaps.size(); i++){
            recycle(bitmaps.get(i).bitmap);
        }
    }
}
