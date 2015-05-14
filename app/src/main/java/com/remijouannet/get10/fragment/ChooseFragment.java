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


package com.remijouannet.get10.fragment;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.remijouannet.get10.activity.FirstActivity;
import com.remijouannet.get10.activity.GameActivity;
import com.remijouannet.get10.Data;
import com.remijouannet.get10.gameMode.GameMode;
import com.remijouannet.get10.R;
import com.remijouannet.get10.Settings;
import com.remijouannet.get10.graphic2D.Tools;


public class ChooseFragment extends ListFragment {
    private static final String TAG = ChooseFragment.class.getSimpleName();
    public static final String TAG_FRAGMENT = "CHOOSEFRAGMENT";
    private Context context;
    private ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose, container, false);
        context = rootView.getContext();

        rootView.setBackgroundColor(Tools.getIntFromColor(Settings.gameOverColor));

        GameMode[] List_GM = GameMode.getGameModes();
        new Data(context);
        String[] values = new String[List_GM.length];

        for (int i =0;i< List_GM.length;i++){
            values[i] = Integer.toString(i);
        }

        ArrayAdapter<String> adapter = new ChooseAdapter(context, values, List_GM);
        setListAdapter(adapter);


/*
        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    listview.setVisibility(View.INVISIBLE);
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    listview.setVisibility(View.VISIBLE);
                return false;
            }
        });
*/
        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String  itemValue    = (String) l.getItemAtPosition(position);
        launchGame(Integer.valueOf(itemValue));
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    private void launchGame(int id){
        if (Data.getUnlock(id) || !Data.getConfig(FirstActivity.context.getString(R.string.key_unlock)) ){
            new Settings(FirstActivity.size.x, FirstActivity.size.y, GameMode.getGameMode(id));
            Intent i = new Intent(context, GameActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        }
    }
}
