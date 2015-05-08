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


package com.remijouannet.get10.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.remijouannet.get10.graphic2D.Helper.BitmapHelper;
import com.remijouannet.get10.graphic2D.Tools;

public class FirstActivityAnimation extends ImageView {

    private static final String TAG =FirstActivityAnimation.class.getSimpleName();
    private static final int DELAY = Tools.fps(8);

    Paint paint1 = new Paint();
    Paint paint2 = new Paint();

    int x;
    int y;

    int maxmove;
    int minmove;
    int speedmove;
    int move;
    int ycanvas;

    Bitmap[] bitmap = new Bitmap[2];
    Picture[] picture = new Picture[2];

    int frame = 0;
    long lastTime;

    boolean startPlaying = false;

        public FirstActivityAnimation(Context context, AttributeSet attrs)
        {
            super(context, attrs);

            lastTime = System.currentTimeMillis();

            paint1.setColor(Color.WHITE);

            paint2.setAntiAlias(true);
            paint2.setFilterBitmap(true);
            paint2.setDither(true);


            x = FirstActivity.size.x;
            y = FirstActivity.size.y;

            maxmove = (int) (y/6.0f);
            minmove = (int) (y/3.1f);
            ycanvas = maxmove;


            speedmove = y/300;
            move = speedmove;

            Bitmap bitmaptemp = BitmapHelper.getAssetsBitmap(context.getAssets(), "bird/texture_manon_2_1024x1024.png");

            bitmap[0] = BitmapHelper.cropBitmap(bitmaptemp, 0, 0, bitmaptemp.getWidth()/2, bitmaptemp.getHeight());
            bitmap[0] = BitmapHelper.scaleBitmapWidth(bitmap[0], x / 2);

            bitmap[1] = BitmapHelper.cropBitmap(bitmaptemp, bitmaptemp.getWidth() / 2, 0,
                    bitmaptemp.getWidth() / 2, bitmaptemp.getHeight());
            bitmap[1] = BitmapHelper.scaleBitmapWidth(bitmap[1], x/2);

            BitmapHelper.recycle(bitmaptemp);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            canvas.drawRect(0, 0,
                    x, FirstActivity.size.y/1.5f, paint1);


            if (startPlaying){

                if (DELAY < (System.currentTimeMillis() - lastTime)){
                    frame=(frame >= 1)?0:frame+1;
                    lastTime = System.currentTimeMillis();
                }

                move=(ycanvas<=maxmove)?speedmove:move;
                move=(ycanvas>=minmove)?-speedmove:move;
                ycanvas+=move;

                canvas.drawBitmap(bitmap[frame], x/3.5f, ycanvas, paint2);

                //canvas.drawPicture(picture[0]);
                postInvalidate();

            }
        }

        public void playAnimation()
        {
            startPlaying = true;
            postInvalidate();
        }

        public void stopAnimation()
        {
            startPlaying = false;
            postInvalidate();
        }
}
