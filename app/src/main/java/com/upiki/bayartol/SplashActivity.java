package com.upiki.bayartol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.upiki.bayartol.page.login.LoginAndRegisterActivity;
import com.upiki.bayartol.page.profile.ProfileFragment;

/**
 * Splash screen activity to check if local data exist.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.24
 */
public class SplashActivity extends AppCompatActivity {

    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImage = (ImageView) findViewById(R.id.splashscreen);
        mImage.setImageResource(getResources().getIdentifier("splashscreen", "drawable", getPackageName()));
        SharedPreferences sp = getSharedPreferences(
                ProfileFragment.PROFILE,
                MODE_PRIVATE);
        String uid = sp.getString(ProfileFragment.UID, "");
        try {
            Thread.sleep(2000);
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
