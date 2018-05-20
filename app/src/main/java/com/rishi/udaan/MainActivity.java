package com.rishi.udaan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView tvLocation;
    Button btToggle,btShowRoute,btReset;
    String points= "";

    private LocationManager locationManager;

    static public ArrayList<LatLng> latLngArrayList = new ArrayList<>();

    private String provider;


    Boolean startTrack=false;

//      Dummy Data
//    private void generateData() {
//
//        latLngArrayList.add(new LatLng(-35.016, 143.321));
//        latLngArrayList.add(new LatLng(-34.747, 145.592));
//        latLngArrayList.add(new LatLng(-34.364, 147.891));
//        latLngArrayList.add( new LatLng(-33.501, 150.217));
//        latLngArrayList.add(new LatLng(-32.306, 149.248));
//        latLngArrayList.add(new LatLng(-32.491, 147.309));
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocation = findViewById(R.id.tvLocation);
        btToggle = findViewById(R.id.btToggle);
        btShowRoute = findViewById(R.id.btShowRoute);

//        generateData();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }



        btShowRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity( new Intent(MainActivity.this,MapsActivity.class));
            }
        });


        btToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startTrack){
                    startTrack = false;
                    btToggle.setText("Start Track");
                }else{
                    startTrack = true;
                    btToggle.setText("Stop Track");
                }
            }
        });



    }

    @Override
    public void onLocationChanged(Location location) {
        if(startTrack) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            latLngArrayList.add(new LatLng(latitude, longitude));

            points += latLngArrayList.size() + "->   Longitude:" + Double.toString(longitude) + "\tLatitude:" + Double.toString(latitude) + "\n";
            tvLocation.setText(points);
        }else {
            Toast.makeText(this, "Location changed. Not Track", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
}

