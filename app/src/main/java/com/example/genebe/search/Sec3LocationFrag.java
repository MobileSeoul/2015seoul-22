package com.example.genebe.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.genebe.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class Sec3LocationFrag extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    GoogleMap gMap;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Sec3LocationFrag newInstance(int sectionNumber) {
        Sec3LocationFrag fragment = new Sec3LocationFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Sec3LocationFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_location_frag, container, false);

//        gMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//        //MapView mapView = (MapView) rootView.findViewById(R.id.main_search_map);
//
//        LatLng latlngtest = new LatLng(37.492359, 127.013691);
//
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlngtest, 17);
//        gMap.moveCamera(update);
//
//        Marker newmarker = gMap.addMarker(new MarkerOptions().position(latlngtest).title("현재 위치").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
//
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//
//        MapsInitializer.initialize(getActivity().getApplicationContext());
//
//        gMap = mapView.getMap();
//        //gMap.clear();
//
//        LatLng latlngtest = new LatLng(37.492359, 127.013691);
//
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlngtest, 17);
//        gMap.moveCamera(update);
//
//        Marker newmarker = gMap.addMarker(new MarkerOptions().position(latlngtest).title("현재 위치").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));


        return rootView;
    }

}
