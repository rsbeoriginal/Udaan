package com.rishi.udaan;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> latLngArrayList;
    Polyline polyline;
    double totalDistance;
    TextView tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        tvDistance = findViewById(R.id.tvDistance);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void generatePolyLine() {
        PolylineOptions polylineOptions = new PolylineOptions().clickable(true);
        for(int i=0;i<latLngArrayList.size();i++){
            polylineOptions.add(latLngArrayList.get(i));
            if(i!=0)
            {
                Location startPoint=new Location("locationA");
                startPoint.setLatitude(latLngArrayList.get(i-1).latitude);
                startPoint.setLongitude(latLngArrayList.get(i-1).longitude);

                Location endPoint=new Location("locationB");
                endPoint.setLatitude(latLngArrayList.get(i).latitude);
                endPoint.setLongitude(latLngArrayList.get(i).longitude);

                double distance=startPoint.distanceTo(endPoint);
                totalDistance += distance;
            }
        }
        polyline = mMap.addPolyline(polylineOptions);
        polyline.setTag("Route");
        tvDistance.setText("Total Distance: " + totalDistance + " Meters");
    }

    private void generateData() {
        latLngArrayList = MainActivity.latLngArrayList;



//        latLngArrayList.add(new LatLng(-35.016, 143.321));
//        latLngArrayList.add(new LatLng(-34.747, 145.592));
//        latLngArrayList.add(new LatLng(-34.364, 147.891));
//        latLngArrayList.add( new LatLng(-33.501, 150.217));
//        latLngArrayList.add(new LatLng(-32.306, 149.248));
//        latLngArrayList.add(new LatLng(-32.491, 147.309));
    }
    
    


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        generateData();

        generatePolyLine();
    }
}
