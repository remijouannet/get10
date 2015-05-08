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

public class GameMode9 extends GameMode{
    public GameMode9(){
        this.id = 9;
        this.scoreToUnlock = 10;
        //this.color = new float[]{155.0f/255.0f, 89/255.0f, 182/255.0f,1.0f};//Rémi
        this.color = new float[]{244/255.0f, 179/255.0f, 80/255.0f,1.0f};//Manon(253, 227, 167)
        this.bigCircleRadius = 2.05f;
        this.littleCircleRadius = 10;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 45;
        this.touchGravity = 150.0f;
        this.fallGravity = -1500.0f;
        this.speedBird = -1.0f;
        this.speedColumns = -2.5f;
        this.speedColumnsWay = 1200.0f;
        this.sizeColumnsWay = 7.0f;
        this.sizeColumns = 19.0f;
        this.numberColumns = 1;
        this.gameScene = 1;
        this.background = null;
    }
}
