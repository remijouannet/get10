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


public class GameMode1 extends GameMode{
    public GameMode1(){
        this.id = 1;
        this.scoreToUnlock = 10;
        //this.color = new float[]{52.0f/255.0f, 152.0f/255.0f, 219.0f/255.0f,1.0f};//Rémi
        this.color = new float[]{241/255.0f, 169/255.0f, 160/255.0f,1.0f};//Manon
        this.bigCircleRadius = 2.05f;
        this.littleCircleRadius = 11;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 35;
        this.touchGravity = 180.0f;
        this.fallGravity = -1400.0f;
        this.speedBird = -1.1f;
        this.speedColumns = 0.3f;
        this.speedColumnsWay = 1600.0f;
        this.sizeColumnsWay = 4.0f;
        this.sizeColumns = 30.0f;
        this.numberColumns = 3;
        this.gameScene = 1;
        this.background = null;
        this.bird = "bird/texture_manon_256x256_" + String.valueOf(this.id) + ".png";
    }
}
