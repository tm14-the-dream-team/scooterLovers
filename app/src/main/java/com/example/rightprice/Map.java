package com.example.rightprice;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton settingsButton;
    private ImageButton filterButton;
    private ToggleButton birdButton;
    private ToggleButton limeButton;
    private ToggleButton spinButton;
    private ToggleButton birdFilter;
    private ToggleButton limeFilter;
    private ToggleButton spinFilter;
    //slider initialize maxPrice
    private ToggleButton bikeFilter;
    private ToggleButton scooFilter;
    private LinearLayout servicesLayer;
    private LinearLayout filterOptionsLayer;
    private Button logoutButton;
    private FirebaseAuth mAuth;
    private Button gpsButton;

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





    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    //marker implementation
    private ArrayList<Vehicle> vehicleArrayList;
    private ArrayList<Marker> markerArrayList;
    float spinColor = BitmapDescriptorFactory.HUE_ORANGE;
    float limeColor = BitmapDescriptorFactory.HUE_GREEN;
    float birdColor = BitmapDescriptorFactory.HUE_AZURE;
    //popup window implementation
    private TextView serviceLabel;
    private TextView batteryValue;
    private TextView startValue;
    private TextView minuteValue;
    private Button startButton;
    private Button closeButton;
    private LinearLayout popupLayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        getLocationPermission();

        mAuth = getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Toast.makeText(Map.this, user.getUid(),
                Toast.LENGTH_SHORT).show();

        settingsButton = (ImageButton) findViewById(R.id.settings_button);
        filterButton = (ImageButton) findViewById(R.id.filter_button);

        birdButton = findViewById(R.id.bird_toggle);
        DocumentReference birdSettingRef = db.collection("Users").document(user.getUid()).collection("Services").document("Bird");

        birdSettingRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    try {
                        if (documentSnapshot.getBoolean("birdAdded"))
                            birdButton.toggle();
                    } catch (Exception e){
                        Toast.makeText(Map.this, "Please create a new account." ,
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(Map.this, MainActivity.class));
                        finish();

                    }
                } else {
                    System.err.println("No such document!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("getCurrSettings", "getCurrSettings:failure", e);
                Toast.makeText(Map.this, "failed to get settings.",
                        Toast.LENGTH_SHORT).show();
            }
        });

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
                Bird.put("birdAdded", true);
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

        // Added initializations for Adding pins
        vehicleArrayList = new ArrayList<Vehicle>();
        markerArrayList = new ArrayList<Marker>();
        // Added Stuff for popup window
        serviceLabel = findViewById(R.id.popup_service);
        batteryValue = findViewById(R.id.popup_battery_value);
        startValue = findViewById(R.id.popup_start_value);
        minuteValue = findViewById(R.id.popup_minute_value);
        startButton = findViewById(R.id.start_button);
        popupLayer = findViewById(R.id.popup_layer);
        popupLayer.setVisibility(View.INVISIBLE);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //implement starting the bird
            }
        });
        closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                popupLayer.setVisibility(View.INVISIBLE);
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
                            Location currentLocation = (Location) task.getResult();

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
        // Add a marker in Sydney and move the camera
        LatLng central_campus = new LatLng(32.880283, -117.237556);
        mMap.addMarker(new MarkerOptions().position(central_campus).title("Geisel"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(central_campus,16.0f));

        //marker fun initialize the dummy vehicles.
//        Vehicle bird = new Vehicle();
//        bird.setLat(32.8797);
//        bird.setLng(-117.2362);
//        bird.setVendor("bird");
//        bird.setBattery(50);
//        LatLng bird_pos = new LatLng(32.8797, -117.2362);
//        Vehicle lime = new Vehicle();
//        lime.setLat(32.8785);
//        lime.setLng(-117.2397);
//        lime.setVendor("lime");
//        lime.setBattery(100);
//        LatLng lime_pos = new LatLng(32.8785, -117.2397);
//        Vehicle spin = new Vehicle();
//        spin.setLat(32.8851);
//        spin.setLng(-117.2392);
//        spin.setVendor("spin");
//        spin.setBattery(69);
//        LatLng spin_pos = new LatLng(32.8851, -117.2392);
//        vehicleArrayList.add(bird);
//        vehicleArrayList.add(lime);
//        vehicleArrayList.add(spin);
//        this.loadVehiclePins(mMap,vehicleArrayList,markerArrayList);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTag() != null ){
                    // Populates popup layer
                    Vehicle vehicle = (Vehicle)marker.getTag();
                    serviceLabel.setText((vehicle.getVendor().substring(0,1).toUpperCase()+vehicle.getVendor().substring(1)));
                    popupLayer.setVisibility(View.VISIBLE);
                }
                return true; //suppresses default behavior. false uses default.
            }
        });
    }

    public void loadVehiclePins(GoogleMap googleMap, ArrayList<Vehicle> vehicleArrayList, ArrayList<Marker> markerArrayList){
        for(int i = 0; i < vehicleArrayList.size(); i++){
            LatLng pos = new LatLng( vehicleArrayList.get(i).getLat(), vehicleArrayList.get(i).getLng());
            String vendor = vehicleArrayList.get(i).getVendor();
            float color;
            switch(vendor) {
                case "bird":
                    color = birdColor;
                    break;
                case "spin":
                    color = spinColor;
                    break;
                case "lime":
                    color = limeColor;
                    break;
                default:
                    System.out.print("What the fuck");
                    color = BitmapDescriptorFactory.HUE_VIOLET;
            }
            Marker marker = googleMap.addMarker(new MarkerOptions().position(pos)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(color)));
            marker.setTag(vehicleArrayList.get(i)); //adds vehicle to the marker.
            markerArrayList.add(marker);
        }
    }



}
