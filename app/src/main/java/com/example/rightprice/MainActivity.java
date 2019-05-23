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
        cache = new DiskBasedCache(this.getCacheDir(), 1024*1024);//1MB
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
	finish();
        try {
            Location loc = new Location();
            loc.setLatitude(32.880277);
            loc.setLongitude(-117.237552);
            Bird bird = new Bird("HSofjd'oiohshsdkjdslkdfjngdflkg@ucsd.com",loc);
            /**
             * USER MUST HAVE CURRENTLY EXISTING LIME ACCOUNT ASSOCIATED WITH
             * THE PHONE NUMBER PASSED BELOW
             */
            requestQueue.add(bird.getInitReq());
            System.out.println("OKKKKKK");
            Lime lime = new Lime("19493713971");//
            requestQueue.add(lime.getInitReq());

            while (bird.getToken()=="none") {

            }
            if(bird.getToken()!="none"){
                requestQueue.add(bird.getVehicleReq());

            }
            else{
                System.out.println("failure--noToken assigned");

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
