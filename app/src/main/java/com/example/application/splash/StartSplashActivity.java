package com.example.application.splash;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.MainActivity;
import com.example.application.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.application.firebase.RealtimeDatabaseAccess;
import com.example.application.user.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartSplashActivity extends AppCompatActivity
{
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_splash);

        context=this;
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if (firebaseUser == null)
                    startActivity(new Intent(context, LoginActivity.class));
                else
                    startActivity(new Intent(context, MainActivity.class));
            }
        },2500);
    }
}