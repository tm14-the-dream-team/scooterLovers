package com.example.rightprice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgotpass);

        nextBtn = (Button) findViewById(R.id.nextbtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(nextBtn.getText().equals("SEND")){
                    sendEmail();

                } else {
                    Intent home = new Intent(ForgotPassActivity.this, MainActivity.class);
                    startActivity(home);
                }

            }
        });
    }

    protected void sendEmail() {
        EditText emailText = (EditText) findViewById(R.id.userEmail);
        String email = emailText.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("SEND EMAIL", "Email sent.");
                            Toast.makeText(ForgotPassActivity.this, "Email Sent.",
                                    Toast.LENGTH_SHORT).show();
                            nextBtn.setText("BACK");
                        } else {
                            Toast.makeText(ForgotPassActivity.this, "Email Not Sent.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
