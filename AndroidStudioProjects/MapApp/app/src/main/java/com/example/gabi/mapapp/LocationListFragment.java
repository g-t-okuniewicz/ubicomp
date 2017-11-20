package com.example.gabi.mapapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    }

}
