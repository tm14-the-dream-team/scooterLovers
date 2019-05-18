package com.example.rightprice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void launchMap(View view) {
        System.out.println("IT WORKED");
        Intent launchMap = new Intent(this, Map.class);
        startActivity(launchMap);
    }

    protected void launchRegistration(View view) {
        System.out.println("IT WORKED");
        Intent launchRegistration = new Intent(this, RegistrationActivity.class);
        startActivity(launchRegistration);
    }
}
