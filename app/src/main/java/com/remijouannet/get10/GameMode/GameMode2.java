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

public class GameMode2 extends GameMode{
    public GameMode2(){
        this.id = 2;
        this.scoreToUnlock = 10;
        //this.color = new float[]{100.0f / 255.0f, 128.0f / 255.0f, 185.0f / 255.0f, 1.0f};//Rémi
        this.color = new float[]{220 / 255.0f, 198 / 255.0f, 224 / 255.0f, 1.0f};//Manon(220, 198, 224)
        this.bigCircleRadius = 2.05f;
        this.littleCircleRadius = 13.0f;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 31.0f;
        this.touchGravity = 150.0f;
        this.fallGravity = -1500.0f;
        this.speedBird = -1.2f;
        this.speedColumns = 0.2f;
        this.speedColumnsWay = 1500.0f;
        this.sizeColumnsWay = 4.3f;
        this.sizeColumns = 26.0f;
        this.numberColumns = 2;
        this.gameScene = 1;
        this.background = null;
    }
}