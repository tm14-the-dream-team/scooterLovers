package com.example.rightprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private boolean birdToggle;
    private boolean limeToggle;
    private boolean spinToggle;
    private double maxPrice = 20;
    private boolean bikeToggle;
    private boolean scooToggle;
    private FirebaseAuth mAuth;


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

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent launchMap = new Intent(this, Map.class);
            startActivity(launchMap);
            finish();
        }
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

    protected void login(View view) {
        mAuth = FirebaseAuth.getInstance();

        EditText emailText = (EditText) findViewById(R.id.loginEmail);
        EditText passText = (EditText) findViewById(R.id.loginPass);

        String email = emailText.getText().toString();
        String password = passText.getText().toString();


        try {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("LOGIN", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                mAuth = FirebaseAuth.getInstance();

                                startActivity(new Intent(MainActivity.this, Map.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Please enter all information.",
                    Toast.LENGTH_SHORT).show();
        }


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
