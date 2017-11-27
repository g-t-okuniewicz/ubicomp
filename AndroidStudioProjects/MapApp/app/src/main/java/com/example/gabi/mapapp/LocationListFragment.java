package com.example.gabi.mapapp;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationListFragment extends Fragment implements AdapterView.OnItemClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private String[] locationNamesArray = new String[]{
            "My Location",
            "Dublin",
            "Kerry",
            "Belfast",
            "Cork",
            "Galway",
            "Wexford"
    };

    private ListView listView;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private MapAppLocation userMapAppLocation;

    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        listView = (ListView) view.findViewById(R.id.locationListView);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                locationNamesArray
        );

        listView.setAdapter(adapter);

        if (mGoogleApiClient == null) {
            Log.d("LOCATION_LIST_ON_CREATE", "google api client null");
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("LOCATION_LIST_ON_CREATE", "google api client: " + mGoogleApiClient.toString());
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void onStart() {
        Log.d("LOCATION_LIST_ON_START", "trying to connect");
        super.onStart();
        mGoogleApiClient.connect();
        Log.d("LOCATION_LIST_ON_START", "google api client connected: " + mGoogleApiClient.isConnected());
    }

    public void onStop() {
        Log.d("LOCATION_LIST_ON_STOP", "trying to disconnect");
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String location = listView.getItemAtPosition(i).toString();

        Activity activity = getActivity();
        Toast.makeText(activity, "Clicked: " + location, Toast.LENGTH_SHORT).show();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            MapAppLocation loc = getLocationFromString(location);
            bundle.putSerializable("location", loc);
            mapFragment.setArguments(bundle);
            //ft.replace(android.R.id.content, mapFragment);
            ft.replace(android.R.id.content, mapFragment, "MapFragment");
            ft.addToBackStack(null);
            ft.commit();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("ONCLICK", "Get fragment by tag");
            MapFragment mapFragment = (MapFragment) activity.getFragmentManager().findFragmentByTag("MapFragment");
            if (mapFragment != null) {
                Log.d("ONCLICK", "fragment " + mapFragment.toString());
                mapFragment.moveMarker(getLocationFromString(location));
            } else {
                Log.d("ONCLICK", "fragment NULL");
            }
        }
    }

    private MapAppLocation getLocationFromString(String locationName) {
        MapAppLocation mapAppLocation = null;
        switch (locationName) {
            case "My Location":
                updateUserMapAppLocation();
                mapAppLocation = userMapAppLocation;
                break;
            case "Dublin":
                mapAppLocation = new MapAppLocation("Dublin", 53.347860, -6.272487);
                break;
            case "Kerry":
                mapAppLocation = new MapAppLocation("Kerry", 52.264007, -9.686990);
                break;
            case "Belfast":
                mapAppLocation = new MapAppLocation("Belfast", 54.602755, -5.945180);
                break;
            case "Cork":
                mapAppLocation = new MapAppLocation("Cork", 51.892171, -8.475068);
                break;
            case "Galway":
                mapAppLocation = new MapAppLocation("Galway", 53.276533, -9.069362);
                break;
            case "Wexford":
                mapAppLocation = new MapAppLocation("Wexford", 52.336521, -6.462855);
                break;
        }

        /*
        Dublin 53.347860 -6.272487
        Kerry 52.264007 -9.686990
        Belfast 54.602755 -5.945180
        Cork 51.892171 -8.475068
        Galway 53.276533 -9.069362
        Wexford 52.336521 -6.462855
*/
        return mapAppLocation;
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE_ASK_PERMISSIONS);
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("ONCONNECTED", "getting user location");
            userMapAppLocation = new MapAppLocation(
                    "Your location",
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude()
            );
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "Connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void updateUserMapAppLocation() {
        if (mLastLocation != null) {
            Log.d("UPDATE_USER_LOCATION", "getting user location");
            userMapAppLocation = new MapAppLocation(
                    "Your location",
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude()
            );
        } else
            Log.d("UPDATE_USER_LOCATION", "last location NULL");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Log.d("ON_REQUEST_PERMISSIONS", "Permission granted");
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
