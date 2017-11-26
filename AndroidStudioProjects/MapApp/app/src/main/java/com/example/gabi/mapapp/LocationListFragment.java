package com.example.gabi.mapapp;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String[] locationNamesArray = new String[] {
            "My Location",
            "Dublin",
            "Kerry",
            "Belfast",
            "Cork",
            "Galway",
            "Wexford"
    };

    private ListView listView;

    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        listView = (ListView)view.findViewById(R.id.locationListView);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                locationNamesArray
        );

        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String location = listView.getItemAtPosition(i).toString();

        Activity activity = getActivity();
        Toast.makeText(activity, "Clicked: " + location, Toast.LENGTH_SHORT).show();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            Location loc = getLocationFromString(location);
            bundle.putSerializable("location", loc);
            mapFragment.setArguments(bundle);
            //ft.replace(android.R.id.content, mapFragment);
            ft.replace(android.R.id.content, mapFragment, "MapFragment");
            ft.addToBackStack(null);
            ft.commit();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("ONCLICK", "Get fragment by tag");
            MapFragment mapFragment = (MapFragment)activity.getFragmentManager().findFragmentByTag("MapFragment");
            if(mapFragment!= null) {
                Log.d("ONCLICK", "fragment " + mapFragment.toString());
                mapFragment.moveMarker(getLocationFromString(location));
            }
            else {
                Log.d("ONCLICK", "fragment NULL");
            }

            /*FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            Location loc = getLocationFromString(location);
            bundle.putSerializable("location", loc);
            mapFragment.setArguments(bundle);
            //ft.replace(android.R.id.content, mapFragment);
            ft.replace(R.id.mapFrameLand, mapFragment);
            ft.addToBackStack(null);
            ft.commit();*/
        }
    }

    private Location getLocationFromString(String locationName) {
        Location location = null;
        switch(locationName) {
            case "My Location":
                location = new Location("Galway", 53.276533, -9.069362);
                break;
            case "Dublin":
                location = new Location("Dublin", 53.347860, -6.272487);
                break;
            case "Kerry":
                location = new Location("Kerry", 52.264007, -9.686990);
                break;
            case "Belfast":
                location = new Location("Belfast", 54.602755, -5.945180);
                break;
            case "Cork":
                location = new Location("Cork", 51.892171, -8.475068);
                break;
            case "Galway":
                location = new Location("Galway", 53.276533, -9.069362);
                break;
            case "Wexford":
                location = new Location("Wexford", 52.336521, -6.462855);
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
        return location;
    }

    public Location myLocation () {
        return null;
    }

}
