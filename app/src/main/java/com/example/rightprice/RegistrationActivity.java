package com.example.rightprice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userEmail, userPass;
    private Button regButton;
    private TextView userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        //sends registration info to the database
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    //TODO
                    //upload data to database
                }
            }
        });

        //When user clicks button, it goes back to login page
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });
    }

    //links variables to the content ID's
    private void setupUIViews() {
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPass = (EditText)findViewById(R.id.password);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.userLogin);
    }

    //Show an error message if info is left blank
    private Boolean validate(){
        Boolean result = false;
        String email = userEmail.getText().toString();
        String pass = userPass.getText().toString();
        if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }
}

