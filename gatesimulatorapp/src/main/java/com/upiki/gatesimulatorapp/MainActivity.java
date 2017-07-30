package com.upiki.gatesimulatorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.zxing.Result;
import com.upiki.gatesimulatorapp.api.Api;
import com.upiki.gatesimulatorapp.api.BayarTolApi;
import com.upiki.gatesimulatorapp.api.UserApi;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.PROFILE;
import static com.upiki.gatesimulatorapp.LoginAndRegisterActivity.UID;

/**
 * A class representing the activity of gate app scanner.
 * @author Martino Christanto Khuangga <martino.aksel.11@gmail.com>
 * @since 2017.07.01
 */
public class MainActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        scannerView = (ZXingScannerView)
                findViewById(R.id.scanner_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    /**
     * Method for handling barcode scan result.
     * @param result scan result
     */
    @Override
    public void handleResult(Result result) {
        final String[] parsedResult = result.toString().split("_");
        if (parsedResult.length != 4) {
            Toast.makeText(getApplicationContext(),
                    "Barcode salah",
                    Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sp = getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
            String uid = sp.getString(UID, "");
            String isBusiness = parsedResult[2];
            BayarTolApi.userApi.getTollTransaction(getApplicationContext(),
                    uid,
                    isBusiness, new Api.ApiListener<UserApi.DataMsgResponse>() {
                        @Override
                        public void onApiSuccess(UserApi.DataMsgResponse result, String rawJson) {
                            Toast.makeText(getApplicationContext(),
                                    "Transaksi dengan pengguna " + parsedResult[0] + " berhasil",
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onApiError(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    "Transaksi dengan pengguna " + parsedResult[0] + " gagal",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;
        }
        isBackPressed = true;
        Toast.makeText(
                getApplicationContext(),
                R.string.quit_application_instruction,
                Toast.LENGTH_SHORT).show();

        //If user not pressed back button for 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }
}
