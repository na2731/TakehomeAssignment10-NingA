package com.example.android.ioder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
//import java.util.*;
//import android.widget.ProgressBar;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText pmail,uord;
    private FirebaseAuth mAuth;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.text2).setOnClickListener(this);

        pmail=(EditText)findViewById(R.id.email);
        uord=(EditText)findViewById(R.id.pass);

    }

    private void registerUser(){
        System.out.println("Entered registerUser???");
        String email=pmail.getText().toString().trim();
        String Password=uord.getText().toString().trim();
        if(email.isEmpty()){
            pmail.setError("Email is required");
            pmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            pmail.setError("Please enter an valid email");
            pmail.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            uord.setError("Password is required");
            uord.requestFocus();
             return;
        }
        if(Password.length()<6){
            uord.setError("Minimum lenghth of password should be 6");
            uord.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            //@Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
////                if(task.isSuccessful()){
////                    Toast.makeText(getApplicationContext(),"User Registered Successful",Toast.LENGTH_SHORT).show();
////                }
//
//
//            }

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //finish();
                    //startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));
                    Toast.makeText(getApplicationContext(), "You are done", Toast.LENGTH_SHORT).show();
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }



        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp:
                registerUser();
                break;

            case R.id.text2:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }



}
