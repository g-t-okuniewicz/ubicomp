package com.example.gabi.mapapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        Log.d("CREATION", "new MapFragment()");
        mMapFragment = new MapFragment();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(findViewById(R.id.mapFrameLand) != null) {
                Log.d("CREATION", "location");
                Location loc = new Location("Cork", 51.892171, -8.475068);
                Log.d("CREATION", "adding map fragment");
                Log.d("CREATION", "transaction");
                FragmentTransaction ft = fm.beginTransaction();

                if(mMapFragment.getArguments() == null) {
                    Log.d("CREATION", "map fragment arguments null");
                    Log.d("CREATION", "bundle");
                    Bundle bundle = new Bundle();
                    Log.d("CREATION", "add to bundle");
                    bundle.putSerializable("location", loc);
                    mMapFragment.setArguments(bundle);
                } else {
                    Log.d("CREATION", "map fragment arguments not null");
                    mMapFragment.setLocation(new Location("Kerry", 52.264007, -9.686990));
                }
                //mMapFragment.setArguments(bundle);

                //ft.replace(android.R.id.content, mapFragment);
                Log.d("CREATION", "replacing the fragment");
                ft.replace(R.id.mapFrameLand, mMapFragment, "MapFragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}
