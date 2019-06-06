package com.example.rightprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ServiceActivity extends AppCompatActivity {

    private ToggleButton birdButton;
    private ToggleButton limeButton;
    private ToggleButton spinButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth = getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userUID = user.getUid();

        //check saved settings
        DocumentReference birdSettingRef = db.collection("Users").document(user.getUid()).collection("Services").document("Bird");
        final DocumentReference userDocRef = db.getInstance().collection("Users").document(userUID);

        birdButton = findViewById(R.id.bird_toggle);
        birdSettingRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    try {
                        if (documentSnapshot.getBoolean("birdAdded"))
                            birdButton.toggle();
                    } catch (Exception e){
                        Toast.makeText(ServiceActivity.this, "Please create a new account." ,
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(ServiceActivity.this, MainActivity.class));
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
                Toast.makeText(ServiceActivity.this, "failed to get settings.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        limeButton = findViewById(R.id.lime_toggle);
        DocumentReference limeSettingRef = db.collection("Users").document(user.getUid()).collection("Services").document("Lime");

        limeSettingRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    try {
                        if (documentSnapshot.getBoolean("limeAdded"))
                            limeButton.toggle();
                    } catch (Exception e){
                        Toast.makeText(ServiceActivity.this, "Please create a new account." ,
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(ServiceActivity.this, MainActivity.class));
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
                Toast.makeText(ServiceActivity.this, "failed to get settings.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        spinButton = findViewById(R.id.spin_toggle);
        DocumentReference spinSettingRef = db.collection("Users").document(user.getUid()).collection("Services").document("Spin");

        spinSettingRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    try {
                        if (documentSnapshot.getBoolean("spinAdded"))
                            spinButton.toggle();
                    } catch (Exception e){
                        Toast.makeText(ServiceActivity.this, "Please create a new account." ,
                                Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                        startActivity(new Intent(ServiceActivity.this, MainActivity.class));
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
                Toast.makeText(ServiceActivity.this, "failed to get settings.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //add or delete bird
        birdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //handle bird login
                HashMap<String, Object> Bird = new HashMap<>();
                if(birdButton.isChecked()) {
                    Bird.put("birdAdded", true);
                    Bird.put("birdEmail", userUID + "@ucsd.com");
                } else {
                    Bird.put("birdAdded", false);
                    Bird.put("birdEmail", userUID + "@ucsd.com");
                }
                userDocRef.collection("Services").document("Bird").set(Bird);
            }
        });
        //add or delete lime
        limeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HashMap<String, Object> Lime = new HashMap<>();
                if(limeButton.isChecked()) {
                    Lime.put("limeAdded", true);
                } else {
                    Lime.put("limeAdded", false);
                }
                userDocRef.collection("Services").document("Lime").set(Lime);
            }
        });
        //add or delete spin
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                HashMap<String, Object> Spin = new HashMap<>();
                if(spinButton.isChecked()) {
                    Spin.put("spinAdded", true);
                } else {
                    Spin.put("spinAdded", false);
                }
                userDocRef.collection("Services").document("Spin").set(Spin);
            }
        });

    }
}
