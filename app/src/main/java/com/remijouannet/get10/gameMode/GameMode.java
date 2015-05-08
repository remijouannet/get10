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

public class GameMode {
    public int id;
    public int scoreToUnlock;
    public  float[] color;
    public  float bigCircleRadius;
    public  float littleCircleRadius;
    public  float sizeCloseButton;
    public  float sizeBird;
    public  float touchGravity;
    public  float fallGravity;
    public  float speedBird;
    public  float speedColumns;
    public  float speedColumnsWay;
    public  float sizeColumnsWay;
    public  int numberColumns;
    public  float sizeColumns;
    public int gameScene;
    public String background;

    public static GameMode getGameMode0(){
        return new GameMode0();
    }

    public static GameMode getGameMode1(){
        return new GameMode1();
    }

    public static GameMode getGameMode2(){
        return new GameMode2();
    }

    public static GameMode getGameMode3(){
        return new GameMode3();
    }

    public static GameMode getGameMode4(){
        return new GameMode4();
    }

    public static GameMode getGameMode5(){
        return new GameMode5();
    }

    public static GameMode getGameMode6(){
        return new GameMode6();
    }

    public static GameMode getGameMode7(){
        return new GameMode7();
    }

    public static GameMode getGameMode8(){
        return new GameMode8();
    }

    public static GameMode getGameMode9(){
        return new GameMode9();
    }

    public static GameMode getGameMode10(){
        return new GameMode10();
    }


    public static GameMode[] getGameModes(){
        return new GameMode[]{new GameMode0(),
                new GameMode1(), new GameMode2(),
                new GameMode3(), new GameMode4(),
                new GameMode5(),  new GameMode6(),
                new GameMode7(), new GameMode8(),
        new GameMode9(), new GameMode10()};
    }

    public static GameMode getGameMode(int id){
        for(GameMode GM : getGameModes()){
            if (GM.id == id){
                return GM;
            }
        }
        return getGameMode0();
    }
}
