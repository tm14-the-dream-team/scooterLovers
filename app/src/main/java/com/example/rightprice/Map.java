package com.example.rightprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Map extends FragmentActivity implements OnMapReadyCallback {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


    /*
    *  On press of back button exit the app
    */
    @Override
    public void onBackPressed() {
        finishAffinity();
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

        // Add a marker in Sydney and move the camera
        LatLng central_campus = new LatLng(32.880283, -117.237556);
        mMap.addMarker(new MarkerOptions().position(central_campus).title("Geisel"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(central_campus,16.0f));
    }
}
