package com.example.gokuniewicz.mapapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    String location = "Dublin";
    double lat, lng;

    private GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    Marker mMarker;
    CameraPosition cameraPosition;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById((R.id.map_fragment));
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        setCoordinates(location);

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(location).snippet(lat + ", " + lng));

        cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(6).bearing(0).tilt(45).build();

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void moveMarker(String location) {
        setCoordinates(location);
        mMarker.setPosition(new LatLng(lat, lng));
        mMarker.setTitle(location);
        mMarker.setSnippet(lat + ", " + lng);
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(6).bearing(0).tilt(45).build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setCoordinates(String location) {
        switch(location) {
            case "Dublin":
                lat = 53.347860;
                lng = -6.272487;
                break;
            case "Kerry":
                lat = 52.264007;
                lng = -9.686990;
                break;
            case "Belfast":
                lat = 54.602755;
                lng = -5.945180;
                break;
            case "Cork":
                lat = 51.892171;
                lng = -8.475068;
                break;
            case "Galway":
                lat = 53.276533;
                lng = -9.069362;
                break;
            case "Wexford":
                lat = 52.336521;
                lng = -6.462855;
                break;
        }
    }
}
