package com.remijouannet.get10.GameMode;

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

public class GameMode5  extends GameMode{
    public GameMode5(){
        this.id = 5;
        this.scoreToUnlock = 10;
        //this.color = new float[]{241/255.0f, 196/255.0f, 15/255.0f,1.0f};//Rémi
        this.color = new float[]{197/255.0f, 239/255.0f, 247/255.0f,1.0f};//Manon
        this.bigCircleRadius = 1.9f;
        this.littleCircleRadius = 6.0f;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 34.0f;
        this.touchGravity = 140.0f;
        this.fallGravity = -1500.0f;
        this.speedBird = -1.3f;
        this.speedColumns = 0.3f;
        this.speedColumnsWay = 1400.0f;
        this.sizeColumnsWay = 4.5f;
        this.sizeColumns = 25.0f;
        this.numberColumns = 4;
        this.gameScene = 1;
        this.background = null;
    }
}
