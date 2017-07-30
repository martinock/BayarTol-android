package com.upiki.gatesimulatorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PROFILE;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.UID;

public class SplashActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImage = (ImageView) findViewById(R.id.splashscreen);
        mImage.setImageResource(getResources().getIdentifier("splashscreen", "drawable", getPackageName()));
        SharedPreferences sp = getSharedPreferences(
                PROFILE,
                MODE_PRIVATE);
        final String uid = sp.getString(UID, "");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (uid.isEmpty()) {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            LoginAndRegisterActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
