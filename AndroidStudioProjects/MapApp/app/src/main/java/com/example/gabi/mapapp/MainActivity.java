package com.example.gabi.mapapp;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAP_FRAGMENT = "MapFragment";

    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("CREATION", "onCreate() is being executed");

        // find the retained map fragment on activity restarts
        FragmentManager fm = getFragmentManager();
        mMapFragment = (MapFragment) fm.findFragmentByTag(TAG_MAP_FRAGMENT);

        // create the map fragment and data the first time
        if(mMapFragment == null) {
            // add the fragment
            Log.d("CREATION", "new MapFragment()");
            mMapFragment = new MapFragment();
            //fm.beginTransaction().add(mMapFragment, TAG_MAP_FRAGMENT).commit();
            //mMapFragment.setLocation(new Location("Dublin", 53.347860, -6.272487));
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(findViewById(R.id.mapFrameLand) != null) {
                /*
                if(savedInstanceState != null) {
                    Log.d("CREATION", "savedInstanceState != null");
                    return;
                }
                */

                Log.d("CREATION", "adding map fragment");
                getFragmentManager().beginTransaction().add(R.id.mapFrameLand, mMapFragment).commit();
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}
