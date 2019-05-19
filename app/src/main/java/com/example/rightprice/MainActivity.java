package com.example.rightprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private boolean birdToggle;
    private boolean limeToggle;
    private boolean spinToggle;
    private double maxPrice = 20;
    private boolean bikeToggle;
    private boolean scooToggle;

    public void birdToggle(){
        birdToggle = !birdToggle;
    }

    public void birdToggle(boolean b){
        birdToggle = b;
    }

    public void limeToggle(){
        limeToggle = !limeToggle;
    }

    public void limeToggle(boolean b){
        limeToggle = b;
    }

    public void spinToggle(){
        spinToggle = !spinToggle;
    }

    public void spinToggle(boolean b){
        spinToggle = b;
    }

    public void bikeToggle(){
        bikeToggle = !bikeToggle;
    }

    public void bikeToggle(boolean b){
        bikeToggle = b;
    }

    public void scooToggle(){
        scooToggle = !scooToggle;
    }

    public void scooToggle(boolean b){
        scooToggle = b;
    }

    public void setMaxPrice(double num){
        maxPrice = num;
    }

/*    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        startActivity(launchMap);
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void launchMap(View view) {
        System.out.println("LAUNCH MAP");
        Intent launchMap = new Intent(this, Map.class);
        startActivity(launchMap);
        finish();
    }

    protected void launchRegistration(View view) {
        System.out.println("LAUNCH REGISTRATION");
        Intent launchRegistration = new Intent(this, RegistrationActivity.class);
        startActivity(launchRegistration);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
