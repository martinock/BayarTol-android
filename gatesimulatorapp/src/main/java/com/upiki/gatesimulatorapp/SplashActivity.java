package com.upiki.gatesimulatorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PROFILE;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.UID;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sp = getSharedPreferences(
                PROFILE,
                MODE_PRIVATE);
        String uid = sp.getString(UID, "");
        try {
            Thread.sleep(1000);
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
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
