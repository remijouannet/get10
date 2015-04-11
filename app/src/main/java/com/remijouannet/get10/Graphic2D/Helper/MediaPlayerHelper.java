package com.remijouannet.get10.Graphic2D.Helper;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
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

class MediaObject{
    MediaPlayer mediaPlayer;
    int idres;
    boolean prepare;
}

public class MediaPlayerHelper implements MediaPlayer.OnPreparedListener{
    private static String TAG = MediaPlayerHelper.class.getSimpleName();
    private static ArrayList<MediaObject> media = new ArrayList<>();
    private static MediaObject mediaObject = null;

    private int idres;

    public MediaPlayerHelper(Context context, int idres){
        if (getMediaPlayer(idres) == null){
            MediaObject mediaObject = new MediaObject();
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(idres);
            MediaPlayer mediaplayer = new MediaPlayer();
            mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaplayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaplayer.setOnPreparedListener(this);
                mediaplayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaObject.mediaPlayer = mediaplayer;
            mediaObject.idres = idres;
            mediaObject.prepare = false;
            this.idres = idres;

            media.add(mediaObject);
        }
    }

    public static void playSound(Context context, int idres) {
        mediaObject = getMediaPlayer(idres);
        if (mediaObject != null && mediaObject.prepare){
            if (mediaObject.mediaPlayer.getCurrentPosition() != 0) {
                mediaObject.mediaPlayer.pause();
                mediaObject.mediaPlayer.seekTo(0);
            }
            mediaObject.mediaPlayer.start();
        }else if (mediaObject == null){
            new MediaPlayerHelper(context, idres);
            playSound(context, idres);
        }
    }

    private static void release(int idres){
        for (int i = 0; i < media.size(); i++){
            if (media.get(i).idres == idres){
                media.get(i).mediaPlayer.release();
                media.remove(i);
            }
        }
    }

    private static void release(){
        for (int i = 0; i < media.size(); i++){
            media.get(i).mediaPlayer.release();
            media.remove(i);
        }
    }

    private static MediaObject getMediaPlayer(int idres){
        for (int i = 0; i < media.size(); i++){
            if (media.get(i).idres == idres){
                return media.get(i);
            }
        }
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        for (int i = 0; i < media.size(); i++){
            if (media.get(i).idres == idres){
                media.get(i).prepare = true;
            }
        }
    }
}
