package com.example.gokuniewicz.mapapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment {

    String[] cityArray = {
            "Dublin",
            "Kerry",
            "Belfast",
            "Cork",
            "Galway",
            "Wexford"
    };

    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        ListView listView = (ListView)view.findViewById(R.id.cityListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                cityArray
        );

        listView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

}
