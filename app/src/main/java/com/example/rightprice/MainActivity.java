package com.example.rightprice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

<<<<<<< .merge_file_a17712
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
=======
import com.google.firebase.auth.FirebaseUser;
>>>>>>> .merge_file_a18280

public class MainActivity extends AppCompatActivity {
    //Context context = this.getApplicationContext();
    Cache cache;
    Network network;
    RequestQueue requestQueue;


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
<<<<<<< .merge_file_a17712
        cache = new DiskBasedCache(this.getCacheDir(), 1024*1024);//1MB
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();
        try {
            Bird bird = new Bird("fakEeMaIl@ucsd.edu");
            requestQueue.add(bird.getInitReq());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
=======
        finish();
    }

    protected void launchRegistration(View view) {
        System.out.println("LAUNCH REGISTRATION");
        Intent launchRegistration = new Intent(this, RegistrationActivity.class);
        startActivity(launchRegistration);
>>>>>>> .merge_file_a18280
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}
