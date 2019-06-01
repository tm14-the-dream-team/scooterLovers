package com.example.rightprice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        startActivity(launchMap);
    }

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
