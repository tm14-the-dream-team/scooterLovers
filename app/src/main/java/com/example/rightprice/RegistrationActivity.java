package com.example.rightprice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userEmail, userPass, confirmPass;
    private Button regButton;
    private TextView userLogin;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        //sends registration info to the database
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                if (validate()) {
                    //TODO
                    //upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPass.getText().toString().trim();

                    /*
                     *  create a user with an email and password
                     */
                    mAuth.createUserWithEmailAndPassword(user_email, user_password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignInFailed", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(RegistrationActivity.this, Map.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignInFailed", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegistrationActivity.this, "User already exists.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }
        });


        //When user clicks button, it goes back to login page
        userLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

    }

    //links variables to the content ID's
    private void setupUIViews() {
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPass = (EditText)findViewById(R.id.password);
        confirmPass = (EditText)findViewById(R.id.confirmPass);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.userLogin);

    }

    //Show an error message if info is left blank
    private Boolean validate(){
        Boolean result = false;
        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        String cPass = confirmPass.getText().toString();
        if (email.isEmpty() && pass.isEmpty() && cPass.isEmpty()) {
            Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }
}

