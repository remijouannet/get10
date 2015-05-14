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


public class GameMode11 extends GameMode{
    public GameMode11(){
        this.id = 11;
        this.scoreToUnlock = 10;
        this.color = new float[]{0.0f, 0.0f, 0.0f,1.0f};
        this.bigCircleRadius = 2.05f;
        this.littleCircleRadius = 10;
        this.sizeCloseButton = 5.2f;
        this.sizeBird = 45;
        this.touchGravity = 150.0f;
        this.fallGravity = -1100.0f;
        this.speedBird = -0.8f;
        this.speedColumns = 0.9f;
        this.speedColumnsWay = 1000.0f;
        this.sizeColumnsWay = 4.2f;
        this.sizeColumns = 35.0f;
        this.numberColumns = 7;
        this.gameScene = 2;
        this.background = "background/background1.png";
        this.bird = "bird/texture_manon_256x256_" + String.valueOf(this.id) + ".png";
    }
}
