package com.example.gabi.mapapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAP_FRAGMENT = "MapFragment";

    private MapFragment mMapFragment;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private MapAppLocation userMapAppLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        mMapFragment = new MapFragment();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(findViewById(R.id.mapFrameLand) != null) {

                MapAppLocation loc = new MapAppLocation("Cork", 51.892171, -8.475068);

                FragmentTransaction ft = fm.beginTransaction();

                if(mMapFragment.getArguments() == null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("location", loc);
                    mMapFragment.setArguments(bundle);
                } else {
                    mMapFragment.setMapAppLocation(new MapAppLocation("Kerry", 52.264007, -9.686990));
                }

                ft.replace(R.id.mapFrameLand, mMapFragment, "MapFragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        }
    }

}
