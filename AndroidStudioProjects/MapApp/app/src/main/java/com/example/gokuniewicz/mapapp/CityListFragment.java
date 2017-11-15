package com.example.gokuniewicz.mapapp;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment implements AdapterView.OnItemClickListener {

    String[] cityArray = {
            "Dublin",
            "Kerry",
            "Belfast",
            "Cork",
            "Galway",
            "Wexford"
    };

    ListView listView;

    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        listView = (ListView)view.findViewById(R.id.cityListView);
        listView.setOnItemClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                cityArray
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

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MapFragment fragment = (MapFragment) activity.getFragmentManager().findFragmentById(R.id.mapFragment);
            Bundle args = new Bundle();
            args.putString("location", location);
            fragment.setArguments(args);
            //fragment.moveMarker(location);
        } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MapFragment fragment = new MapFragment();
            Bundle args = new Bundle();
            args.putString("location", location);
            fragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.city_list_fragment, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
