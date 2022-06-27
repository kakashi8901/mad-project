package com.foddie.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferenceManager.init(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceManager.readBoolean(SharedPreferenceManager.IS_SIGNIN, false))
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, SignInScreen.class));

                finish();
            }
        }, 1000);

    }
}