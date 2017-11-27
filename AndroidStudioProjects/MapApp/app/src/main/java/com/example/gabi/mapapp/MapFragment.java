package com.example.gabi.mapapp;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
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

    private MapAppLocation mapAppLocation;

    private GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    Marker mMarker;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //mapAppLocation = new MapAppLocation("Dublin", 53.347860, -6.272487);
        mapAppLocation = (MapAppLocation)getArguments().getSerializable("location");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceBundle) {
        super.onViewCreated(view, savedInstanceBundle);

        mMapView = (MapView) mView.findViewById(R.id.mapView);

        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if(mapAppLocation != null) {
            mMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(
                            mapAppLocation.getLat(),
                            mapAppLocation.getLng()
                    ))
                    .title(mapAppLocation.getName())
                    .snippet(
                            mapAppLocation.getLat()
                                    + " "
                                    + mapAppLocation.getLng()
                    )
            );

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(new LatLng(
                            mapAppLocation.getLat(),
                            mapAppLocation.getLng()
                    ))
                    .zoom(6)
                    .bearing(0)
                    .tilt(45)
                    .build();

            mGoogleMap.moveCamera(
                    CameraUpdateFactory
                            .newCameraPosition(cameraPosition)
            );
        }
    }

    public MapAppLocation getMapAppLocation() {
        return mapAppLocation;
    }

    public void setMapAppLocation(MapAppLocation mapAppLocation) {
        this.mapAppLocation = mapAppLocation;
    }

    public void moveMarker(MapAppLocation mapAppLocation) {
        LatLng latLng = new LatLng(mapAppLocation.getLat(), mapAppLocation.getLng());
        mMarker.setPosition(latLng);
        mMarker.setTitle(mapAppLocation.getName());
        mMarker.setSnippet(mapAppLocation.getLat() + ", " + mapAppLocation.getLng());
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(latLng)
                .zoom(6)
                .bearing(0)
                .tilt(45)
                .build();

        mGoogleMap.moveCamera(
                CameraUpdateFactory
                        .newCameraPosition(cameraPosition)
        );
    }
}
