package com.example.rightprice;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;


import java.util.HashMap;
import java.util.List;


public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton settingsButton;
    private ImageButton filterButton;
    private Button birdButton;
    private Button limeButton;
    private Button spinButton;
    private Button birdFilter;
    private Button limeFilter;
    private Button spinFilter;
    //slider initialize maxPrice
    private Button bikeFilter;
    private Button scooFilter;
    private LinearLayout servicesLayer;
    private LinearLayout filterOptionsLayer;
    private Button logoutButton;
    private FirebaseAuth mAuth;
    private Button gpsButton;


    private Location currentLocation = new Location("dummyprovider");




    Cache cache;
    Network network;
    RequestQueue requestQueue;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }

        cache = new DiskBasedCache(this.getCacheDir(), 1024*1024);//1MB
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        try {
            //Bird bird = new Bird("ZSofjd'oiohshsdkjdslkdfjngdflkg@ucsd.com",loc);
            final Bird bird = new Bird();

           // bird.setLocation(currentLocation);

            bird.setBirdListener(new Bird.BirdListener() {
                @Override
                public void onObjectReady(String title) {
                    //code to handle object ready
                }

                @Override
                public void onDataLoaded(List birdList) {
                    // Code to handle data loaded from network
                    // Use the data here!

                    if(!bird.getToken().equals("none")){


                        System.out.println("Token assigned Printing request..");
                        System.out.println(bird.getVehicleReq());
                        requestQueue.add(bird.getInitReq());
                        requestQueue.add(bird.getVehicleReq());

                    }
                    else{
                        System.out.println("No token Bird...");
                    }
                    System.out.println(bird.getBirds());
                    System.out.println("############################");
                    System.out.println("############################");
                    System.out.println("############################");
                    System.out.println("############################");
                    System.out.println("############################");
                    System.out.println("###########LAST ONE#########");


                }
            });




        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getLocationPermission();

        settingsButton = (ImageButton) findViewById(R.id.settings_button);
        filterButton = (ImageButton) findViewById(R.id.filter_button);
        birdButton = findViewById(R.id.bird_toggle);
        limeButton = findViewById(R.id.lime_toggle);
        spinButton = findViewById(R.id.spin_toggle);
        //start of button initialization
        /*
        birdFilter = findViewById(R.id.);
        limeFilter = findViewById(R.id.);
        spinFilter = findViewById(R.id.);
        //maxPrice find
        bikeFilter = findViewById(R.id.);
        scooFilter = findViewById(R.id.);
        */
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                // implement logging out.
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Map.this, MainActivity.class));
                finish();

            }
        });
        servicesLayer = (LinearLayout) findViewById(R.id.services_layer);
        servicesLayer.setVisibility(View.INVISIBLE);
        // Shows settings when pressing the Settings Button
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (servicesLayer.getVisibility()==View.VISIBLE) {
                    servicesLayer.setVisibility(View.INVISIBLE);
                } else {
                    servicesLayer.setVisibility(View.VISIBLE);
                }
            }
        });

        //add or delete bird
        birdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle bird login
                FirebaseUser user = mAuth.getCurrentUser();
                String userUID = user.getUid();
                DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("Users").document(userUID);

                HashMap<String, Object> Bird = new HashMap<>();
                Bird.put("birdEmail", userUID + "@ucsd.com");

                userDocRef.collection("Services").document("Bird").set(Bird);
            }
        });
        //add or delete lime
        limeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle lime login
            }
        });
        //add or delete spin
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle spin login
            }
        });

        //some more functions for later
        /*
        //handle service filters
        //filter for bird
        birdFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle bird filter toggle
            }
        });
        //filter for lime
        limeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle lime filter toggle
            }
        });
        //filter for spin
        spinFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle spin filter toggle
            }
        });
        //alter maxPrice variable
        /*
        //handle vehicle filters
        //filter for bike
        bikeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle bike filter toggle
            }
        });
        //filter for scooter
        scooFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle scooter filter toggle
            }
        });
         */

        //handle price filter


        //handle services filter

        filterOptionsLayer = (LinearLayout) findViewById(R.id.filter_options_layer);
        filterOptionsLayer.setVisibility(View.INVISIBLE);
        // Shows filter menu when pressing the filter Button
        filterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (filterOptionsLayer.getVisibility() == View.VISIBLE) {
                    filterOptionsLayer.setVisibility(View.INVISIBLE);
                } else {
                    filterOptionsLayer.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(Map.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Map.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


}
