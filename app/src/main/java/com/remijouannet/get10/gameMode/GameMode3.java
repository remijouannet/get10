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


package com.remijouannet.get10.gameMode;


public class GameMode3  extends GameMode{
    public GameMode3(){
        this.id = 3;
        this.scoreToUnlock = 10;
        //this.color = new float[]{52.0f/255.0f, 152.0f/255.0f, 219.0f/255.0f,1.0f};//Remi
        this.color = new float[]{190/255.0f, 144/255.0f, 212/255.0f,1.0f};//Manon
        this.bigCircleRadius = 2.05f;
        this.littleCircleRadius = 10.0f;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 31.0f;
        this.touchGravity = 170.0f;
        this.fallGravity = -1500.0f;
        this.speedBird = -1.3f;
        this.speedColumns = 0.2f;
        this.speedColumnsWay = 1500.0f;
        this.sizeColumnsWay = 4.3f;
        this.sizeColumns = 26.0f;
        this.numberColumns = 3;
        this.gameScene = 1;
        this.background = null;
        this.bird = "bird/texture_manon_256x256_" + String.valueOf(this.id) + ".png";


    }
}