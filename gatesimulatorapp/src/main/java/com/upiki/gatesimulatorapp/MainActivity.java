package com.upiki.gatesimulatorapp;

import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

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
        Toast.makeText(
                getApplicationContext(),
                result.getText(),
                Toast.LENGTH_LONG).show();

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
