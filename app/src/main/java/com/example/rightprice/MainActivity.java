package com.example.rightprice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class MainActivity extends AppCompatActivity {
    //Context context = this.getApplicationContext();
    Cache cache;
    Network network;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void launchMap(View view) {
        System.out.println("IT WORKED");
        Intent launchMap = new Intent(this, Map.class);
        startActivity(launchMap);
        cache = new DiskBasedCache(this.getCacheDir(), 1024*1024);//1MB
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        try {
            Location loc = new Location();
            loc.setLatitude(32.880277);
            loc.setLongitude(-117.237552);
            //Bird bird = new Bird("ZSofjd'oiohshsdkjdslkdfjngdflkg@ucsd.com",loc);
            Bird bird = new Bird(loc);
            /**
             * USER MUST HAVE CURRENTLY EXISTING LIME ACCOUNT ASSOCIATED WITH
             * THE PHONE NUMBER PASSED BELOW
             */
            //requestQueue.add(bird.getInitReq());
            System.out.println("------------OKKKKKK--------------");
            //Lime lime = new Lime("19493713971");//
            //requestQueue.add(lime.getInitReq());

            if(!bird.getToken().equals("none")){
                System.out.println("Token assigned Printing request..");
                System.out.println(bird.getVehicleReq());
                requestQueue.add(bird.getInitReq());
                requestQueue.add(bird.getVehicleReq());

            }
            else{
                System.out.println("No token Bird...");

            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
